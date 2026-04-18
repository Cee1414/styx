import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends ASTNode {
    List<ASTNode> statements = new ArrayList<>();

    ProgramNode(List<ASTNode> statements) {
        this.statements = statements;
    }
}
