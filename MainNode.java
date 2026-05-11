import java.util.List;

public class MainNode extends ASTNode {
    List<String> params;
    BlockNode body;
    public MainNode(List<String> params, BlockNode body) {
        this.params = params;
        this.body = body;
    }
}
