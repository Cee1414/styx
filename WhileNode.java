public class WhileNode extends ASTNode {
    ASTNode condition;
    BlockNode body;

    public WhileNode(ASTNode condition, BlockNode body) {
        this.condition = condition;
        this.body = body;
    }
}
