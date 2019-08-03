/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.common.AI_Var2;
import com.funnyai.common.Tools_Init;
import com.funnyai.fs.AI_Var3;
import com.funnyai.fs.C_Run_Session;

/**
 *
 * @author happyli
 */
public class SYS {
    private String[] args=null;
    
    SYS(String[] args) {
        this.args=args.clone();
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
}
