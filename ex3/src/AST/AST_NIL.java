package AST;
import TYPES.*;

public class AST_NIL extends AST_EXP {
	public AST_NIL() {this.SerialNumber = AST_Node_Serial_Number.getFresh();}

	public void PrintMe(){
		System.out.format("Nil");

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Nil"));
	}

	public TYPE SemantMe(){
		return TYPE_NIL.getInstance();
	}
}