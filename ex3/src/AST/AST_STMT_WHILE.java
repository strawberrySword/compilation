package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_STMT_WHILE extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.cond = cond;
		this.body = body;
		this.lineNum = line;
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
		SYMBOL_TABLE.getInstance().beginScope("Loop");
		
		TYPE t = cond.SemantMe();
		if (t != TYPE_INT.getInstance()){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> condition inside while statement is not of type int\n");
			System.exit(0);
			return null;
		}
		
		body.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}
}