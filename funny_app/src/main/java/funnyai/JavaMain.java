/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.io.S_File_Text;
import com.funnyai.io.S_file_sub;
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
        SYS pSYS=new SYS(args);
        
        Object out = Context.javaToJS(System.out, scope); 
        ScriptableObject.putProperty(scope, "out", out);
        
        Object myfile = Context.javaToJS(com.funnyai.io.S_file_sub.main, scope);
        ScriptableObject.putProperty(scope, "file", myfile);
        
        Object sys = Context.javaToJS(pSYS, scope);
        ScriptableObject.putProperty(scope, "sys", sys);
        
        String js_script=S_File_Text.Read(args[0], "utf-8",10000);
        ct.evaluateString(scope, js_script, null, 1, null);
    }
}
