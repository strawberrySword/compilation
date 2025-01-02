package AST;

public class AST_STMT_IF extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body ) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.cond = cond;
		this.body = body;	
	}

	public void PrintMe(){
		System.out.format("Stmt_if");

		this.cond.PrintMe();
		this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_if"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}
}