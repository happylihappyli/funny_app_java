package Tools;

import antlr_js.ECMAScriptParser;
import com.funnyai.io.S_file;
import com.funnyai.net.Old.S_Net;
import com.funnyai.string.Old.S_Strings;
import funnyai.JavaMain;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author happyli
 */
public class Function_SYS {
    public static Object math_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        switch(function){
            case "pow":
                ECMAScriptParser.SingleExpressionContext left = pList.singleExpression(0);// .singleExpression(0));
                ECMAScriptParser.SingleExpressionContext right = pList.singleExpression(1);
                Double express1=(Double) pParent.parse_single_expression(left);
                Double express2=(Double) pParent.parse_single_expression(right);
                return Math.pow(express1, express2);
            default:
                out.println("没有这个函数:s_math."+function+"!");
                break;
        }
        return null;
    }
    
    public static Object out_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "println":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    out.println(pObj.toString());
                }
                return null;
            default:
                out.println("没有这个函数:s_out."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object file_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "read_begin":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);// .singleExpression(0));
                    ECMAScriptParser.SingleExpressionContext pFile = pList.singleExpression(1);
                    String key=(String) pParent.parse_single_expression(pKey);
                    String file=(String) pParent.parse_single_expression(pFile);
                    JavaMain.pFile.read_begin(pParent,key,file);
                    return null;
                }
            case "read_line":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);
                    String key=(String) pParent.parse_single_expression(pKey);
                    
                    return JavaMain.pFile.read_line(pParent,key);
                }
            case "close":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);
                    String key=(String) pParent.parse_single_expression(pKey);
                    
                    JavaMain.pFile.close(pParent,key);
                    return null;
                }
            default:
                out.println("没有这个函数:s_out."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object net_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "http_get":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                return S_Net.http_get(value);
            default:
                out.println("没有这个函数:s_net."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object string_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
           case "urlencode":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                return S_Strings.URL_Encode(value);
            default:
                out.println("没有这个函数:s_string."+function+"!");
                break;
        }
        return null;
    }
    
    public static Object json_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
           case "parse":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                if (value.startsWith("{")){
                    return new JSONObject(value);
                }else if (value.startsWith("[")){
                    return new JSONArray(value);
                }else{
                    return null;
                }
            default:
                out.println("没有这个函数:s_json."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object sys_call(
            String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            
            case "exit":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                System.exit(Integer.valueOf((String)value));
            case "sleep":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                {
                    try {
                        Thread.sleep(Integer.parseInt((String)value)*1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Function_SYS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return value;
            case "args":
                {
                    ECMAScriptParser.SingleExpressionContext pKey = pList.singleExpression(0);
                    int index=Integer.parseInt((String) pParent.parse_single_expression(pKey));
                    
                    return JavaMain.sys_args[index+1];
                }
            default:
                out.println("没有这个函数:s_sys."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object call(String function,
            String value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){

        String[] strSplit=function.split("\\.");
        if (strSplit.length>1){
            switch(strSplit[0]){
                case "s_math":
                    return math_call(strSplit[1],value,pParent,pList);
                case "s_out":
                    return out_call(strSplit[1],value,pParent,pList);
                case "s_file":
                    return file_call(strSplit[1],value,pParent,pList);
                case "s_net":
                    return net_call(strSplit[1],value,pParent,pList);
                case "s_string":
                    return string_call(strSplit[1],value,pParent,pList);
                case "s_sys":
                    return sys_call(strSplit[1],value,pParent,pList);
                case "s_json":
                    return json_call(strSplit[1],value,pParent,pList);
                default:
                    Object pObj=pParent.get_var(strSplit[0]);
                    if (pObj!=null){
                        switch(pObj.getClass().getName()){
                            case "java.util.ArrayList":
                                switch(strSplit[1]){
                                    case "push":
                                    {
                                        ArrayList pList2=(ArrayList)pObj;
                                        pList2.add(value);
                                        return pList2;
                                    }
                                    case "sort":
                                        ECMAScriptParser.SingleExpressionContext pFun=pList.singleExpression(0);
                                        if ("antlr_js.ECMAScriptParser$FunctionExpressionContext".equals(pFun.getClass().getName())){
                                            
                                            pParent.visitFunctionExpression((ECMAScriptParser.FunctionExpressionContext) pFun);
                                            
                                            ECMAScriptParser.FunctionExpressionContext pFun2=
                                                    (ECMAScriptParser.FunctionExpressionContext)
                                                    pParent.pMap.get("function:0");
                                            if (pFun2==null){
                                                out.println("no function:"+function);
                                                return "";
                                            }else{
                                                Comparator pFun3=new Comparator() {
                                                    @Override
                                                    public int compare(Object o1, Object o2) {
                                                        ArrayList pList=new ArrayList();
                                                        pList.add(o1);
                                                        pList.add(o2);
                                                        return (Integer)pParent.call_function2(pFun2,pList);
                                                    }
                                                };
                                                
                                                ArrayList pList2=(ArrayList)pObj;
                                                pList2.sort(pFun3);
                                                return pList2;
                                            }
                                        }else{
                                            out.println("error");
                                            return null;
                                        }

                                    default:
                                        out.println("test");
                                        break;
                                }
                            case "java.lang.String":
                                
                                switch(strSplit[1]){
                                    case "split":
                                        String strLine=(String)pObj;
                                        return strLine.split(value);
                                }
                                break;
                        }
                    }else{
                        out.println("没有这个对象:"+strSplit[0]);
                    }
            }
        }else{
            switch(function){
                case "parseFloat":
                    
                    ECMAScriptParser.SingleExpressionContext left = pList.singleExpression(0);
                    Object pObj=pParent.parse_single_expression(left);
                    
                    return pObj;
                default:
                    ECMAScriptParser.FunctionDeclarationContext pFun=
                            (ECMAScriptParser.FunctionDeclarationContext)
                            pParent.pMap.get("function:"+function);
                    if (pFun==null){
                        out.println("no function:"+function);
                    }else{
                        return pParent.call_function(pFun,pList);
                    }
                    break;
            }
        }
        
        return null;
    }
     
}
