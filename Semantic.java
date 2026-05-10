//TODO Phase One: check for assignment 

import java.util.HashSet;
import java.util.Set;

public class Semantic {
    Set<String> memory = new HashSet<>();
    public void checkAssignment(ASTNode node) {

        if (node instanceof BlockNode ) {
            BlockNode block = (BlockNode) node;

            for (ASTNode stmt : block.statements) {
                checkAssignment(stmt);
            }
        }
        else if (node instanceof IntNode) {
        return;
        }
        else if (node instanceof IdNode) {
        IdNode idNode = (IdNode) node;
        if (!memory.contains(idNode.id)) {
            System.out.println("Error: " + idNode.id + " used before assignment");
        }
        }  
        else if (node instanceof OperationNode) {
        OperationNode op = (OperationNode) node;
        checkAssignment(op.left);
        checkAssignment(op.right);
        }
        else if (node instanceof AssignNode) {
        AssignNode assignmentStatement = (AssignNode) node;
        String id = assignmentStatement.id.id;
        checkAssignment(assignmentStatement.value);
        memory.add(id);
        }
        else if (node instanceof PrintNode) {
            PrintNode printStatement = (PrintNode) node;
            checkAssignment(printStatement.value);
        }
        else if (node instanceof ExprNode) {
            System.out.println("unused expression, skipping evaluation");
        }
        else {
            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
        }

    }

}
