package AST;
import TYPES.*;

public class AST_TYPE extends AST_Node /*TODO: determine inheritance*/ {

	public String myType;

	public AST_TYPE(String t) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.myType = t;
	}

	public void PrintMe(){
		System.out.format("Type(%s)", this.myType);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Type(%s)", this.myType));
	}
}