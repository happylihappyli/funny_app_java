package Tools;

import antlr_js.ECMAScriptBaseVisitor;
import antlr_js.ECMAScriptParser;
import antlr_js.ECMAScriptParser.*;
import funnyai.JavaMain;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.TreeMap;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author happyli
 */
public class MyVisitor extends ECMAScriptBaseVisitor{

    public MyVisitor pParent=null;
    public TreeMap pMap = new TreeMap();
    
    
    @Override 
    public Object visitVariableDeclaration(
            ECMAScriptParser.VariableDeclarationContext ctx) {
        
        TerminalNode pNode1=ctx.Identifier();
        InitialiserContext pInit=ctx.initialiser();
        
        String varName=pNode1.getText();
        String Value=pInit.getText();
        

        Object pObj=visitChildren(pInit);
        if (pObj==null){
            out.println("break");
        }
//        if (JavaMain.bDebug){
//            out.println(pObj.getClass().getName());
//        }

        switch(pObj.getClass().getName().toLowerCase()){
            case "java.lang.string":
                if ("[]".equals((String)pObj)){
                    pObj=new ArrayList();
                    this.pMap.put(varName, pObj);
                }else{
                    this.pMap.put(varName, pObj);
                }
                break;
            case "java.lang.double":
                this.pMap.put(varName, pObj);
                break;
            default:
                this.pMap.put(varName, pObj);
                break;
            
        }
        return pObj; 
    }
    
    
    @Override
    public Object visitLiteral(ECMAScriptParser.LiteralContext ctx) {
        return ctx.getText();
    }
    
    public Object parse_single_expression(SingleExpressionContext ctx){
        
        switch(ctx.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MultiplicativeExpressionContext":
                return visitMultiplicativeExpression((ECMAScriptParser.MultiplicativeExpressionContext) ctx);
                
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                Object pObj=visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext) ctx);
                return pObj;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                return visitIdentifierExpression((ECMAScriptParser.IdentifierExpressionContext) ctx);
            case "antlr_js.ECMAScriptParser$AssignmentOperatorExpressionContext":
                return this.visitAssignmentOperatorExpression((AssignmentOperatorExpressionContext) ctx);
            case "antlr_js.ECMAScriptParser$EqualityExpressionContext":
                return visitEqualityExpression((EqualityExpressionContext)ctx);
            case "antlr_js.ECMAScriptParser$LiteralExpressionContext":
                return this.visitLiteralExpression((LiteralExpressionContext) ctx);
            case "antlr_js.ECMAScriptParser$AssignmentExpressionContext":
                return visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext)ctx);
            
            case "antlr_js.ECMAScriptParser$AdditiveExpressionContext":
                AdditiveExpressionContext p=(AdditiveExpressionContext)ctx;
                return visitAdditiveExpression(p);
            case "antlr_js.ECMAScriptParser$MemberDotExpressionContext":
                return this.visitMemberDotExpression((MemberDotExpressionContext) ctx);
            case "antlr_js.ECMAScriptParser$MemberIndexExpressionContext":
                return this.visitMemberIndexExpression((MemberIndexExpressionContext) ctx);
            case "antlr_js.ECMAScriptParser$ParenthesizedExpressionContext":
                return this.visitParenthesizedExpression((ParenthesizedExpressionContext) ctx);
            
            default:
                if (JavaMain.bDebug){
                    out.println(ctx.getClass().getName());
                }
                return visitChildren(ctx);
        }
    }
    
    
    @Override
    public Object visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
        AssignmentOperatorContext pOperator=ctx.assignmentOperator();
        SingleExpressionContext p1=ctx.singleExpression(0);
        SingleExpressionContext p2=ctx.singleExpression(1);
        
        String Name=p1.getText();
        Object pObj1=this.parse_single_expression(p1);
        Object pObj2=this.parse_single_expression(p2);
        switch(pOperator.getText()){
            case "+=":
                Double sum1=(Double)pObj1+(Double)pObj2;
                this.pMap.put(Name,sum1);
                break;
            case "-=":
                Double sum2=(Double)pObj1-(Double)pObj2;
                this.pMap.put(Name,sum2);
                break;
            default:
                out.println("test");
                break;
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitLiteralExpression(ECMAScriptParser.LiteralExpressionContext ctx) {
        LiteralContext p1=ctx.literal();
        String value=p1.getText();
        
        if (value.startsWith("\"")){
            return value;//.substring(1, value.length()-1);
        }
        if ("null".equals(value)){
            return null;
        }
        //if (JavaMain.bDebug) out.println(value);
        return Double.parseDouble(value);// value;
    }
    @Override
    public Object visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
        TerminalNode p=(TerminalNode)ctx.getChild(1);
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        
        Object express1=parse_single_expression(pLeft);
        Object express2=parse_single_expression(pRight);
        
        switch(p.getText()){
            case  "==":
                return (express1 == express2);
            case "!==":
                if ("null".equals(express2)){
                    return (express1 != null);
                }else{
                    return (express1 != express2);
                }
            default:
                out.println("error visitEqualityExpression");
                return false;
        }
    }
    
    @Override
    public Object visitExpressionStatement(ECMAScriptParser.ExpressionStatementContext ctx) {
        
        return visitChildren(ctx);
    }

    
    @Override
    public Object visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx) {
        String key=ctx.getText();
        Object pObj=get_var(key);
        if (pObj!=null){
            return pObj;
        }
        return visitChildren(ctx);
    }
    
    public Object get_var(Object pObj){
        
        switch (pObj.getClass().getName()){
            case "java.lang.Double":
                return pObj;
            case "java.lang.String":
                String key=(String)pObj;
                if (key.startsWith("\"")){
                    return key.substring(1,key.length()-1);
                }

                if (this.pMap.containsKey(key)){
                    return this.pMap.get(key);
                }
                if (pParent!=null && pParent.pMap.containsKey(key)){
                    return pParent.pMap.get(key);
                }
            default:
                break;
        }
        return null;
    }
    
    @Override
    public Object visitIfStatement(ECMAScriptParser.IfStatementContext ctx) {
        ECMAScriptParser.ExpressionSequenceContext pCondition=ctx.expressionSequence();
        
        ECMAScriptParser.StatementContext pIf=ctx.statement(0);
        ECMAScriptParser.StatementContext pElse=ctx.statement(1);
        
        Object pObj=this.visitExpressionSequence(pCondition);
        
        boolean iResult=false;
        switch (pObj.getClass().getName()){
            case "java.lang.Boolean":
                iResult=(boolean)pObj;
                break;
            default:
                if (JavaMain.bDebug) out.println(pObj.getClass().getName());
                break;
        }
        if (iResult){
            return visitChildren(pIf);
        }else{
            return visitChildren(pElse);
        }
    }
    
    
    @Override
    public Object visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        boolean bLeft=(boolean) parse_single_expression(pLeft);
        boolean bRight=(boolean) parse_single_expression(pRight);
        return bLeft && bRight;
    }
    
    
    @Override
    public Object visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        boolean bLeft=(boolean) parse_single_expression(pLeft);
        boolean bRight=(boolean) parse_single_expression(pRight);
        return bLeft || bRight;
    }
    
    @Override
    public Object visitParenthesizedExpression(
            ECMAScriptParser.ParenthesizedExpressionContext ctx) {
        ExpressionSequenceContext p=ctx.expressionSequence();
        return this.visitExpressionSequence(p);
    }
    
    @Override
    public Object visitExpressionSequence(ECMAScriptParser.ExpressionSequenceContext ctx) {
        SingleExpressionContext pExpression=ctx.singleExpression(0);
        
        switch(pExpression.getClass().getName()){
            case "antlr_js.ECMAScriptParser$AdditiveExpressionContext":
                AdditiveExpressionContext p=(AdditiveExpressionContext)pExpression;
                return visitAdditiveExpression(p);
            case "antlr_js.ECMAScriptParser$LogicalAndExpressionContext":
                return this.visitLogicalAndExpression((LogicalAndExpressionContext)pExpression);
            
            case "antlr_js.ECMAScriptParser$ParenthesizedExpressionContext":
                return this.visitParenthesizedExpression((ParenthesizedExpressionContext)pExpression);
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                return visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext) pExpression);
                
            case "antlr_js.ECMAScriptParser$AssignmentExpressionContext":
                Object pObj=this.visitAssignmentExpression((AssignmentExpressionContext)pExpression);
                return pObj;
            case "antlr_js.ECMAScriptParser$EqualityExpressionContext":
                return visitEqualityExpression((EqualityExpressionContext)pExpression);
            case "antlr_js.ECMAScriptParser$MultiplicativeExpressionContext":
                return this.visitMultiplicativeExpression((MultiplicativeExpressionContext)pExpression);
            case "antlr_js.ECMAScriptParser$LiteralExpressionContext":
                return this.visitLiteralExpression((LiteralExpressionContext)pExpression);
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                return this.visitIdentifierExpression((IdentifierExpressionContext) pExpression);
            case "antlr_js.ECMAScriptParser$RelationalExpressionContext":
                return this.visitRelationalExpression((RelationalExpressionContext) pExpression);
            case "antlr_js.ECMAScriptParser$AssignmentOperatorExpressionContext":
                return this.visitAssignmentOperatorExpression((AssignmentOperatorExpressionContext) pExpression);
            case "antlr_js.ECMAScriptParser$PostIncrementExpressionContext":
                return this.visitPostIncrementExpression((PostIncrementExpressionContext) pExpression);
            default:
                if (JavaMain.bDebug){
                    out.println(pExpression.getClass().getName());
                }
                break;
        }
        
        if (ctx.children.size()>1){
            return "0";
        }else{
            Object pObj=visitChildren(pExpression);
            return pObj;
        }
    }
    
    
    
    
    @Override
    public Object visitPostIncrementExpression(ECMAScriptParser.PostIncrementExpressionContext ctx) {
        SingleExpressionContext p=ctx.singleExpression();
        String Name=p.getText();
        Double pObj=(Double) this.get_var(Name);
        pObj+=1;
        if (this.pMap.containsKey(Name)){
            pMap.put(Name, pObj);
        }else if (pParent.pMap.containsKey(Name)){
            pParent.pMap.put(Name, pObj);
        }else{
            out.println("error");
        }
        return null;// visitChildren(ctx);
    }

    
    
    @Override
    public Object visitRelationalExpression(ECMAScriptParser.RelationalExpressionContext ctx) {
        SingleExpressionContext p1=ctx.singleExpression(0);
        SingleExpressionContext p2=ctx.singleExpression(1);
        Object express1=this.parse_single_expression(p1);
        Object express2=this.parse_single_expression(p2);
        
        TerminalNode p3=(TerminalNode) ctx.getChild(1);
        
        if (JavaMain.bDebug){
            out.println(p1.getText());
            out.println(p2.getText());
            out.println(p3.getText());
        }
        
        Double v1=0.0;
        
        switch (express1.getClass().getName()){
            case "java.lang.Double":
                v1=(Double)express1;
                break;
            default:
                v1=Double.parseDouble((String)express1);
                break;
        }
        Double v2=0.0;
        if (express2==null){
            out.println("stop");
        }
        switch (express2.getClass().getName()){
            case "java.lang.Double":
                v2=(Double)express2;
                break;
            case "java.lang.Integer":
                v2=(Integer)express2+0.0;
                break;
            default:
                v2=Double.parseDouble((String)express2);
                break;
        }
        switch(p3.getText()){
            case "<":
                if (v1==null || v2==null){
                    out.println("stop");
                }
                return v1<v2;
            case "<=":
                return v1<=v2;
            case ">":
                return v1>v2;
            case ">=":
                return v1>=v2;
        }
        
        return visitChildren(ctx);
    }
    
    
    @Override
    public Object visitAssignmentExpression(ECMAScriptParser.AssignmentExpressionContext ctx) {
        
        ECMAScriptParser.SingleExpressionContext pLeft = ctx.singleExpression(0);
        ECMAScriptParser.SingleExpressionContext pRight = ctx.singleExpression(1);
        String str1=pLeft.getText();
        Object pObj=parse_single_expression(pRight);
        this.pMap.put(str1,pObj);
        return "";
    }
    
    
    @Override
    public Object visitFunctionDeclaration(ECMAScriptParser.FunctionDeclarationContext ctx) {
        TerminalNode id=ctx.Identifier();
        String function =id.getText();
        this.pMap.put("function:"+function,ctx);
        return null;
    }
    
    @Override
    public Object visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {

        this.pMap.put("function:0",ctx);
        return null;//visitChildren(ctx);
    }
    
    
    @Override
    public Object visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {
        out.println(ctx.getText()); 
        ExpressionSequenceContext p1=ctx.expressionSequence();
        Object pObj=this.visitExpressionSequence(p1);
        //out.println(pObj.toString());
        StatementContext p2=ctx.statement();

        
        while((boolean)pObj){
            visitChildren(p2);
            pObj=this.visitExpressionSequence(p1);
        }
        return null;
    }
    
    
    public Object call_function(ECMAScriptParser.FunctionDeclarationContext ctx,
            ECMAScriptParser.ArgumentListContext pList) {
        //if (JavaMain.bDebug) out.println("call function");
        Function_Call pFun=new Function_Call();
        pFun.pList=pList;
        pFun.Init(ctx,this);
        pFun.visitChildren(ctx);
        return pFun.pReturn;
    }
    
    public Object call_function(ECMAScriptParser.FunctionDeclarationContext ctx,
            ArrayList pList) {
        //if (JavaMain.bDebug) out.println("call function");
        Function_Call pFun=new Function_Call();
        pFun.pList2=pList;
        pFun.Init2(ctx,this);
        pFun.visitChildren(ctx);
        return pFun.pReturn;
    }
    
    public Object call_function2(ECMAScriptParser.FunctionExpressionContext ctx,
            ArrayList pList) {
        //if (JavaMain.bDebug) out.println("call function");
        Function_Call2 pFun=new Function_Call2();
        pFun.pList=pList;
        pFun.Init(ctx,this);
        pFun.visitChildren(ctx);
        return pFun.pReturn;
    }
    
    
    @Override
    public Object visitSourceElements(ECMAScriptParser.SourceElementsContext ctx) {
        Object pObj=visitChildren(ctx);
        return pObj;
    }

    
    @Override
    public Object visitSourceElement(ECMAScriptParser.SourceElementContext ctx) {
        StatementContext p=ctx.statement();
        if (p!=null){
//            if (JavaMain.bDebug){
//                out.println(p.getText());
//            }
        }
        Object pObj=visitChildren(ctx);
        return pObj;
    }
    
    
    @Override
    public Object visitStatement(ECMAScriptParser.StatementContext ctx) {
        String value=ctx.getText();
//        if (value.startsWith("sum+")){
//            out.println("stop");
//        }
        return visitChildren(ctx);
    }
    
    
    @Override
    public Object visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
        ECMAScriptParser.ExpressionSequenceContext p=ctx.expressionSequence();
        Object pObj=this.visitExpressionSequence(p);//visitChildren(ctx);;
        
        return pObj;
    }
    
    /**
     * 加法
     * @param ctx
     * @return 
     */
    @Override 
    public Object visitAdditiveExpression(ECMAScriptParser.AdditiveExpressionContext ctx) {
        ECMAScriptParser.SingleExpressionContext left = ctx.singleExpression(0);
        ECMAScriptParser.SingleExpressionContext right = ctx.singleExpression(1);
        TerminalNode p=(TerminalNode) ctx.children.get(1);
        String operate =p.getText();
        
        Object express1=parse_single_expression(left);
        Object express2=parse_single_expression(right);
        
        switch(operate){
            case "-":
                if (express1==null){
                    out.println("test");
                }
                switch (express1.getClass().getName()){
                    case "java.lang.Double":
                        return (Double)express1-(Double)express2;
                    default:
                        out.println("error");
                        break;
                }
            default:
                switch (express1.getClass().getName()){
                    case "java.lang.Double":
                        switch(express2.getClass().getName()){
                            case "java.lang.String":
                                return express1+(String)express2;
                            case "java.lang.Double":
                                return (Double)express1+(Double)express2;
                            default:
                                return (Double)express1+(Double)express2;
                        }
                    case "java.lang.String":
                        Object pObj2=this.get_var(express2);
                        if (pObj2==null){
                           out.println("stop");
                        }
                        return express1+pObj2.toString();
                    default:
                        out.println("error");
                        break;
                }
        }
        return "";
    }
    
    
    /**
     * 乘法
     * @param ctx
     * @return 
     */
    @Override 
    public Object visitMultiplicativeExpression(ECMAScriptParser.MultiplicativeExpressionContext ctx) { 
        ECMAScriptParser.SingleExpressionContext left = ctx.singleExpression(0);// .singleExpression(0));
        ECMAScriptParser.SingleExpressionContext right = ctx.singleExpression(1);
        TerminalNode p=(TerminalNode) ctx.children.get(1);
        String operate =p.getText();
        Object express1=parse_single_expression(left);
        Object express2=parse_single_expression(right);
        switch(operate){
            case "/":
                if (express1==null){
                    out.println("stop");
                }
                if (express2==null){
                    out.println("stop");
                }
                return ((Double)express1)/((Double)express2);
            default:
                if (express1==null){
                    out.println("stop");
                }
                if (express2==null){
                    out.println("stop");
                }
                return ((Double)express1)* ((Double)express2);
        }
    }
    
    
    
    
    @Override
    public Object visitArguments(ECMAScriptParser.ArgumentsContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Object visitArgumentsExpression(ECMAScriptParser.ArgumentsExpressionContext ctx) {
        ECMAScriptParser.SingleExpressionContext p1=ctx.singleExpression();
        ECMAScriptParser.ArgumentsContext p2= ctx.arguments();
        ECMAScriptParser.ArgumentListContext pList=p2.argumentList();
        String value=pList.getText();
        
        String function=p1.getText();

        return Function_SYS.call(function,value,this,pList);
    }
    
    
    @Override
    public Object visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
        IdentifierNameContext pName=ctx.identifierName();
        SingleExpressionContext pExpression=ctx.singleExpression();
        
        String strProperty=pName.getText();
        if (JavaMain.bDebug){
            out.println(strProperty);
        }
        String ObjectName=pExpression.getText();
        if (JavaMain.bDebug){
            out.println(ObjectName);
        }
        
        if (JavaMain.bDebug){
            out.println(pExpression.getClass().getName());
        }
        
        Object pResult;
        switch(pExpression.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MemberIndexExpressionContext":
                pResult=this.visitMemberIndexExpression((MemberIndexExpressionContext) pExpression);
                break;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                pResult=this.visitIdentifierExpression((IdentifierExpressionContext) pExpression);
                break;
            default:
                pResult=visitChildren(pExpression);
                break;
        }
        
        if (pResult==null){
            Object pObj=this.get_var(ObjectName);
            if (pObj==null){
                return visitChildren(ctx);
            }else{
                switch (pObj.getClass().getName()){
                    case "org.json.JSONObject":
                        Object pObj2=((org.json.JSONObject)pObj).get(strProperty);
                        return pObj2;
                    default:
                        if (JavaMain.bDebug){
                            out.println(pObj.getClass().getName());
                        }
                        break;
                }
            }
        }else{
            switch (pResult.getClass().getName()){
                case "org.json.JSONObject":
                    Object pObj2=((org.json.JSONObject)pResult).get(strProperty);
                    return pObj2;
                case "[Ljava.lang.String;":
                    switch(strProperty){
                        case "length":
                            return ((String[])pResult).length;
                        default:
                            if (JavaMain.bDebug){
                                out.println(pResult.getClass().getName());
                            }  
                    }
                default:
                    if (JavaMain.bDebug){
                        out.println(pResult.getClass().getName());
                    }
            }
        }
        if (JavaMain.bDebug){
            out.println(ctx.getText());
        }
        return visitChildren(ctx);
    }

    @Override
    public Object visitMemberIndexExpression(
            ECMAScriptParser.MemberIndexExpressionContext ctx) {
        SingleExpressionContext p1=ctx.singleExpression();
        ExpressionSequenceContext p2=ctx.expressionSequence();
        
//        if (JavaMain.bDebug){
//            out.println(p1.getText());
//        }
//        if (JavaMain.bDebug){
//            out.println(p2.getText());
//        }
        
        Object p1_result=this.parse_single_expression(p1);
        Object p2_result=this.visitExpressionSequence(p2);
        
        switch(p1_result.getClass().getName()){
            case "org.json.JSONArray":
                org.json.JSONArray pArray=(org.json.JSONArray) p1_result;
                int index=Integer.parseInt(p2.getText());
                return pArray.get(index);
            case "[Ljava.lang.String;":
                int index2=Integer.valueOf((String)p2_result);
                return ((String[])p1_result)[index2];
            default:
                if (JavaMain.bDebug){
                    out.println(p1_result.getClass().getName());
                }
                return visitChildren(ctx);
        }
    }
    
    @Override 
    public Object visitArrayLiteral(ECMAScriptParser.ArrayLiteralContext ctx) {
        return ctx.getText();
    }
}
