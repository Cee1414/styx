import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class ASTBuilder extends styxBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProg(styxParser.ProgContext ctx) {
        return visit(ctx.mainFunction());
    }

    @Override
    public ASTNode visitMainFunction(styxParser.MainFunctionContext ctx) {
        List<String> params = new ArrayList<>();

        if (ctx.paramList() != null) {
            for (TerminalNode id : ctx.paramList().ID()) {
                params.add(id.getText());
            }
        }

        BlockNode block = (BlockNode) visit(ctx.block()); 
        return new MainNode(params,block);
    }

    @Override
    public ASTNode visitBlockStmt(styxParser.BlockStmtContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public ASTNode visitBlock(styxParser.BlockContext ctx) {
        List<ASTNode> statements = new ArrayList<>();
        for (styxParser.StatContext s : ctx.stat()) {
            ASTNode stmt = visit(s);
            if (stmt != null) {
                statements.add(stmt);
            }
        }

        return new BlockNode(statements);
    }
    
    @Override
    public ASTNode visitExprStmt(styxParser.ExprStmtContext ctx) {
        ASTNode value = visit(ctx.expr());
        return new ExprNode(value);
    }
    @Override
    public ASTNode visitIfStmt(styxParser.IfStmtContext ctx) {
        ASTNode condition = visit(ctx.expr());
        BlockNode thenBlock = (BlockNode) visit(ctx.block(0)); 
        BlockNode elseBlock = null;
        if (ctx.block().size() > 1) {
            elseBlock = (BlockNode) visit(ctx.block(1)); 
        }
        return new IfNode(condition, thenBlock, elseBlock);
    }
    public ASTNode visitWhileStmt(styxParser.WhileStmtContext ctx) {
        ASTNode condition = visit(ctx.expr());
        BlockNode body = (BlockNode) visit(ctx.block());
        return new WhileNode(condition, body);
    }

    @Override
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
    public ASTNode visitComparison(styxParser.ComparisonContext ctx) {
        ASTNode left = visit(ctx.expr(0));
        ASTNode right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        return new OperationNode(op, left, right);
    }   

    @Override
    public ASTNode visitEquality(styxParser.EqualityContext ctx) {
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
    public PrintNode visitPrintStmt(styxParser.PrintStmtContext ctx) {
        ASTNode value = visit(ctx.expr());
        return new PrintNode(value);
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
