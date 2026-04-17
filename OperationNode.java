public class OperationNode extends ASTNode {
    ASTNode left;
    ASTNode right;
    String operation;

    public OperationNode(String operation, ASTNode left, ASTNode right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }
}
