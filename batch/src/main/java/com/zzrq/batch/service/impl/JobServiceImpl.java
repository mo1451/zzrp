package com.zzrq.batch.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.batch.dto.JobDetails;
import com.zzrq.batch.dto.Triggers;
import com.zzrq.batch.mapper.JobDetailsMapper;
import com.zzrq.batch.mapper.TriggersMapper;
import com.zzrq.batch.service.IJobService;
import org.apache.commons.beanutils.BeanUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements IJobService {

    //加入Qulifier注解，通过名称注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private TriggersMapper triggersMapper;

    @Autowired
    private JobDetailsMapper jobDetailsMapper;

    private static Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Override
    public void addJob(String jobName, String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        // 启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    private static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    @Override
    public void jobPause(String jobName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
    }

    @Override
    public void jobResume(String jobName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
    }

    @Override
    public void jobReschedule(String jobName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }

    @Override
    public void jobDelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @Override
    public  List<Map<String, String>> queryJob() {
        List<Trigger> triggers;
        List<Map<String, String>> maps = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //get job's trigger
                    triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        maps.add(BeanUtils.describe(trigger));
                    }
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);
                }
            }
        } catch (SchedulerException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return maps;
    }

    @Override
    public List<JobDetails> queryJobDetail(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return jobDetailsMapper.selectAll();
    }

    @Override
    public List<Triggers> queryJobTrigger(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return triggersMapper.selectAll();
    }
}
