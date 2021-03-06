/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.net.Old.S_Net;
import com.funnyai.net.S_net;

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
        S_Net.SI_Send2(event_type,stype,from,to,msg);
    }
    
    public void upload(String host,Integer iPort,String username,String password,
            String uploadFile,String directory){
        S_net.main.SFtp_Upload(host, iPort, username, password, uploadFile, directory);
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
    
    public String ssh(
            String IP,
            String user,
            String password,
            String file_pem,
            String strLine,
            Integer wait_count){
        M_SSH sshExecutor = new M_SSH(IP,user,password,file_pem,wait_count);
 
        String strReturn=sshExecutor.execute(strLine);
        return strReturn;
    }
}
