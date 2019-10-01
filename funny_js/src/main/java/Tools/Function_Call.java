/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import antlr_js.ECMAScriptParser;
import funnyai.JavaMain;
import static java.lang.System.out;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author happyli
 */
public class Function_Call extends MyVisitor{
    public Object pReturn=null;
    public ECMAScriptParser.ArgumentListContext pList;

    
    public void Init(ECMAScriptParser.FunctionDeclarationContext ctx,
            MyVisitor pParent){
        this.pParent=pParent;
        //初始化函数的变量
        ECMAScriptParser.FormalParameterListContext pCtx=ctx.formalParameterList();
        for(int i=0;i<pList.getChildCount();i++){
            ParseTree pValue=pList.children.get(i);
            ParseTree pName=pCtx.children.get(i);
            String strName=pName.getText();
            String strValue=pValue.getText();
            if (JavaMain.bDebug) out.println(strName+"="+strValue);
            pMap.put(strName,strValue);
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
