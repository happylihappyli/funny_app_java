/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import antlr_js.ECMAScriptParser;
import funnyai.JavaMain;
import static java.lang.System.out;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author happyli
 */
public class Function_Call extends MyVisitor{
    public ECMAScriptParser.ArgumentListContext pList;
    public ArrayList pList2;

    
    public void Init(ECMAScriptParser.FunctionDeclarationContext ctx,
            MyVisitor pParent){
        this.pParent=pParent;
        //初始化函数的变量
        if (ctx==null){
            out.println("error");
        }
        ECMAScriptParser.FormalParameterListContext pCtx=ctx.formalParameterList();
        if (pList==null){
            return ;
        }
        for(int i=0;i<pList.singleExpression().size();i++){
            ECMAScriptParser.SingleExpressionContext pValue=pList.singleExpression(i);// .children.get(i);
            TerminalNode pName=pCtx.Identifier(i);// .children.get(i);
            String strName=pName.getText();
            //out.println(pValue.getClass().getName());
            Object pObj=this.parse_single_expression(pValue);
            switch(pObj.getClass().getName()){
                case "java.lang.String":
                    String strValue=(String)pObj;
                    if (strValue.startsWith("\"")){
                        strValue=strValue.substring(1,strValue.length()-1);
                    }
                    if (JavaMain.bDebug) out.println(strName+"="+strValue);
                    this.put_var(strName, strValue); //
                    //pMap.put(strName,strValue);
                    break;
                default:
                    this.put_var(strName, pObj); //
                    //pMap.put(strName,pObj);
                    break;
            }
        }
    }
    
    
    public void Init2(ECMAScriptParser.FunctionDeclarationContext ctx,
            MyVisitor pParent){
        this.pParent=pParent;
        //初始化函数的变量
        if (ctx==null){
            out.println("error");
        }
        ECMAScriptParser.FormalParameterListContext pCtx=ctx.formalParameterList();
        for(int i=0;i<pList2.size();i++){
            //ECMAScriptParser.SingleExpressionContext pValue=pList.singleExpression(i);// .children.get(i);
            TerminalNode pName=pCtx.Identifier(i);// .children.get(i);
            String strName=pName.getText();
            //out.println(pValue.getClass().getName());
            Object pObj=pList2.get(i);
            switch(pObj.getClass().getName()){
                case "java.lang.String":
                    String strValue=(String)pObj;
                    if (strValue.startsWith("\"")){
                        strValue=strValue.substring(1,strValue.length()-1);
                    }
                    if (JavaMain.bDebug) out.println(strName+"="+strValue);
                    this.put_var(strName, strValue); //
                    //pMap.put(strName,strValue);
                    break;
                default:
                    this.put_var(strName, pObj); //
                    //pMap.put(strName,pObj);
                    break;
            }
        }
    }
    
    
    @Override
    public Object visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
        ECMAScriptParser.ExpressionSequenceContext p=ctx.expressionSequence();
        Object pObj=this.visitExpressionSequence(p);//visitChildren(ctx);;
        
        pData.pReturn=pObj;
        return pObj;
    }
    
    
}
