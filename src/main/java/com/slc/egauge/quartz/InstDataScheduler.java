/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.egauge.quartz;


import com.slc.egauge.service.DataDumpService;
import com.slc.egauge.service.InstantaneousScraperService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.quartz.DateBuilder;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Class to handle requesting for data every 30 seconds
 * Implements ServeletContextListener to start when the servlet starts
 * 
 * @author srostantkritikos06
 */
@WebListener
public class InstDataScheduler implements ServletContextListener {
     private Scheduler scheduler = null;
    
    /**
     * Schedule job to use instantaneous scraper service. 
     * 
     * @param sce 
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       try {
                System.out.println("Sheduler started");
                SchedulerFactory sf = new StdSchedulerFactory();
                scheduler = sf.getScheduler();
                scheduler.start();
                
                JobDetail job =  newJob(InstantaneousScraperService.class).withIdentity("instJob").build();
                
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1")
                        .startAt(DateBuilder.futureDate(30, DateBuilder.IntervalUnit.SECOND))
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30).repeatForever()).build();
               
               //DATA DUMP
               JobDetail dataDumpjob = newJob(DataDumpService.class).withIdentity("dumpJob").build();
               Trigger dataDumpTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2")
                       .build();
               
               scheduler.scheduleJob(job, trigger);
               //scheduler.scheduleJob(dataDumpjob, dataDumpTrigger);
               
            } catch (SchedulerException ex) {
            Logger.getLogger(InstDataScheduler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception thrown in scheduler function");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       if (scheduler !=  null) {
           try {
               scheduler.shutdown();
           } catch (SchedulerException ex) {
               Logger.getLogger(InstDataScheduler.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }  
            
}
