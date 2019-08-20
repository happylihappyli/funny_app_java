/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import funnyai.JavaMain;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author happyli
 */
public class M_Job implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String key=(String) jobExecutionContext
                .getJobDetail().getJobDataMap().get("key");
        System.out.println("key: "+key);
        JavaMain.pSYS.js_functon("call_back_job", key);
    }

}