import java.util.ArrayList;
import java.util.List;

public class BlockNode extends ASTNode {
    List<ASTNode> statements = new ArrayList<>();

    BlockNode(List<ASTNode> statements) {
        this.statements = statements;
    }
}
