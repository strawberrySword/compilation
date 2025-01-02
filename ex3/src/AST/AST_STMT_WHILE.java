package AST;

public class AST_STMT_WHILE extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body) {
		this.cond = cond;
		this.body = body;
	}

	public void PrintMe(){
		System.out.format("Stmt_while");

		this.cond.PrintMe();
		this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_while"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}
}