import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class ASTBuilder extends styxBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProg(styxParser.ProgContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        for (styxParser.StatContext s : ctx.stat()) {
            ASTNode stmt = visit(s);
            if (stmt != null) {
                statements.add(stmt);
            }
        }

        return new ProgramNode(statements);
    }
    
    public ASTNode visitExprStmt(styxParser.ExprStmtContext ctx) {
        return visit(ctx.expr());
    }

    public ASTNode visitParens(styxParser.ParensContext ctx) {
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
    public ASTNode visitAssign(styxParser.AssignContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());
        ASTNode value = visit(ctx.expr());
        
        return new AssignNode(id, value);
    }

    @Override
    public ASTNode visitInt(styxParser.IntContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText());
        return new IntNode(value);
    }

    @Override
    public ASTNode visitId(styxParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return new IdNode(id);
    }



}
