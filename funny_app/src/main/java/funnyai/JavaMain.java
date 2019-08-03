/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.io.S_File_Text;
import com.funnyai.io.S_file;
import com.funnyai.net.S_net;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author happyli
 */
public class JavaMain {
    public static void main(String[] args){
        Context ct = Context.enter(); 
        Scriptable scope = ct.initStandardObjects(); 

        scope_put(scope,"s_out",System.out);
        scope_put(scope,"s_file",S_file.main);
        scope_put(scope,"s_net",S_net.main);
        
        scope_put(scope,"s_sys",new SYS(args));
        scope_put(scope,"s_db",new C_DB());
        scope_put(scope,"s_json",new C_JSON());
        
        String js_script=S_File_Text.Read(args[0], "utf-8",10000);
        ct.evaluateString(scope, js_script, null, 1, null);
    }
    
    public static void scope_put(Scriptable scope,String key,Object pObj){
        
        Object pObj2 = Context.javaToJS(pObj, scope);
        ScriptableObject.putProperty(scope,key, pObj2);
    }
}
