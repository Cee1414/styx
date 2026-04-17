import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class ASTBuilder extends styxBaseVisitor<ASTNode> {
    
    public ASTNode visitExprStmt(styxParser.ExprStmtContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitMulDiv(styxParser.MulDivContext ctx) {
        ASTNode left = visit(ctx.expr(0));
        ASTNode right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        
        return new OperationNode(op, left, right);
    }

    @Override
    public ASTNode visitAddSub(styxParser.AddSubContext ctx) {
        ASTNode left = visit(ctx.expr(0));
        ASTNode right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        
        return new OperationNode(op, left, right);
    }

    @Override
    public ASTNode visitInt(styxParser.IntContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText());
        return new IntNode(value);
    }



}
