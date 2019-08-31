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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.mozilla.javascript.Context;
//import org.mozilla.javascript.Function;
//import org.mozilla.javascript.Scriptable;

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
    
    public void println(String a){
        System.out.println(a);
    }
    public void println(Double a){
        System.out.println(a);
    }
    
    public String linux(String command,Integer wait_second){
        return S_Command.RunShell_Return(command, wait_second);
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
    
}
