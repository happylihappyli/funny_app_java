package Tools;

import antlr_js.ECMAScriptParser;
import com.funnyai.io.S_file;
import com.funnyai.net.Old.S_Net;
import com.funnyai.string.Old.S_Strings;
import funnyai.JavaMain;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        switch(function){
            case "pow":
                ECMAScriptParser.SingleExpressionContext left = pList.singleExpression(0);// .singleExpression(0));
                ECMAScriptParser.SingleExpressionContext right = pList.singleExpression(1);
                Double express1=(Double) pParent.parse_single_expression(left);
                Double express2=(Double) pParent.parse_single_expression(right);
                return Math.pow(express1, express2);
                
            case "round":
                ECMAScriptParser.SingleExpressionContext left1 = pList.singleExpression(0);
                Double express3=(Double) pParent.parse_single_expression(left1);
                return Math.round(express3);
            default:
                out.println("没有这个函数:s_math."+function+"!");
                break;
        }
        return null;
    }
    
    public static Object out_call(
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        //Object pObj;
        switch(function){
            case "println":
                
                switch(value.getClass().getName()){
                    case "java.lang.String":
                        value=pParent.string_process((String)value);
                        out.println(value);
                        break;
                    case "java.lang.Integer":
                        out.println(value);
                        break;
                    case "java.lang.Double":
                        out.println(value);
                        break;
                    default:
                        out.println(value);
                        break;
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
            Object value,
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
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            case "http_get":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                return S_Net.http_get((String)value);
            default:
                out.println("没有这个函数:s_net."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object string_call(
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
           case "urlencode":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(String) pObj;
                }
                return S_Strings.URL_Encode((String)value);
            default:
                out.println("没有这个函数:s_string."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object Array_call(
            MyVisitor pParent,
            String function,
            Object value,
            Object pObj2,
            ECMAScriptParser.ArgumentListContext pList){
        
        switch(function){
            case "reduce":
                return reduce_call(function,pParent,pObj2,pList);
            case "map":
                return map_call(function,pParent,pObj2,pList);
            case "push":
                ArrayList pList2=(ArrayList)pObj2;
                pList2.add(value);
                return pList2;
            case "sort":
                return sort_call(function,pParent,pObj2,pList);
            default:
                out.println("test");
                break;
        }
        return null;
    }
    
    
    public static Object reduce_call(
            String function, 
            MyVisitor pParent, 
            Object pObj2, 
            ECMAScriptParser.ArgumentListContext pList) {
        
        ECMAScriptParser.SingleExpressionContext p1=pList.singleExpression(0);
        ECMAScriptParser.SingleExpressionContext p2=pList.singleExpression(1);
        
        switch(p1.getClass().getName()){
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                
                Object p2_result=pParent.parse_single_expression(p2);
                String name=(String)p1.getText();
                ECMAScriptParser.FunctionDeclarationContext pFun1=
(ECMAScriptParser.FunctionDeclarationContext)pParent.get_var("function:"+name);// .pMap.get();
                if (pFun1==null){
                    out.println("no function:"+function);
                    return "no function";
                }else{
                    BinaryOperator pFun2=(BinaryOperator) (Object x, Object y) -> {
                        Object pObj=pParent.get_var("reduce_sum");
                        int reduce_sum=0;
                        if (pObj!=null){
                            reduce_sum=(int)pObj;
                        }
                        ArrayList pList1 = new ArrayList();
                        pList1.add(x);
                        pList1.add(y);
                        reduce_sum+=1;
                        pParent.put_var("reduce_sum", reduce_sum);
                        
                        double mReturn = (Double) pParent.call_function(pFun1, pList1);
                        //out.println(reduce_sum+"="+mReturn);
                        return mReturn;
                    };

                    ArrayList pList4=(ArrayList)pObj2;
                    Object pResult3=pList4.stream().reduce(p2_result, pFun2);
                    
                    Object pObj=pParent.get_var("reduce_sum");
                    out.println(pObj+"");
                    return pResult3;
                }
            case "antlr_js.ECMAScriptParser$FunctionExpressionContext":
                pParent.visitFunctionExpression((ECMAScriptParser.FunctionExpressionContext) p1);

                ECMAScriptParser.FunctionExpressionContext pFun2=
(ECMAScriptParser.FunctionExpressionContext)pParent.get_var("function:0");
                if (pFun2==null){
                    out.println("no function:"+function);
                    return "";
                }else{
                    BinaryOperator pFun4=(BinaryOperator) (Object x, Object y) -> {
                        ArrayList pList1 = new ArrayList();
                        pList1.add(x);
                        pList1.add(y);
                        double mReturn = ((Double) pParent.call_function2(pFun2, pList1)).intValue();
                        return mReturn;
                    };

                    ArrayList pList4=(ArrayList)pObj2;
                    Object pResult=pList4.stream().reduce(0, pFun4);
                    return pResult;
                }
            default:
                out.println("error");
                return null;
        }
    }
    
    public static Object map_call(
            String function,
            MyVisitor pParent,
            Object pObj2,
            ECMAScriptParser.ArgumentListContext pList) {
        
        ECMAScriptParser.SingleExpressionContext pFun=pList.singleExpression(0);
        switch(pFun.getClass().getName()){
            case "antlr_js.ECMAScriptParser$FunctionExpressionContext":
                pParent.visitFunctionExpression((ECMAScriptParser.FunctionExpressionContext) pFun);

                ECMAScriptParser.FunctionExpressionContext pFun2=
                    (ECMAScriptParser.FunctionExpressionContext)pParent.get_var("function:0");
                if (pFun2==null){
                    out.println("no function:"+function);
                    return "";
                }else{
                    Function pFun4=(Function) (Object s) -> {
                        ArrayList pList1 = new ArrayList();
                        pList1.add(s);
                        Object pObj=pParent.call_function2(pFun2, pList1);
                        
                        double mReturn =0;
                        switch (pObj.getClass().getName()){
                            case "java.lang.Double":
                                mReturn=(Double)pObj;
                                break;
                            case "java.lang.Integer":
                                mReturn=(Integer)pObj+0.0;
                                break;
                            default:
                                mReturn=Double.parseDouble((String)pObj);
                                break;
                        }
                        return mReturn;
                    };

                    ArrayList pList4=(ArrayList)pObj2;
                    ArrayList<Object> results = new ArrayList<>();
                    pList4.stream().map(pFun4).forEach(s -> results.add(s));
                    
                    double sum=0;
                    for (int i=0;i<results.size();i++){
                        sum+=(Double)results.get(i);
                    }
                    out.println(sum+"");
                    return results;
                }
            default:
                out.println("error");
                return null;
        }
    }
    
    
    public static Object sort_call(
            String function,
            MyVisitor pParent,
            Object pObj2,
            ECMAScriptParser.ArgumentListContext pList) {
        
        ECMAScriptParser.SingleExpressionContext pFun2=pList.singleExpression(0);
        switch(pFun2.getClass().getName()){
            case "antlr_js.ECMAScriptParser$FunctionExpressionContext":
                pParent.visitFunctionExpression((ECMAScriptParser.FunctionExpressionContext) pFun2);

                ECMAScriptParser.FunctionExpressionContext pFun5=
                        (ECMAScriptParser.FunctionExpressionContext)pParent.get_var("function:0");
                        //pParent.pMap.get("function:0");
                if (pFun5==null){
                    out.println("no function:"+function);
                    return "";
                }else{
                    Comparator pFun6=new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            ArrayList pList=new ArrayList();
                            pList.add(o1);
                            pList.add(o2);
                            return ((Double)pParent.call_function2(pFun5,pList)).intValue();
                        }
                    };
                    ArrayList pList3=(ArrayList)pObj2;
                    pList3.sort(pFun6);
                    return pList3;
                }
            default:
                out.println("error");
                return null;
        }

    }
    
    
    public static Object default_call(
            String ObjectName,
            String function,
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        
        Object pObj2=pParent.get_var(ObjectName);
        if (pObj2!=null){
            switch(pObj2.getClass().getName()){
                case "java.util.ArrayList":
                    return Array_call(pParent,function,value,pObj2,pList);
                case "java.lang.String":
                    switch(function){
                        case "split":
                            String strLine=(String)pObj2;
                            String value2=pList.getText();
                            value2=(String)pParent.get_var(value2);
                            return strLine.split(value2);
                    }
                    break;
            }
        }else{
            out.println("没有这个对象:"+ObjectName);
        }
        return null;
    }
    public static Object json_call(
            String function,
            Object pObj2,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        String value=(String)pObj2;
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
            Object value,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        Object pObj;
        switch(function){
            
            case "exit":
                pObj=pParent.get_var(value);
                if (pObj!=null){
                    value=(Double) pObj;
                }
                System.exit(((Double)value).intValue());
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
                    int index=((Double) pParent.parse_single_expression(pKey)).intValue();
                    //Integer.parseInt
                    return JavaMain.sys_args[index+1];
                }
            default:
                out.println("没有这个函数:s_sys."+function+"!");
                break;
        }
        return null;
    }
    
    
    public static Object call(String function,
            Object pValue,
            MyVisitor pParent,
            ECMAScriptParser.ArgumentListContext pList){
        
        Object pObj=pValue;//pParent.get_var(pValue);

        String[] strSplit=function.split("\\.");
        if (strSplit.length>1){
            switch(strSplit[0].toLowerCase()){
                case "s_math":
                    return math_call(strSplit[1],pObj,pParent,pList);
                case "s_out":
                    return out_call(strSplit[1],pObj,pParent,pList);
                case "s_file":
                    return file_call(strSplit[1],pObj,pParent,pList);
                case "s_net":
                    return net_call(strSplit[1],pObj,pParent,pList);
                case "s_string":
                    return string_call(strSplit[1],pObj,pParent,pList);
                case "s_sys":
                    return sys_call(strSplit[1],pValue,pParent,pList);
                case "s_json":
                    return json_call(strSplit[1],pObj,pParent,pList);
                case "math":
                    return math_call(strSplit[1],pObj,pParent,pList);
                default:
                    return default_call(strSplit[0],strSplit[1],pObj,pParent,pList);
            }
        }else{
            switch(function){
                case "parseFloat":
                    ECMAScriptParser.SingleExpressionContext left = pList.singleExpression(0);
                    pObj=pParent.parse_single_expression(left);
                    return Double.parseDouble((String)pObj);
                default:
                    ECMAScriptParser.FunctionDeclarationContext pFun=
(ECMAScriptParser.FunctionDeclarationContext)pParent.get_var("function:"+function);
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
