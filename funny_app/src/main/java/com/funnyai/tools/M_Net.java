/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.io.Old.S_File_Text;
import com.funnyai.net.Old.S_Net;
import com.funnyai.net.S_net;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author happyli
 */
public class M_Net {
    public void set_socket_server(String url){
        S_Net.set_socket_server(url);
    }
    
    public void send_msg(
            String event_type,String stype,
            String from,String to,String msg){
        S_Net.SI_Send(event_type,stype,from,to,msg);
    }
    
    public void stop_socket(){
        S_Net.socket.close();
    }
    
    public String http_get(String url){
        return S_Net.http_get(url);
    }
    
    public String http_post(String url,String strData){
        return S_net.main.http_post(url, strData);
    }
    
    public String ssh(String IP,
            String user,String password,
            String file_pem,int wait_count,
            String strLine){
        M_SSH sshExecutor = new M_SSH(IP,user,password,file_pem,wait_count);
 
        String strReturn=sshExecutor.execute(strLine);
//        while(sshExecutor.bReading){
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(M_SSH.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return strReturn;
    }
}
