public class ASTPrinter {
    public static void printAST(ASTNode node, int indent) {

        if (node == null) { 
            return;
        }

        for (int i = 0; i < indent; i++) {
        System.out.print("  ");
        }

        if (node instanceof IntNode) {
        System.out.println(((IntNode) node).value);
        } 

        else if (node instanceof OperationNode) {
        OperationNode op = (OperationNode) node;
        System.out.println(op.operation);

        printAST(op.left, indent + 1);
        printAST(op.right, indent + 1);

        }
        
        else {
            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
        }
    }
}
