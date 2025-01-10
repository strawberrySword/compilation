package AST;
import TYPES.*;

public class AST_NIL extends AST_EXP {
	public AST_NIL(int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Nil");

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Nil"));
	}

	@Override
	public TYPE SemantMe(){
		return TYPE_NIL.getInstance();
	}
}