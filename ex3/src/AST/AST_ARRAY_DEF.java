package AST;
import TYPES.*;

public class AST_ARRAY_DEF extends AST_DEC {

	public String arrName;
	public AST_TYPE type;

	public AST_ARRAY_DEF(String s, AST_TYPE t) {
		this.arrName = s;
		this.type = t;
	}

	public void PrintMe(){
		System.out.format("ArrayDef(%s, %s)", this.type.myType, this.arrName);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ArgList(%s, %s)", this.type.myType, this.arrName));
	}
}