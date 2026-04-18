public class AssignNode extends ASTNode {
   IdNode id;
   ASTNode value;

   public AssignNode(IdNode id, ASTNode value) {
      this.id = id;
      this.value = value;
   }
}
