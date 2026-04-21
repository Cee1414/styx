
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeGen {

    HashMap<String, Integer> memory = new HashMap<>();
    List<String> out = new ArrayList<>();

    private void emit(String s) {
        out.add(s);
    }
    private void pushReg(String reg) {
        emit("storeAI " + reg + " => r1, 0");
        emit("addI r1, 8 => r1");
    }
    private void popTo(String reg) {
        emit("subI r1, 8 => r1");
        emit("loadAI r1, 0 => " + reg);
    }
    
    public void descendStatements(ASTNode node) {

        if (node instanceof ProgramNode ) {
            ProgramNode prog = (ProgramNode) node;

            for (ASTNode stmt : prog.statements) {
                descendStatements(stmt);
            }
        }
        else if (node instanceof IntNode) {
        IntNode intNode = (IntNode) node;
        emit("loadI " + intNode.value + " => r2");
        pushReg("r2");
        return;
        }
        // else if (node instanceof IdNode) {
        // //TODO FIXME
        // IdNode idNode = (IdNode) node;
        // if (!memory.contains(idNode.id)) {
        //     System.out.println("Error: " + idNode.id + " used before assignment");
        // }
        // }  
        else if (node instanceof OperationNode) {
        OperationNode opNode = (OperationNode) node;
        descendStatements(opNode.left);
        descendStatements(opNode.right);

        popTo("r3");
        popTo("r2");

        switch (opNode.operation) {
                case "+" -> emit("add r2, r3 => r2");
                case "-" -> emit("sub r2, r3 => r2");
                case "*" -> emit("mult r2, r3 => r2");
                case "/" -> emit("div r2, r3 => r2");
                default -> throw new RuntimeException("unknown binary op: " + opNode.operation);
            }

            pushReg("r2");
            return;
        }
        // else if (node instanceof AssignNode) {
        // AssignNode assignmentStatement = (AssignNode) node;
        // String id = assignmentStatement.id.id;
        // descendStatements(assignmentStatement.value);
        // memory.add(id);
        // }
        else {
            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
        }

    }

    public List<String> generate(ASTNode e) {
        out.clear();
        descendStatements(e);
        popTo("r2");
        return out;
        }



}
