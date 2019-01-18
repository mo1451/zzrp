package com.zzrq.batch.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BaseJob implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //这里可以获取控制器绑定的值，实际应用中可以设置为某个活动的id,以便进行数据库操作
        Object jobName = jobExecutionContext.getJobDetail().getKey();
        logger.info("定时任务[{}]开始执行>{}", jobName, new Date());
    }

}
