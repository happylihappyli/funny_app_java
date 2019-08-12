/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.tools.C_Scheduler;
import com.funnyai.tools.C_JSON;
import com.funnyai.tools.C_SYS;
import com.funnyai.tools.C_DB;
import com.funnyai.io.S_File_Text;
import com.funnyai.io.S_file;
import com.funnyai.net.S_net;
import com.funnyai.tools.C_File;
import com.funnyai.tools.C_MapDB;
import com.funnyai.tools.C_Net;
import com.funnyai.tools.C_RSA;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author happyli
 */
public class JavaMain {
    
    public static C_SYS pSYS=null;
    
    public static void main(String[] args){
        Context ct = Context.enter(); 
        Scriptable scope = ct.initStandardObjects(); 

        pSYS=new C_SYS(args,scope,ct);
        scope_put(scope,"s_sys",pSYS);
        scope_put(scope,"s_out",System.out);
        
        scope_put(scope,"s_db",new C_DB());
        scope_put(scope,"s_json",new C_JSON());
        scope_put(scope,"s_scheduler",new C_Scheduler());
        scope_put(scope,"s_net",new C_Net());
        scope_put(scope,"s_file",new C_File());
        scope_put(scope,"s_mapdb",new C_MapDB());
        scope_put(scope,"s_rsa",new C_RSA());
        
        String js_script=S_File_Text.Read(args[0], "utf-8",10000);
        ct.evaluateString(scope, js_script, null, 1, null);

//        JavaMain.pSYS.js_functon("call_back_job", "a1");        
    }
    
    public static void scope_put(Scriptable scope,String key,Object pObj){
        
        Object pObj2 = Context.javaToJS(pObj, scope);
        ScriptableObject.putProperty(scope,key, pObj2);
    }
}
