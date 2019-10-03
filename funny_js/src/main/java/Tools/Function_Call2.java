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
public class Function_Call2 extends MyVisitor{
    public Object pReturn=null;
    public ArrayList pList;

    
    public void Init(ECMAScriptParser.FunctionExpressionContext ctx,
            MyVisitor pParent){
        this.pParent=pParent;
        //初始化函数的变量
        if (ctx==null){
            out.println("error");
        }
        ECMAScriptParser.FormalParameterListContext pCtx=ctx.formalParameterList();
        for(int i=0;i<pList.size();i++){
            Object pObj=pList.get(i);// .children.get(i);
            TerminalNode pName=pCtx.Identifier(i);// .children.get(i);
            String strName=pName.getText();
            out.println(pObj.getClass().getName());
            //Object pObj=this.parse_single_expression(pValue);
            switch(pObj.getClass().getName()){
                case "java.lang.String":
                    String strValue=(String)pObj;
                    if (strValue.startsWith("\"")){
                        strValue=strValue.substring(1,strValue.length()-1);
                    }
                    if (JavaMain.bDebug) out.println(strName+"="+strValue);
                    pMap.put(strName,strValue);
                    break;
                default:
                    pMap.put(strName,pObj);
                    break;
            }
        }
    }
    
    
    @Override
    public Object visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {
        ECMAScriptParser.ExpressionSequenceContext p=ctx.expressionSequence();
        Object pObj=this.visitExpressionSequence(p);//visitChildren(ctx);;
        
        pReturn=pObj;
        return pObj;
    }
    
    
}
