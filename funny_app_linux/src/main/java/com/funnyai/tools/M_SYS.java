/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.common.AI_Var2;
import com.funnyai.common.S_Command;
import com.funnyai.common.Tools_Init;
import com.funnyai.fs.AI_Var3;
import com.funnyai.fs.C_Run_Session;
import com.funnyai.io.Old.S_File;
import funnyai.JavaMain;
import static funnyai.JavaMain.strPath;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author happyli
 */
public class M_SYS {
    private String[] args = null;
    public M_SYS(String[] args) {
        this.args=args.clone();
    }
    
    public String args(Integer index){
        return this.args[index];
    }
    
    public String args_all(){
        String strLine="";
        for(int i=1;i<args.length;i++){
            strLine+=args[i]+" ";
        }
        return strLine.trim();
    }
    public void println(String a){
        System.out.println(a);
    }
    public void println(Double a){
        System.out.println(a);
    }
    
    public String linux(String command,Integer wait_second){
        return S_Command.RunShell_Return(command, wait_second);
    }
    
    public String js(String strLines){
        String[] args=strLines.split("\\|");
        M_SYS pSYS = new M_SYS(args);
        String strFile=args[0];
        strPath=new File(strFile).getParent();
        
        String strReturn=new JavaMain().Run(pSYS, strFile);
        return strReturn;
    }
    
    public void exit(Integer status){
        System.exit(status);
    }
    
    public void init_setting(String strFile){
        Tools_Init.Init(strFile);
    }
    
    public void init_session(){
        AI_Var3.pSessionS=C_Run_Session.Get_New_Session(0,0);
    }
    
    public String Path_Segmentation(){
        return AI_Var2.Path_Segmentation;
    }
    
    
    public String Path_Struct(){
        return AI_Var2.Path_Struct;
    }
    
    public void sleep(Integer second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException ex) {
            Logger.getLogger(M_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String path_jar(){
        String path = System.getProperty("java.class.path");
        int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        path = path.substring(firstIndex, lastIndex);
        return path;
    }
    
    public String path(){
        return JavaMain.strPath;
    }
    
    public String read_input(String strInfo){
        try {
            String strReturn=null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            out.println(strInfo);
            strReturn = br.readLine();
            return strReturn;
        } catch (IOException ex) {
            Logger.getLogger(M_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
    public void open_url(String url){
        if (url.startsWith("@")){
            url=url.replace("@", JavaMain.strPath);
            String[] strSplit=url.split("\\\\");
            
            //路径 .. 和前面一个目录抵消
            url="";
            for (int i=0;i<strSplit.length-1;i++){
                if (strSplit[i+1].equals("..")){
                    i+=1;
                }else{
                    url+=strSplit[i]+"\\";
                }
            }
            url+=strSplit[strSplit.length-1];
            if (S_File.Exists(url)){
                out.println("exists");
            }else{
                out.println("not exists");
            }
        }
        try {
            String browser = "cmd.exe "+url;//"chrome.exe";//+url; 
            Runtime.getRuntime().exec(new String[] { browser, url });
        } catch (IOException ex) {
            Logger.getLogger(M_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
