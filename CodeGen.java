
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeGen {

    HashMap<String, Integer> memory = new HashMap<>();
    int nextVariableOffset= 0;
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

    int labelCounter = 0;

    private String getLabel() {
        String label = "L" + labelCounter;
        labelCounter += 1;
        return label;
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
                case ">" -> emit("cmp_GT r2, r3 => r2");
                case "<" -> emit("cmp_LT r2, r3 => r2");
                case "==" -> emit("cmp_EQ r2, r3 => r2");
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
            memory.put(id, nextVariableOffset);
            nextVariableOffset += 8;
            }

            int variableOffset = memory.get(id);
            emit("storeAI " + "r2" + " => r0, " + variableOffset);
        }
        else if (node instanceof IfNode) {
            IfNode ifNode = (IfNode) node;
            descendStatements(ifNode.condition);
            String lTrue = getLabel();
            String lFalse = getLabel();
            String lJoin = getLabel();
            if (ifNode.elseBlock != null){
            popTo("r2");
            emit("cbr r2 => " + lTrue + ", " + lFalse);
            emit(lTrue + ": nop");
            descendStatements(ifNode.thenBlock);
            emit("jumpI => " + lJoin);
            emit(lFalse + ": nop");
            descendStatements(ifNode.elseBlock);
            emit(lJoin + ": nop");
            }
            else {
            popTo("r2");
            emit("cbr r2 => " + lTrue + ", " + lJoin);
            emit(lTrue + ": nop");
            descendStatements(ifNode.thenBlock);
            emit(lJoin + ": nop");
            }

        }
        else if (node instanceof MainNode) {
            MainNode mainNode = (MainNode) node;
            // assignment code from CLI per param
            // for (String id : mainNode.params) {
            for (int paramNum = 0; paramNum < mainNode.params.size(); paramNum++){
                //get CLI Value from argv
                int cliOffset = paramNum * 8;
                emit("loadAI r_argv, " + cliOffset + " => r4");
                emit("atoi r4 => r4");
                //handle variable store offset logic
                String id = mainNode.params.get(paramNum);
                if (!memory.containsKey(id)) {
                    memory.put(id, nextVariableOffset);
                    nextVariableOffset += 8;
                }
                int variableOffset = memory.get(id);
                emit("storeAI r4 => r0, " + variableOffset);
            }
            //enter main block
            descendStatements(mainNode.body);
        }
        else if (node instanceof PrintNode) {
            PrintNode printStatement = (PrintNode) node;
            descendStatements(printStatement.value);
            popTo("r2");
            String printRegister = "r2";
            emit("p_int " + printRegister);
        }
        else if (node instanceof BlockNode) {
            BlockNode blockStatement = (BlockNode) node;
            for (ASTNode stmt : blockStatement.statements) {
            descendStatements(stmt);
        }      
        }
        else if (node instanceof ExprNode) {
            ExprNode exprStatement = (ExprNode) node;
            descendStatements(exprStatement.value);
            popTo("r2"); //unused value
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

        descendStatements(node);

        return out;
        }



}
