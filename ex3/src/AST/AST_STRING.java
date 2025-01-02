package AST;
import TYPES.*;

public class AST_STRING extends AST_EXP {

	public String val;

	public AST_STRING(String s) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.val = s;
	}

	public void PrintMe(){
		System.out.format("String(%s)", this.val);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("String(%s)", this.val));
	}
}