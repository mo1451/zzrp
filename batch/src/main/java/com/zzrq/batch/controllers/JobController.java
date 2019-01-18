package com.zzrq.batch.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.batch.service.IJobService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IJobService jobService;

    private static Logger log = LoggerFactory.getLogger(JobController.class);

    @ApiOperation(value = "添加job")
    @ApiImplicitParams({@ApiImplicitParam(name = "jobName", value = "任务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobClassName", value = "任务class完整路径", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobGroupName", value = "任务group名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cronExpression", value = "corn表达式", required = true, dataType = "String")})
    @PostMapping(value = "/addJob")
    public ResponseData addJob(@RequestParam(value = "jobName") String jobName,
                               @RequestParam(value = "jobClassName") String jobClassName,
                               @RequestParam(value = "jobGroupName") String jobGroupName,
                               @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        jobService.addJob(jobName, jobClassName, jobGroupName, cronExpression);
        return new ResponseData();
    }

    @ApiOperation(value = "暂停job")
    @ApiImplicitParams({@ApiImplicitParam(name = "jobName", value = "任务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobGroupName", value = "任务group名", required = true, dataType = "String")})
    @PostMapping(value = "/pauseJob")
    public ResponseData pauseJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobService.jobPause(jobName, jobGroupName);
        return new ResponseData();
    }

    @ApiOperation(value = "恢复job")
    @ApiImplicitParams({@ApiImplicitParam(name = "jobName", value = "任务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobGroupName", value = "任务group名", required = true, dataType = "String"),})
    @PostMapping(value = "/resumeJob")
    public ResponseData resumeJob(@RequestParam(value = "jobName") String jobName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobService.jobResume(jobName, jobGroupName);
        return new ResponseData();
    }

    @ApiOperation(value = "重置job的执行计划")
    @ApiImplicitParams({@ApiImplicitParam(name = "jobName", value = "任务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobGroupName", value = "任务group名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cronExpression", value = "corn表达式", required = true, dataType = "String")})
    @PostMapping(value = "/rescheduleJob")
    public ResponseData rescheduleJob(@RequestParam(value = "jobName") String jobName,
                                      @RequestParam(value = "jobGroupName") String jobGroupName,
                                      @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        jobService.jobReschedule(jobName, jobGroupName, cronExpression);
        return new ResponseData();
    }

    @ApiOperation(value = "删除job")
    @ApiImplicitParams({@ApiImplicitParam(name = "jobName", value = "任务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jobGroupName", value = "任务group名", required = true, dataType = "String"),})
    @PostMapping(value = "/deleteJob")
    public ResponseData deleteJob(@RequestParam(value = "jobName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobService.jobDelete(jobClassName, jobGroupName);
        return new ResponseData();
    }

    @ApiOperation(value = "查询任务Detail")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping(value = "/queryJobDetail")
    public ResponseData queryJobDetail(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseData(jobService.queryJobDetail(pageNum, pageSize));
    }


    @ApiOperation(value = "查询任务trigger")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示个数", required = true, dataType = "int"),})
    @GetMapping(value = "/queryJobTrigger")
    public ResponseData queryJobTrigger(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseData(jobService.queryJobTrigger(pageNum, pageSize));
    }
}
