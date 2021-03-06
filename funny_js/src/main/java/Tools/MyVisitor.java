package Tools;

import antlr_js.ECMAScriptBaseVisitor;
import antlr_js.ECMAScriptParser;
import antlr_js.ECMAScriptParser.*;
import funnyai.JavaMain;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author happyli
 */
public class MyVisitor extends ECMAScriptBaseVisitor{

    public MyVisitor pParent=null;
    public Function_Data pData=new Function_Data();
    
    
    @Override 
    public Object visitVariableDeclaration(
            ECMAScriptParser.VariableDeclarationContext ctx) {
        
        TerminalNode pNode1=ctx.Identifier();
        InitialiserContext pInit=ctx.initialiser();
        
        String varName=pNode1.getText();
        pData.pMap.put(varName,"");//定义变量，需要在当前上下文声明一个新的
        
        if (pInit==null){
            return null;
        }
        String Value=pInit.getText();

        Object pObj=visitChildren(pInit);
        if (pObj==null){
            out.println("break");
            pObj=visitChildren(pInit);
        }

        switch(pObj.getClass().getName().toLowerCase()){
            case "java.lang.string":
                if ("[]".equals((String)pObj)){
                    pObj=new ArrayList();
                    this.put_var(varName, pObj);
                }else{
                    this.put_var(varName, pObj);
                }
                break;
            case "java.lang.double":
                this.put_var(varName, pObj);
                break;
            default:
                this.put_var(varName, pObj);
                break;
            
        }
        return pObj; 
    }
    
    
    @Override
    public Object visitLiteral(ECMAScriptParser.LiteralContext ctx) {
        return ctx.getText();
    }
    public Object parse_single_expression_name(SingleExpressionContext ctx){
        
        switch(ctx.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MultiplicativeExpressionContext":
                return visitMultiplicativeExpression((ECMAScriptParser.MultiplicativeExpressionContext) ctx);
                
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                Object pObj=visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext) ctx);
                return pObj;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                return ctx.getText();
                //return visitIdentifierExpression((ECMAScriptParser.IdentifierExpressionContext) ctx);
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
    
    public void parse_single_expression_value_set(SingleExpressionContext ctx,Object pObj){
        switch(ctx.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MemberIndexExpressionContext":
                this.visitMemberIndexExpression_set((MemberIndexExpressionContext) ctx,pObj);
                break;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                this.put_var(ctx.getText(), pObj);
                break;
            default:
                if (JavaMain.bDebug){
                    //out.println(ctx.getClass().getName());
                }
                this.put_var(ctx.getText(), pObj);
        }
    }
    public Object parse_single_expression_value(SingleExpressionContext ctx){
        
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
            case "antlr_js.ECMAScriptParser$NewExpressionContext":
                return this.visitNewExpression((NewExpressionContext) ctx);
            default:
                if (JavaMain.bDebug){
                    out.println(ctx.getClass().getName());
                }
                return visitChildren(ctx);
        }
    }
    
    
    @Override
    public Object visitForVarStatement(ECMAScriptParser.ForVarStatementContext ctx) {
        
        VariableDeclarationListContext pVar=ctx.variableDeclarationList();
        ExpressionSequenceContext p1=ctx.expressionSequence(0);
        ExpressionSequenceContext p2=ctx.expressionSequence(1);
        StatementContext pState=ctx.statement();
        
        this.visitVariableDeclarationList(pVar);
        
        
        Object pObj=this.visitExpressionSequence(p1);
        
        boolean iResult=false;
        switch (pObj.getClass().getName()){
            case "java.lang.Boolean":
                iResult=(boolean)pObj;
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(pObj.getClass().getName());
                }
                break;
        }
        while (iResult){
            visitChildren(pState);
            this.visitExpressionSequence(p2);
            pObj=this.visitExpressionSequence(p1);
            iResult=(boolean)pObj;
        }
        return null;
    }
    
    
    @Override
    public Object visitForStatement(ECMAScriptParser.ForStatementContext ctx) {
        StatementContext pState=ctx.statement();
        
        ExpressionSequenceContext p1=ctx.expressionSequence(0);
        ExpressionSequenceContext pCondition=ctx.expressionSequence(1);
        ExpressionSequenceContext p3=ctx.expressionSequence(2);
        
//        if (JavaMain.bDebug){
//            out.println(pState.getText());
//            out.println(p1.getText());
//            out.println(pCondition.getText());
//            out.println(p3.getText());
//        }
        
        this.visitExpressionSequence(p1);
        Object pObj2=this.visitExpressionSequence(pCondition);
        
        boolean iResult=false;
        switch (pObj2.getClass().getName()){
            case "java.lang.Boolean":
                iResult=(boolean)pObj2;
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(pObj2.getClass().getName());
                }
                break;
        }
        
        while (iResult){
            visitChildren(pState);
            this.visitExpressionSequence(p3);
            
            pObj2=this.visitExpressionSequence(pCondition);
            iResult=false;
            switch (pObj2.getClass().getName()){
                case "java.lang.Boolean":
                    iResult=(boolean)pObj2;
                    break;
                default:
                    if (JavaMain.bDebug){
                        out.println(pObj2.getClass().getName());
                    }
                    break;
            }
        }
        return null;
    }
    
    @Override
    public Object visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
        AssignmentOperatorContext pOperator=ctx.assignmentOperator();
        SingleExpressionContext p1=ctx.singleExpression(0);
        SingleExpressionContext p2=ctx.singleExpression(1);
        
        String Name=p1.getText();
        Object pObj1=this.parse_single_expression_value(p1);
        Object pObj2=this.parse_single_expression_value(p2);
        if (pObj1==null){
            out.println("null break;");
            out.println("************");
            out.println(ctx.getText());
            out.println("************");
        }
        Double value1=0.0;
        Double value2=0.0;
        
        boolean bString=false;
        switch(pObj1.getClass().getName()){
            case "java.lang.String":
                bString=true;
                break;
            case "java.lang.Double":
                value1=((Double)pObj1);
                break;
            case "java.lang.Integer":
                value1=(Integer)pObj1+0.0;
                break;
        }
        
        switch(pObj2.getClass().getName()){
            case "java.lang.String":
                bString=true;
                break;
            case "java.lang.Double":
                value2=((Double)pObj2);
                break;
            case "java.lang.Integer":
                value2=(Integer)pObj2+0.0;
                break;
        }
        
        switch(pOperator.getText()){
            case "+=":
                if (bString){
                    String sum_str=(String)pObj1+(String)pObj2;
                    this.put_var(Name,sum_str);
                }else{
                    Double sum1=value1+value2;
                    this.put_var(Name,sum1);
                }
                break;
            case "-=":
                Double sum2=value1-value2;
                this.put_var(Name,sum2);
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
            return value.substring(1, value.length()-1);
        }
        if ("null".equals(value)){
            return null;
        }
        if ("false".equals(value)){
            return false;
        }
        if ("true".equals(value)){
            return true;
        }
        return Double.parseDouble(value);// value;
    }
    @Override
    public Object visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
        TerminalNode p=(TerminalNode)ctx.getChild(1);
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        
        Object express1=parse_single_expression_value(pLeft);
        Object express2=parse_single_expression_value(pRight);
        
        switch(p.getText()){
            case  "==":
                switch(express1.getClass().getName()){
                    case "java.lang.Double":
                        return Double.compare((Double)express1, (Double)express2) == 0;
                    case "java.lang.Integer":
                    case "java.lang.Long":
                        return (express1 == express2);
                    case "java.lang.String":
                        return ((String)express1).equals(express2);
                }
            case "!=":
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
//        if ("strSplit.length".equals(ctx.getText())){
//            out.println("stop");
//        }
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
    
    public void put_var(Function_Data pData2,String key,Object pObj){
        pData2.pMap.put(key,pObj);
    }
    
    public void put_var(String key,Object pObj){
        if (pData.pMap.containsKey(key)){
            pData.pMap.put(key,pObj);
            return ;
        }else{
            if (this.pParent!=null){
                if (this.pParent.pData.pMap.containsKey(key)){
                    pParent.pData.pMap.put(key,pObj);
                    return ;
                }else{
                    while(pParent.pParent!=null){
                        pParent=pParent.pParent;
                        if (pParent!=null && pParent.pData.pMap.containsKey(key)){
                            pParent.pData.pMap.put(key,pObj);
                            return ;
                        }
                    }
                }
            }
        }
        pData.pMap.put(key,pObj);
    }
    
    public Object get_var(Object pObj){
        if (pObj==null){
            return null;
        }
        switch (pObj.getClass().getName()){
            case "java.lang.Double":
                return pObj;
            case "java.lang.Integer":
                return pObj;
            case "java.lang.Long":
                return pObj;
            case "java.lang.String":
                String key=(String)pObj;
                if (key.startsWith("\"")){
                    key=key.substring(1,key.length()-1);
                    key=key.replace("\\\\", "\\");
                    return key;
                }
                
                if (pData.pMap.containsKey(key)){
                    return pData.pMap.get(key);
                }
                if (pParent!=null && pParent.pData.pMap.containsKey(key)){
                    return pParent.pData.pMap.get(key);
                }
                while(pParent!=null && pParent.pParent!=null){
                    pParent=pParent.pParent;
                    if (pParent!=null && pParent.pData.pMap.containsKey(key)){
                        return pParent.pData.pMap.get(key);
                    }
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
                if (JavaMain.bDebug){
                    out.println(pObj.getClass().getName());
                }
                break;
        }
        if (iResult){
            if (pIf!=null){
                Object pResult=visitChildren(pIf);
                return pResult;
            }
        }else{
            if (pElse!=null){
                return visitChildren(pElse);
            }
        }
        return null;
    }
    
    
    @Override
    public Object visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        boolean bLeft=(boolean) parse_single_expression_value(pLeft);
        boolean bRight=(boolean) parse_single_expression_value(pRight);
        return bLeft && bRight;
    }
    
    
    @Override
    public Object visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        boolean bLeft=(boolean) parse_single_expression_value(pLeft);
        boolean bRight=(boolean) parse_single_expression_value(pRight);
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
        this.put_var(Name, pObj);
        
        return null;// visitChildren(ctx);
    }

    
    
    @Override
    public Object visitRelationalExpression(ECMAScriptParser.RelationalExpressionContext ctx) {
        SingleExpressionContext p1=ctx.singleExpression(0);
        SingleExpressionContext p2=ctx.singleExpression(1);
        Object express1=this.parse_single_expression_value(p1);
        Object express2=this.parse_single_expression_value(p2);
        
        TerminalNode p3=(TerminalNode) ctx.getChild(1);
        
        
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
        
        Object pObj=parse_single_expression_value(pRight);
        if (str1.startsWith("this.")){
            str1=str1.split("\\.")[1];
        }else if (str1.contains(".")){
            String[] strSplit=str1.split("\\.");
            String Object_Name=strSplit[0];
            Function_Data pData2=(Function_Data) this.get_var(Object_Name);
            //this.put_var(pData2,strSplit[1], pObj);
            //return pObj;
        }
        //this.put_var(str1,pObj);
        
        parse_single_expression_value_set(pLeft,pObj);
        return pObj;
    }
    
    @Override
    public Object visitFunctionBody(ECMAScriptParser.FunctionBodyContext ctx) {
        //out.print("visitFunctionBody");

        return visitChildren(ctx);
    }
    
    @Override
    public Object visitFunctionDeclaration(ECMAScriptParser.FunctionDeclarationContext ctx) {
        TerminalNode id=ctx.Identifier();
        String function =id.getText();
        this.put_var("function:"+function,ctx);
        return null;
    }
    
    @Override
    public Object visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {

        this.put_var("function:0",ctx);
        return null;//visitChildren(ctx);
    }
    
    
    @Override
    public Object visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {
        ExpressionSequenceContext p1=ctx.expressionSequence();
        Object pObj=this.visitExpressionSequence(p1);
        StatementContext p2=ctx.statement();

        
        while((boolean)pObj){
            Object pObj2=visitChildren(p2);
            
            //out.println(pObj2.getClass().getName());
            if (pObj2!=null){
                if ("Tools.C_Break".equals(pObj2.getClass().getName())){
                    break;
                }
            }
                
            pObj=this.visitExpressionSequence(p1);
        }
        return null;
    }
    
    @Override
    public Object visitBreakStatement(ECMAScriptParser.BreakStatementContext ctx) {
        return new C_Break();
        //return visitChildren(ctx);
    }
    
    /**
     * new xxx
     * @param ctx
     * @return 
     */
    @Override
    public Object visitNewExpression(ECMAScriptParser.NewExpressionContext ctx) {
        ArgumentsContext pA=ctx.arguments();
        if (pA!=null){
            out.println("stop");
        }
        SingleExpressionContext pS=ctx.singleExpression();
        
        switch(pS.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MultiplicativeExpressionContext":
                //return visitMultiplicativeExpression((ECMAScriptParser.MultiplicativeExpressionContext) ctx);
                
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                ArgumentsExpressionContext pS2=(ArgumentsExpressionContext) pS;
                ECMAScriptParser.SingleExpressionContext p1=pS2.singleExpression();
                ECMAScriptParser.ArgumentsContext p2= pS2.arguments();
                ECMAScriptParser.ArgumentListContext pList=p2.argumentList();
                Object pObj=null;//this.visitArgumentList(pList);
                if (pList!=null){
                    pObj=this.visitArgumentList(pList);
                }else{
                    out.println("null");
                }
                String function=p1.getText();
                switch(function){
                    case "Array":
                        ArrayList pList2=new ArrayList();
                        ECMAScriptParser.SingleExpressionContext left2 = pList.singleExpression(0);
                        Object pParam=pParent.parse_single_expression_value(left2);
                        int count=pParent.double_from_object(pParam).intValue();
                        for (int i=0;i<count;i++){
                            pList2.add("");
                        }
                        return pList2;
                    default:
                        ECMAScriptParser.FunctionDeclarationContext pFun=
        (ECMAScriptParser.FunctionDeclarationContext)pParent.get_var("function:"+function);
                        if (pFun==null){
                            out.println("no function:"+function);
                        }else{
                            Function_Data pData=pParent.call_function_return_data(pFun,pList);
                            return pData;
                        }
                        break;
                }
                
        }
        return null;
    }
    
    public Object call_function(
            ECMAScriptParser.FunctionDeclarationContext ctx,
            ECMAScriptParser.ArgumentListContext pList) {
        
        Function_Call pFun=new Function_Call();
        pFun.pList=pList;
        pFun.Init(ctx,this);
        pFun.visitChildren(ctx);
        return pFun.pData.pReturn;
    }
    
    
    public Function_Data call_function_return_data(
            ECMAScriptParser.FunctionDeclarationContext ctx,
            ECMAScriptParser.ArgumentListContext pList) {
        
        Function_Call pFun=new Function_Call();
        pFun.pList=pList;
        pFun.Init(ctx,this);
        FunctionBodyContext pBody=ctx.functionBody();
        pFun.visitFunctionBody(pBody);
        //pFun.visitChildren(ctx);
        return pFun.pData;
    }
    
    public Object call_function(
            ECMAScriptParser.FunctionDeclarationContext ctx,
            ArrayList pList) {
        Function_Call pFun=new Function_Call();
        pFun.pList2=pList;
        pFun.Init2(ctx,this);
        pFun.visitChildren(ctx);
        return pFun.pData.pReturn;
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
            if (JavaMain.bDebug){
                //out.println(p.getText());
            }
        }
        Object pObj=visitChildren(ctx);
        return pObj;
    }
    
    
    @Override
    public Object visitStatement(ECMAScriptParser.StatementContext ctx) {
        String value=ctx.getText();
        return visitChildren(ctx);
    }
    
     
    @Override
    public Object visitStatementList(ECMAScriptParser.StatementListContext ctx) {
        List<StatementContext> pList=ctx.statement();
        
        for (int i=0;i<pList.size();i++){
            StatementContext pState=pList.get(i);
            Object pObj=this.visitStatement(pState);
            if (pObj!=null){
                //out.println(pObj.getClass().getName());
                switch (pObj.getClass().getName()){
                    case "Tools.C_Break":
                        return pObj;
                }
            }
        }
        return null;//visitChildren(ctx);
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
//        if ("line.length-1".equals(ctx.getText())){
//            out.println("break;");
//        }
        Object express1=parse_single_expression_value(left);
        Object express2=parse_single_expression_value(right);
        
        switch(operate){
            case "-":
                if (express1==null){
                    out.println("test");
                }
                switch (express1.getClass().getName()){
                    case "java.lang.Double":
                        switch(express2.getClass().getName()){
                            case "java.lang.String":
                                Object pObj2=this.get_var(express2);
                                return express1+string_process((String)pObj2);
                            case "java.lang.Double":
                                return (Double)express1-(Double)express2;
                            default:
                                return (Double)express1-(Double)express2;
                        }
                    case "java.lang.Integer":
                        return (Integer)express1-(Double)express2;
                    default:
                        out.println("error");
                        break;
                }
            default:
                switch (express1.getClass().getName()){
                    case "java.lang.Double":
                        switch(express2.getClass().getName()){
                            case "java.lang.String":
                                return express1+string_process((String)express2);
                            case "java.lang.Double":
                                return (Double)express1+(Double)express2;
                            case "java.lang.Integer":
                                return (Double)express1+(Integer)express2;
                            default:
                                return (Double)express1+(Double)express2;
                        }
                    case "java.lang.String":
                        
                        switch(express2.getClass().getName()){
                            case "java.lang.String":
                                return string_process((String)express1)
                                        +string_process((String)express2);
                            case "java.lang.Double":
                                return string_process((String)express1)
                                        +(Double)express2;
                            case "java.lang.Integer":
                                return string_process((String)express1)
                                        +(Integer)express2;
                            default:
                                return string_process((String)express1)
                                        +(Double)express2;
                        }
                    default:
                        out.println("error");
                        break;
                }
        }
        return "";
    }
    
    public String string_process(String content){
        if (content.startsWith("\"")){
            return content.substring(1,content.length()-1);
        }
        return content;
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
        Object express1=parse_single_expression_value(left);
        Object express2=parse_single_expression_value(right);
        switch(operate){
            case "/":
                return double_from_object(express1)/double_from_object(express2);
            default:
                if (express1==null){
                    out.println("stop");
                }
                if (express2==null){
                    out.println("stop");
                }
                return double_from_object(express1)*double_from_object(express2);
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
//        if ("s_out.println(line)".equals(ctx.getText())){
//            out.println("test");
//        }
        Object pParam=null;//this.visitArgumentList(pList);
        if (pList!=null){
            pParam=this.visitArgumentList(pList);
        }else{
            out.println("null");
        }
        //String value=pList.getText();
        String function=p1.getText();
//        if (function.equals("strSplit[4].startsWith")){
//            out.println("stop");
//            out.println(p1.getClass().getName());
//        }
        SingleExpressionContext pObj=null;
        switch(p1.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MemberDotExpressionContext":
                MemberDotExpressionContext ctx2=(MemberDotExpressionContext) p1;
                IdentifierNameContext pFun=ctx2.identifierName();
                pObj=ctx2.singleExpression();
                function=pFun.getText();
                break;
            default:
                function=p1.getText();
                if (function.contains(".")){
                    out.println(p1.getClass().getName());
                    out.println("error");
//                    String[] strSplit=function.split("\\.");
//                    pObj=strSplit[0];
//                    function=strSplit[1];
                }
                break;
        }
        return Function_SYS.call(pObj,function,pParam,this,pList);
    }
    
    
    @Override
    public Object visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
        IdentifierNameContext pName=ctx.identifierName();
        SingleExpressionContext pExpression=ctx.singleExpression();
        
        String strProperty=pName.getText();
        String ObjectName=pExpression.getText();
        
        Object pResult;
        switch(pExpression.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MemberIndexExpressionContext":
                pResult=this.visitMemberIndexExpression((MemberIndexExpressionContext) pExpression);
                break;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                pResult=this.visitIdentifierExpression((IdentifierExpressionContext) pExpression);
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(pExpression.getClass().getName());
                }
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
                case "java.util.ArrayList":
                        switch(strProperty){
                            case "length":
                                return ((ArrayList)pResult).size();
                            default:
                                if (JavaMain.bDebug){
                                    out.println(pResult.getClass().getName());
                                }
                        }
                case "java.lang.String":
                    switch(strProperty){
                        case "length":
                            return ((String)pResult).length();
                        default:
                            if (JavaMain.bDebug){
                                out.println(pResult.getClass().getName());
                            }  
                    }
                case "[Ljava.lang.String;":
                    switch(strProperty){
                        case "length":
                            return ((String[])pResult).length;
                        default:
                            if (JavaMain.bDebug){
                                out.println(pResult.getClass().getName());
                            }  
                    }
                case "Tools.Function_Data":
                    Object pObj_Property=((Function_Data)pResult).pMap.get(strProperty);
                    return pObj_Property;
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

    
    public Double double_from_object(Object p2_result){
        Double mValue=0.0;
        switch(p2_result.getClass().getName()){
            case "java.lang.String":
                mValue=Double.valueOf((String)p2_result);
                break;
            case "java.lang.Double":
                mValue=((Double)p2_result);
                break;
            case "java.lang.Long":
                mValue=((Long)p2_result)+0.0;
                break;
            case "java.lang.Integer":
                mValue=((Integer)p2_result)+0.0;
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(p2_result.getClass().getName());
                }
                break;
        }
        return mValue;
    }
    
    public Integer int_from_object(Object p2_result){
        int index=0;
        switch(p2_result.getClass().getName()){
            case "java.lang.String":
                index=Integer.valueOf((String)p2_result);
                break;
            case "java.lang.Double":
                index=((Double)p2_result).intValue();
                break;
            case "java.lang.Long":
                index=((Long)p2_result).intValue();
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(p2_result.getClass().getName());
                }
                break;
        }
        return index;
    }
    
    
    @Override
    public Object visitSwitchStatement(ECMAScriptParser.SwitchStatementContext ctx) {
        CaseBlockContext pCases=ctx.caseBlock();
        ExpressionSequenceContext pCondition=ctx.expressionSequence();

        Object pObj=this.visitExpressionSequence(pCondition);
        switch (pObj.getClass().getName()){
            case "java.lang.String":
                boolean bRun=false;
                String value=(String)pObj;
                List<CaseClausesContext> pList=pCases.caseClauses();
                for (int i=0;i<pList.size();i++){
                    CaseClausesContext p=pList.get(i);
                    List<CaseClauseContext> pList2=p.caseClause();
                    for (int j=0;j<pList2.size();j++){
                        CaseClauseContext p2=pList2.get(j);
                        ExpressionSequenceContext pCondition2=p2.expressionSequence();
                        Object pObj2=this.visitExpressionSequence(pCondition2);
                        if (value.equals((String)pObj2)){
                            StatementListContext p3=p2.statementList();
                            this.visitStatementList(p3);
                            bRun=true;
                        }
                    }
                }
                if (bRun==false){
                    DefaultClauseContext p=pCases.defaultClause();
                    StatementListContext p3=p.statementList();
                    this.visitStatementList(p3);
                }
                break;
        }
        return null;
    }
    
    public void visitMemberIndexExpression_set(
            ECMAScriptParser.MemberIndexExpressionContext ctx,Object pObj) {
        SingleExpressionContext p1=ctx.singleExpression();
        ExpressionSequenceContext p2=ctx.expressionSequence();
        
        
        Object p1_result=this.parse_single_expression_value(p1);
        Object p2_result=this.visitExpressionSequence(p2);
        
        switch(p1_result.getClass().getName()){
            case "java.util.ArrayList":
                ArrayList pArrayList=(ArrayList) p1_result;
                Integer index=this.int_from_object(p2_result);
                while (pArrayList.size()<= index){
                    pArrayList.add("");
                }
                pArrayList.set(index,pObj);
                break;
            case "[Ljava.lang.String;":
                int index2=0;
                switch(p2_result.getClass().getName()){
                    case "java.lang.String":
                        index2=Integer.valueOf((String)p2_result);
                        break;
                    case "java.lang.Double":
                        index2=((Double)p2_result).intValue();
                        break;
                    default:
                        if (JavaMain.bDebug){
                            out.println(p1_result.getClass().getName());
                        }
                        break;
                }
                ((String[])p1_result)[index2]=(String)pObj;
                break;
            default:
                if (JavaMain.bDebug){
                    out.println(p1_result.getClass().getName());
                }
                visitChildren(ctx);
                break;
        }
    }
    
    @Override
    public Object visitMemberIndexExpression(
            ECMAScriptParser.MemberIndexExpressionContext ctx) {
        SingleExpressionContext p1=ctx.singleExpression();
        ExpressionSequenceContext p2=ctx.expressionSequence();
        
        
        Object p1_result=this.parse_single_expression_value(p1);
        Object p2_result=this.visitExpressionSequence(p2);
        
        switch(p1_result.getClass().getName()){
            case "java.util.ArrayList":
                ArrayList pArrayList=(ArrayList) p1_result;
                Integer index=this.int_from_object(p2_result);
                while (pArrayList.size()<= index){
                    pArrayList.add("");
                }
                return pArrayList.get(index);//((Long)mValue).intValue());
            case "org.json.JSONArray":
                org.json.JSONArray pArray=(org.json.JSONArray) p1_result;
                int index_jsonarray=Integer.parseInt(p2.getText());
                return pArray.get(index_jsonarray);
            case "[Ljava.lang.String;":
                int index2=0;
                switch(p2_result.getClass().getName()){
                    case "java.lang.String":
                        index2=Integer.valueOf((String)p2_result);
                        break;
                    case "java.lang.Double":
                        index2=((Double)p2_result).intValue();
                        break;
                    default:
                        if (JavaMain.bDebug){
                            out.println(p1_result.getClass().getName());
                        }
                        break;
                }
//                if (index2==1){
//                    out.println("error");
//                }
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
