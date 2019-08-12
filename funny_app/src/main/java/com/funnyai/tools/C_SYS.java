/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

import com.funnyai.common.AI_Var2;
import com.funnyai.common.Tools_Init;
import com.funnyai.fs.AI_Var3;
import com.funnyai.fs.C_Run_Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 *
 * @author happyli
 */
public class C_SYS {
    private String[] args = null;
    private Scriptable scope = null;
    private Context ct = null;
    
    public C_SYS(String[] args,Scriptable scope,Context ct) {
        this.args=args.clone();
        this.scope=scope;
        this.ct=ct;
    }
    
    public String args(int index){
        return this.args[index];
    }
    
    public void Debug(String... a){
        for (String a1 : a) {
            System.out.println(a1);
        }
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
    
    public void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException ex) {
            Logger.getLogger(C_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String read_input(String strInfo){
        try {
            String strReturn=null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(strInfo);
            strReturn = br.readLine();
            return strReturn;
        } catch (IOException ex) {
            Logger.getLogger(C_SYS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void js_functon(String Function,String Data){
        Function fct = (Function)scope.get(Function, scope);
        Object result = fct.call(ct, scope, scope, new Object[] {Data});
    }
}
