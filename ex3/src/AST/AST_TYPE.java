package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_TYPE extends AST_Node {

	public String myType;

	public AST_TYPE(String t) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.myType = t;
	}

	@Override
	public void PrintMe(){
		System.out.format("Type(%s)", this.myType);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Type(%s)", this.myType));
	}

	public TYPE SemantMe(){
		TYPE t= SYMBOL_TABLE.getInstance().find(this.myType);
		if(t == null){
			// TODO: print error to file
			System.exit(0);
			return null;
		}

		return t;
	}
}