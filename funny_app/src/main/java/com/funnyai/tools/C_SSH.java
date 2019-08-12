/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

/**
 *
 * @author happyli
 */
import com.funnyai.io.C_Property_File;
import com.funnyai.io.Old.S_File_Text;
import com.funnyai.string.Old.S_Strings;
import com.funnyai.tools.other.MyUserInfo;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provide interface to execute command on remote Linux.
 */
public class C_SSH {

    public String ipAddress;

    public String username;

    public String password;
    
    public String file_pem="";
    
    public static int iPort = 22;

    public boolean bReading=true;
    public StringBuilder output_stringbuilder = new StringBuilder();
    public int Wait_Count=3;
    int iCount=0;
    
    public C_SSH(final String ipAddress, final String username, 
            final String password,String file_pem,
            int Wait_Count) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        this.Wait_Count=Wait_Count;
        this.file_pem=file_pem;
    }

    public String execute(final String command) {
        StringBuilder pStr=new StringBuilder();
        JSch jsch = new JSch();

        try {
            if ("".equals(password)){
                jsch.addIdentity(this.file_pem);
                jsch.setConfig("StrictHostKeyChecking", "no");
            }
            Session session = jsch.getSession(username, ipAddress, iPort);
            UserInfo ui = new MyUserInfo();
            
            if ("".equals(password)==false){
                session.setPassword(password);
            }
            session.setUserInfo(ui);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            Channel channel = session.openChannel("shell");
            
            //((ChannelShell)channel).setPty(false);//不要显示字体颜色
            

            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            PrintStream commander = new PrintStream(inputstream_for_the_channel, true);

            InputStream outputstream_from_the_channel = channel.getInputStream();

            channel.connect();

            
            commander.println("bash");
            String[] strSplit=command.split("\n");
            for (int i=0;i<strSplit.length;i++){
                commander.println(strSplit[i]);
            }
            //commander.println("exit");
   
            InputStreamReader pReader=new InputStreamReader(outputstream_from_the_channel,"utf-8");
            readerThread(output_stringbuilder,pReader,this.Wait_Count);
            
            while(true){
                out.println(iCount+"/"+Wait_Count);
                if (iCount>=Wait_Count){
                    break;
                }
                Thread.sleep(1000);
            }
            
            commander.close();
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  pStr.toString();
    }

    
    public void readerThread(final StringBuilder pReturn,final InputStreamReader tout,final int Wait_Count)
    {
        Thread read2 = new Thread(){
        @Override
        public void run(){
            StringBuilder line=new StringBuilder();
            char toAppend = ' ';
            try {
                while(true){
                    iCount+=1;
                    out.println(iCount+"/"+Wait_Count);
                    try {
                        while (tout.ready()) {
                            iCount=0;
                            toAppend = (char) tout.read();
                            if(toAppend == '\n')
                            {
                                pReturn.append(line.toString());
                                out.println(line.toString());
                                line.setLength(0);
                            }
                            else
                                line.append(toAppend);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.println("\n\n\n************errorrrrrrr reading character**********\n\n\n");
                    }
                    Thread.sleep(1000);
                    if (iCount>=Wait_Count){
                        break;
                    }
                }
            }catch (Exception ex) {
                out.println(ex);
                try{
                    tout.close();
                }
                catch(Exception e)
                {}
            }
            bReading=false;
        }
        };
        read2.start();
    }

    public static void main(final String[] args) {
        if (args.length <2) {
            out.println("至少两个参数！file.ini file.command");
            return ;
        }
        String strFile=args[0];
        String strFile_Command=args[1];
        
        out.println(strFile);
        out.println(strFile_Command);
        
        String IP="120.26.115.192";
        String user="root";
        String password="";
        String file_pem="";
        int wait_count=2;
        try {
            C_Property_File pFile=new C_Property_File(strFile);
            
            IP = pFile.Read("ip");
            iPort = Integer.valueOf(pFile.Read("port"));
            if (iPort==0){
                iPort=22;
            }
            user = pFile.Read("user");
            password = pFile.Read("password");
            file_pem = pFile.Read("pem");
            wait_count = S_Strings.getIntFromStr(pFile.Read("wait"),2);
        } catch (IOException ex) {
        }
        
        out.println("IP="+IP);
        out.println("user="+user);
        out.println("file_pem="+file_pem);
        C_SSH sshExecutor = new C_SSH(IP,user,password,file_pem,wait_count);
        String strLine = S_File_Text.Read(strFile_Command,"utf-8",10000);//
        
        sshExecutor.execute(strLine);
        while(sshExecutor.bReading){
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(C_SSH.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.exit(0);
    }
}
