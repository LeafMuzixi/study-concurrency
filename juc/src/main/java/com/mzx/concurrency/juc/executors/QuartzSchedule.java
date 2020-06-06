package com.mzx.concurrency.juc.executors;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzSchedule {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = newJob(SimpleJob.class).withIdentity("Job1", "Group1").build();
        Trigger trigger = newTrigger().withIdentity("Trigger1", "Group1")
                .withSchedule(cronSchedule("0/5 * * * * ?")).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
