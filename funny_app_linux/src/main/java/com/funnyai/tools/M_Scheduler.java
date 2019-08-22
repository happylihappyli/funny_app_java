/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author happyli
 */
public class M_Scheduler {
    private Scheduler scheduler;

    public M_Scheduler() {
        try {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException ex) {
            Logger.getLogger(M_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void stop(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException ex) {
            Logger.getLogger(M_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init(){
        try {
            scheduler.start();
        } catch (SchedulerException ex) {
            Logger.getLogger(M_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void add_job_daily(
            String job_key,String trigger_key,
            Integer hour,Integer minute){
        try {
            // define the job and tie it to our M_Job class
            JobDetail job = JobBuilder.newJob(M_Job.class)
                    .withIdentity(job_key)
                    .usingJobData("key", job_key)
                    .build();

            String exp = "10 "+minute+" "+hour+" 1/1 * ? *";
            Trigger trigger = TriggerBuilder.newTrigger()
                                    .startNow()
                                    .withSchedule(
                                         CronScheduleBuilder.cronSchedule(exp))
                                    .build();
            
            
            scheduler.scheduleJob(job, trigger);
            
        } catch (SchedulerException ex) {
            Logger.getLogger(M_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void add_job_week(
            String job_key,String trigger_key,
            Integer week,Integer hour,Integer minute){
        try {
            // define the job and tie it to our M_Job class
            JobDetail job = JobBuilder.newJob(M_Job.class)
                    .withIdentity(job_key)
                    .usingJobData("key", job_key)
                    .build();

            String exp = "10 "+minute+" "+hour+" ? * "+week+ " *";
            Trigger trigger = TriggerBuilder.newTrigger()
                                    .startNow()
                                    .withSchedule(
                                         CronScheduleBuilder.cronSchedule(exp))
                                    .build();
            
            
            scheduler.scheduleJob(job, trigger);
            
        } catch (SchedulerException ex) {
            Logger.getLogger(M_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
