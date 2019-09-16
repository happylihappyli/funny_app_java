/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import java.util.Calendar;

/**
 *
 * @author happyli
 */
public class M_AI {
    
    public int 时间(String strLine){
        return time(strLine);
    }
    public int time(String strLine){
        Calendar now = Calendar.getInstance();
        //System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
        String[] strSplit=strLine.split("\\.");
        if (strSplit.length<2){
            System.out.println("error! 使用方法：今天.xxx");
        }
        switch(strSplit[0]){
            case "today":
            case "今天":
                switch(strSplit[1]){
                    case "年":
                        return now.get(Calendar.YEAR);
                    case "月":
                        return (now.get(Calendar.MONTH)+1);
                    case "日":
                        return now.get(Calendar.DAY_OF_MONTH);
                    case "时":
                        return now.get(Calendar.HOUR_OF_DAY);
                    case "分":
                        return now.get(Calendar.MINUTE);
                    case "秒":
                        return now.get(Calendar.SECOND);
                }
                break;
        }
        return -1;
    }
    
}
