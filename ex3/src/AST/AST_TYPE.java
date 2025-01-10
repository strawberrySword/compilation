package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_TYPE extends AST_Node {

	public String myType;

	public AST_TYPE(String t, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.myType = t;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Type(%s)", this.myType);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Type(%s)", this.myType));
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE a = t.find(this.myType);
		if(a == null){
			System.out.println("Semantcic error: type "+myType+" does not exist");
			System.exit(0);
		}

		return a;
	}
}