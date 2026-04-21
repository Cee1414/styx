
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeGen {

    HashMap<String, Integer> memory = new HashMap<>();
    int variableOffset= 0;
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

        if (node instanceof IntNode) {
        IntNode intNode = (IntNode) node;
        emit("loadI " + intNode.value + " => r2");
        pushReg("r2");
        return;
        }
        else if (node instanceof IdNode) {
        IdNode idNode = (IdNode) node;
        if (!memory.containsKey(idNode.id)) {
            System.out.println("Error: " + idNode.id + " used before assignment");
        }
        emit("loadAI " + "r0, " + memory.get(idNode.id) + " => r2");
        // emit("loadAI r1, 0 => " + reg);
        pushReg("r2");
        return;
        }  
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
        else if (node instanceof AssignNode) {
        AssignNode assignmentStatement = (AssignNode) node;
        String id = assignmentStatement.id.id;        
        descendStatements(assignmentStatement.value);
        popTo("r2");

        if (!memory.containsKey(id)) {
        memory.put(id, variableOffset);
        variableOffset += 8;
        }

        int variableOffset = memory.get(id);
        emit("storeAI " + "r2" + " => r0, " + variableOffset);
        pushReg("r2");
        }
        else {
            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
        }

    }

    public List<String> generate(ASTNode node) {
        out.clear();

        // INIT STEP (PROLOGUE)
        emit("loadI 0 => r0");
        emit("loadI 256 => r1");

        ProgramNode prog = (ProgramNode) node;

            for (ASTNode stmt : prog.statements) {
                descendStatements(stmt);
                popTo("r2");
            }        
        return out;
        }



}
