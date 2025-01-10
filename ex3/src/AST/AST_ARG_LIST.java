package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_ARG_LIST extends AST_Node {

	public AST_TYPE type;
	public String name;
	public AST_ARG_LIST next;

	public AST_ARG_LIST(AST_TYPE t, String n, AST_ARG_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.type = t;
		this.name = n;
		this.next = l;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Arg(%s, %s)", this.type.myType, this.name);

		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Arg(%s, %s)", this.type.myType, this.name));

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}

	public TYPE_LIST SemantMe(){
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();


		if (t.findInScope(this.name)!= null){ // double argument definition "int f(int x,int x){}"
			SYMBOL_TABLE.getInstance().writeError(this.lineNum);
			System.out.println("Semantic error: double definition of function argument "+this.name);
			System.exit(0);
		}
		
		t.enter(this.name, this.type.SemantMe());
		
		if (this.next == null){
			return new TYPE_LIST(this.type.SemantMe(), null);
		}

		return new TYPE_LIST(this.type.SemantMe(), this.next.SemantMe());
	}
}