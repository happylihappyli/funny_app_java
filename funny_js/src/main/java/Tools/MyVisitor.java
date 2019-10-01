package Tools;

import antlr_js.ECMAScriptBaseVisitor;
import antlr_js.ECMAScriptParser;
import antlr_js.ECMAScriptParser.*;
import funnyai.JavaMain;
import static java.lang.System.out;
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
        TerminalNode pNode1 = (TerminalNode) ctx.getChild(0);
        ECMAScriptParser.InitialiserContext pNode2 = (ECMAScriptParser.InitialiserContext) ctx.getChild(1);
        String varName=pNode1.getText();
        Object pObj=visitChildren(pNode2);
        if (JavaMain.bDebug) out.println(pObj.getClass().getName());
        if (JavaMain.bDebug) out.println(pObj.getClass().getCanonicalName());
        
        String express="";
        switch(pObj.getClass().getName().toLowerCase()){
            case "java.lang.string":
                express=(String)pObj;
                break;
            case "java.lang.double":
                express=(Double)pObj+"";
        }
        this.pMap.put(varName, express);
        return express; 
    }
    
    
    @Override
    public Object visitLiteral(ECMAScriptParser.LiteralContext ctx) {
        return ctx.getText();
    }
    
    public Object parse_single_expression(ECMAScriptParser.SingleExpressionContext pItem){
        Double express1=0.0;
        switch(pItem.getClass().getName()){
            case "antlr_js.ECMAScriptParser$MultiplicativeExpressionContext":
                express1=visitMultiplicativeExpression((ECMAScriptParser.MultiplicativeExpressionContext) pItem);
                break;
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                express1=(Double) visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext) pItem);
                break;
            case "antlr_js.ECMAScriptParser$IdentifierExpressionContext":
                express1=Double.parseDouble((String) visitIdentifierExpression((ECMAScriptParser.IdentifierExpressionContext) pItem));
                break;
            case "antlr_js.ECMAScriptParser$EqualityExpressionContext":
                return visitEqualityExpression((EqualityExpressionContext)pItem);
            case "antlr_js.ECMAScriptParser$LiteralExpressionContext":
                express1=Double.parseDouble(pItem.getText());
                break;
            case "antlr_js.ECMAScriptParser$AssignmentExpressionContext":
                return visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext)pItem);
            default:
                if (JavaMain.bDebug){
                    out.println(pItem.getClass().getName());
                }
                express1=Double.parseDouble((String) visitChildren(pItem));
                break;
        }
        return express1;
    }
    
    
    @Override
    public Object visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
        TerminalNode p=(TerminalNode)ctx.getChild(1);
        SingleExpressionContext pLeft= ctx.singleExpression(0);
        SingleExpressionContext pRight= ctx.singleExpression(1);
        
        double express1=(double) parse_single_expression(pLeft);
        double express2=(double) parse_single_expression(pRight);
        
        if (p.getText().equals("==")){
            if (express1 == express2){
                return true;
            }
        }else{
            out.println("error visitEqualityExpression");
        }
        return false;
    }
    
    @Override
    public Object visitExpressionStatement(ECMAScriptParser.ExpressionStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public Object visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx) {
        String value=ctx.getText();
        
        if (this.pMap.containsKey(value)){
            return this.pMap.get(value);
        }
        if (pParent!=null && pParent.pMap.containsKey(value)){
            return pParent.pMap.get(value);
        }
        return visitChildren(ctx);
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
                out.println(pObj.getClass().getName());
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
    public Object visitExpressionSequence(ECMAScriptParser.ExpressionSequenceContext ctx) {
        SingleExpressionContext pExpression=ctx.singleExpression(0);
        
        switch(pExpression.getClass().getName()){
            case "antlr_js.ECMAScriptParser$AdditiveExpressionContext":
                AdditiveExpressionContext p=(AdditiveExpressionContext)pExpression;
                return visitAdditiveExpression(p);
            case "antlr_js.ECMAScriptParser$LogicalAndExpressionContext":
                return this.visitLogicalAndExpression((LogicalAndExpressionContext)pExpression);
            
            case "antlr_js.ECMAScriptParser$ArgumentsExpressionContext":
                return visitArgumentsExpression((ECMAScriptParser.ArgumentsExpressionContext) pExpression);
                
            case "antlr_js.ECMAScriptParser$AssignmentExpressionContext":
                Object pObj=this.visitAssignmentExpression((AssignmentExpressionContext)pExpression);
                return pObj;
            case "antlr_js.ECMAScriptParser$EqualityExpressionContext":
                return visitEqualityExpression((EqualityExpressionContext)pExpression);
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
        //out.println("visitFunctionDeclaration");
        TerminalNode id=ctx.Identifier();
        this.pMap.put("function:"+id.getText(),ctx);
        return "";//visitChildren(ctx);
    }
    
    public Object call_function(ECMAScriptParser.FunctionDeclarationContext ctx,
            ECMAScriptParser.ArgumentListContext pList) {
        if (JavaMain.bDebug) out.println("call function");
        Function_Call pFun=new Function_Call();
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
            if (JavaMain.bDebug) out.println(p.getText());
        }
        Object pObj=visitChildren(ctx);
        return pObj;
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
    public Double visitAdditiveExpression(ECMAScriptParser.AdditiveExpressionContext ctx) {
        ECMAScriptParser.SingleExpressionContext left = ctx.singleExpression(0);
        ECMAScriptParser.SingleExpressionContext right = ctx.singleExpression(1);
        TerminalNode p=(TerminalNode) ctx.children.get(1);
        String operate =p.getText();
        
        Double express1=(Double) parse_single_expression(left);
        Double express2=(Double) parse_single_expression(right);
        
        switch(operate){
            case "-":
                return express1-express2;
            default:
                return express1+express2;
        }
    }
    
    
    /**
     * 乘法
     * @param ctx
     * @return 
     */
    @Override 
    public Double visitMultiplicativeExpression(ECMAScriptParser.MultiplicativeExpressionContext ctx) { 
        ECMAScriptParser.SingleExpressionContext left = ctx.singleExpression(0);// .singleExpression(0));
        ECMAScriptParser.SingleExpressionContext right = ctx.singleExpression(1);
        TerminalNode p=(TerminalNode) ctx.children.get(1);
        String operate =p.getText();
        Double express1=(Double) parse_single_expression(left);
        Double express2=(Double) parse_single_expression(right);
        switch(operate){
            case "/":
                return express1/express2;
            default:
                return express1*express2;
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
        switch(p1.getText().toLowerCase()){
            case "math.pow":
                ECMAScriptParser.SingleExpressionContext left = pList.singleExpression(0);// .singleExpression(0));
                ECMAScriptParser.SingleExpressionContext right = pList.singleExpression(1);
                Double express1=(Double) parse_single_expression(left);
                Double express2=(Double) parse_single_expression(right);
                return Math.pow(express1, express2);
            case "out.println":
                if (value.startsWith("\"")){
                    out.println(value.substring(1,value.length()-1));
                }else{
                    Object pObj=this.pMap.get(value);
                    out.println(pObj.toString());
                }
                break;
            default:
                FunctionDeclarationContext pFun=(FunctionDeclarationContext) this.pMap.get("function:"+p1.getText());
                //FunctionBodyContext pBody=pFun.functionBody();
                return call_function(pFun,pList);
                
        }
        return visitChildren(ctx);
    }
    
    
    @Override
    public Object visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override 
    public Object visitArrayLiteral(ECMAScriptParser.ArrayLiteralContext ctx) {
        return ctx.getText();
        //return visitChildren(ctx); 
    }
}
