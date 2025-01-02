package AST;
import TYPES.*;

public class AST_INT extends AST_EXP {

	public int val;

	public AST_INT(int i) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.val = i;
	}

	public void PrintMe(){
		System.out.format("Int(%d)", this.val);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Int(%s)", this.val));
	}
}