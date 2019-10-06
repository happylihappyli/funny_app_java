package antlr_js;
// Generated from ECMAScript.g4 by ANTLR 4.7.1

import static java.lang.System.out;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link ECMAScriptVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class ECMAScriptBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements ECMAScriptVisitor<T> {

    
    @Override
    public T visitProgram(ECMAScriptParser.ProgramContext ctx) {
        //out.println("visitProgram");
        return visitChildren(ctx);
    }

    
    @Override
    public T visitSourceElements(ECMAScriptParser.SourceElementsContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitSourceElement(ECMAScriptParser.SourceElementContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitStatement(ECMAScriptParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBlock(ECMAScriptParser.BlockContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitStatementList(ECMAScriptParser.StatementListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitVariableStatement(ECMAScriptParser.VariableStatementContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitVariableDeclarationList(ECMAScriptParser.VariableDeclarationListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitVariableDeclaration(ECMAScriptParser.VariableDeclarationContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitInitialiser(ECMAScriptParser.InitialiserContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitEmptyStatement(ECMAScriptParser.EmptyStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitExpressionStatement(ECMAScriptParser.ExpressionStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitIfStatement(ECMAScriptParser.IfStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitDoStatement(ECMAScriptParser.DoStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitWhileStatement(ECMAScriptParser.WhileStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitForStatement(ECMAScriptParser.ForStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitForVarStatement(ECMAScriptParser.ForVarStatementContext ctx) {
        out.println("visitForVarStatement");
        out.println("a");
        return visitChildren(ctx);
    }

    
    @Override
    public T visitForInStatement(ECMAScriptParser.ForInStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitForVarInStatement(ECMAScriptParser.ForVarInStatementContext ctx) {
        out.println("visitForVarInStatement");

        return visitChildren(ctx);
    }

    
    @Override
    public T visitContinueStatement(ECMAScriptParser.ContinueStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitBreakStatement(ECMAScriptParser.BreakStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitReturnStatement(ECMAScriptParser.ReturnStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitWithStatement(ECMAScriptParser.WithStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitSwitchStatement(ECMAScriptParser.SwitchStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitCaseBlock(ECMAScriptParser.CaseBlockContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitCaseClauses(ECMAScriptParser.CaseClausesContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitCaseClause(ECMAScriptParser.CaseClauseContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitDefaultClause(ECMAScriptParser.DefaultClauseContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitLabelledStatement(ECMAScriptParser.LabelledStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitThrowStatement(ECMAScriptParser.ThrowStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitTryStatement(ECMAScriptParser.TryStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitCatchProduction(ECMAScriptParser.CatchProductionContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitFinallyProduction(ECMAScriptParser.FinallyProductionContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitDebuggerStatement(ECMAScriptParser.DebuggerStatementContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitFunctionDeclaration(ECMAScriptParser.FunctionDeclarationContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitFormalParameterList(ECMAScriptParser.FormalParameterListContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitFunctionBody(ECMAScriptParser.FunctionBodyContext ctx) {
        //out.print("aa");

        return visitChildren(ctx);
    }

    
    @Override
    public T visitArrayLiteral(ECMAScriptParser.ArrayLiteralContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitElementList(ECMAScriptParser.ElementListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitElision(ECMAScriptParser.ElisionContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitObjectLiteral(ECMAScriptParser.ObjectLiteralContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertyNameAndValueList(ECMAScriptParser.PropertyNameAndValueListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertyExpressionAssignment(ECMAScriptParser.PropertyExpressionAssignmentContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertyGetter(ECMAScriptParser.PropertyGetterContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertySetter(ECMAScriptParser.PropertySetterContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertyName(ECMAScriptParser.PropertyNameContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitPropertySetParameterList(ECMAScriptParser.PropertySetParameterListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitArguments(ECMAScriptParser.ArgumentsContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitArgumentList(ECMAScriptParser.ArgumentListContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitExpressionSequence(ECMAScriptParser.ExpressionSequenceContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitTernaryExpression(ECMAScriptParser.TernaryExpressionContext ctx) {

        return visitChildren(ctx);
    }

    
    @Override
    public T visitLogicalAndExpression(ECMAScriptParser.LogicalAndExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitPreIncrementExpression(ECMAScriptParser.PreIncrementExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitObjectLiteralExpression(ECMAScriptParser.ObjectLiteralExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitInExpression(ECMAScriptParser.InExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitLogicalOrExpression(ECMAScriptParser.LogicalOrExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitNotExpression(ECMAScriptParser.NotExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitPreDecreaseExpression(ECMAScriptParser.PreDecreaseExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitArgumentsExpression(ECMAScriptParser.ArgumentsExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitThisExpression(ECMAScriptParser.ThisExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitFunctionExpression(ECMAScriptParser.FunctionExpressionContext ctx) {
        //out.print("aa");

        return visitChildren(ctx);
    }

    
    @Override
    public T visitUnaryMinusExpression(ECMAScriptParser.UnaryMinusExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitAssignmentExpression(ECMAScriptParser.AssignmentExpressionContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public T visitPostDecreaseExpression(ECMAScriptParser.PostDecreaseExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitTypeofExpression(ECMAScriptParser.TypeofExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitInstanceofExpression(ECMAScriptParser.InstanceofExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitUnaryPlusExpression(ECMAScriptParser.UnaryPlusExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitDeleteExpression(ECMAScriptParser.DeleteExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitEqualityExpression(ECMAScriptParser.EqualityExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBitXOrExpression(ECMAScriptParser.BitXOrExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitMultiplicativeExpression(ECMAScriptParser.MultiplicativeExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBitShiftExpression(ECMAScriptParser.BitShiftExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitParenthesizedExpression(ECMAScriptParser.ParenthesizedExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitAdditiveExpression(ECMAScriptParser.AdditiveExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitRelationalExpression(ECMAScriptParser.RelationalExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitPostIncrementExpression(ECMAScriptParser.PostIncrementExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBitNotExpression(ECMAScriptParser.BitNotExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitNewExpression(ECMAScriptParser.NewExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitLiteralExpression(ECMAScriptParser.LiteralExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitArrayLiteralExpression(ECMAScriptParser.ArrayLiteralExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitMemberDotExpression(ECMAScriptParser.MemberDotExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitMemberIndexExpression(ECMAScriptParser.MemberIndexExpressionContext ctx) {
        
        return visitChildren(ctx);
    }

    
    @Override
    public T visitIdentifierExpression(ECMAScriptParser.IdentifierExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBitAndExpression(ECMAScriptParser.BitAndExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitBitOrExpression(ECMAScriptParser.BitOrExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitAssignmentOperatorExpression(ECMAScriptParser.AssignmentOperatorExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitVoidExpression(ECMAScriptParser.VoidExpressionContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitAssignmentOperator(ECMAScriptParser.AssignmentOperatorContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitLiteral(ECMAScriptParser.LiteralContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitNumericLiteral(ECMAScriptParser.NumericLiteralContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitIdentifierName(ECMAScriptParser.IdentifierNameContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitReservedWord(ECMAScriptParser.ReservedWordContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitKeyword(ECMAScriptParser.KeywordContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitFutureReservedWord(ECMAScriptParser.FutureReservedWordContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitGetter(ECMAScriptParser.GetterContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitSetter(ECMAScriptParser.SetterContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitEos(ECMAScriptParser.EosContext ctx) {
        return visitChildren(ctx);
    }

    
    @Override
    public T visitEof(ECMAScriptParser.EofContext ctx) {
        return visitChildren(ctx);
    }
}
