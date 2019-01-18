package com.zzrq.batch.runner;

import com.zzrq.batch.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Set;

//@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 启动调度器
        scheduler.start();
        /*//构建job信息
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("HelloJob", "HelloJobGroup").build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? ");

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("HelloJob" + "_trigger", "HelloJobGroup")
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
*/
      //  scheduler.resumeAll();
    }
}
