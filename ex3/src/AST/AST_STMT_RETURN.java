package AST;

public class AST_STMT_RETURN extends AST_STMT {
	AST_EXP retVal;

	public AST_STMT_RETURN(AST_EXP retVal) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.retVal = retVal;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_ret");

		if (this.retVal != null) {this.retVal.PrintMe();}
		else {AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_ret(null)")); return;}
	
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_ret"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.retVal.SerialNumber);
	}

	@Override 
	public TYPE SemantMe() {
		TYPE t = null;
		if (this.retVal != null) {
			t = this.retVal.SemantMe();
		}
		return t;
	}
}