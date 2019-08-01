/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funnyai;

import com.funnyai.io.S_File_Text;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import funnyai.C_Test;
/**
 *
 * @author happyli
 */
public class JavaMain {
    public static void main(String[] args){
        Context ct = Context.enter(); 
        Scriptable scope = ct.initStandardObjects(); 
        Object out = Context.javaToJS(System.out, scope); 
        ScriptableObject.putProperty(scope, "out", out);
        
        //Object test = Context.javaToJS(new C_Test(), scope); 
        //ScriptableObject.putProperty(scope, "test", test);
        
        String js_script=S_File_Text.Read(args[0], "utf-8",10000);
        ct.evaluateString(scope, js_script, null, 1, null);
    }
}
