public class ASTPrinter {
    public static void printAST(ASTNode node, int indent) {

        for (int i = 0; i < indent; i++) {
        System.out.print("  ");
        }
        if (node instanceof MainNode){
            MainNode mainNode = (MainNode) node;
            System.out.println("Main");
            for (String parameter : mainNode.params) {
                printIndent(indent + 1);
                System.out.println(parameter);
            }
            printAST(mainNode.body, indent + 1);
        }
        else if (node instanceof BlockNode ) {
            BlockNode block = (BlockNode) node;
            System.out.println("Block");

            for (ASTNode stmt : block.statements) {
                printAST(stmt, indent + 1);
            }
        }
        else if (node instanceof IntNode) {
            System.out.println(((IntNode) node).value);
        } 
        else if (node instanceof IdNode) {
            System.out.println(((IdNode) node).id);
        } 
        else if (node instanceof OperationNode) {
            OperationNode op = (OperationNode) node;
            System.out.println(op.operation);
            printAST(op.left, indent + 1);
            printAST(op.right, indent + 1);
        }
        else if (node instanceof AssignNode) {
            AssignNode assignmentStatement = (AssignNode) node;
            System.out.println("=");
            printAST(assignmentStatement.id, indent + 1);
            printAST(assignmentStatement.value, indent + 1);
        }
        else if (node instanceof ExprNode) {
            ExprNode exprNode = (ExprNode) node;
            System.out.println("ExprStmt");
            printAST(exprNode.value, indent + 1);
        }
        else if (node instanceof PrintNode) {
            PrintNode printStatement = (PrintNode) node;
            System.out.println("Print");
            printAST(printStatement.value, indent + 1);
        }
        else if (node instanceof IfNode) {
            IfNode ifNode = (IfNode) node;
            System.out.println("IfStmt");
            printAST(ifNode.condition, indent + 1);
            printAST(ifNode.thenBlock, indent + 1);
            if (ifNode.elseBlock != null) {
                printAST(ifNode.elseBlock, indent + 1);
            }
        }
        else {
            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
        }
    }

    private static void printIndent(int indent) {
    for (int i = 0; i < indent; i++) {
        System.out.print("  ");
    }
}
}
