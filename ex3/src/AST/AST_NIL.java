package AST;
import TYPES.*;

public class AST_NIL extends AST_EXP {
	public AST_NIL() {}

	public void PrintMe(){
		System.out.format("Nil");

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Nil"));
	}
}