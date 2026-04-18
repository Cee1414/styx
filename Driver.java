import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

public class Driver {
    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromStream(System.in);
        Lexer lexer = new styxLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        styxParser parser = new styxParser(tokens);
        ParseTree tree = parser.prog();

        ASTBuilder builder = new ASTBuilder();
    
        // Print AST
        System.out.println("print AST");
        ASTPrinter.printAST(builder.visit(tree), 0);
        //Verify assignment
        System.out.println("\nsemantic check");
        Semantic verifier = new Semantic();
        verifier.checkAssignment(builder.visit(tree));
        System.out.println("\nverified assignment");
    }
}
