package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_STMT_WHILE extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.cond = cond;
		this.body = body;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_while");

		this.cond.PrintMe();
		this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_while"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		TYPE t = cond.SemantMe();
		if (t != TYPE_INT.getInstance()){
			System.out.format(">> condition inside while statement is not of type int\n");
			System.exit(0);
			return null;
		}
		SYMBOL_TABLE.getInstance().beginScope("Loop");
		
		body.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
}