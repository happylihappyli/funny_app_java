/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import funnyai.JavaMain;
import static funnyai.JavaMain.pSYS;
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
        //Object pObj=JavaMain.v8.executeJSFunction("call_back_job", key);
        
        String strFile=JavaMain.strPath+"\\call_back_job_"+key+".js";
        strFile=strFile.replace("\\\\", "\\");
        
        new JavaMain().Run(pSYS, strFile);
        
    }

}