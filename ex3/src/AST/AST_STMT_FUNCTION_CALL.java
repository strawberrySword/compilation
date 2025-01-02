package AST;

public class AST_STMT_FUNCTION_CALL extends AST_STMT {
	AST_VAR var;
	String funcName;
	AST_EXP_LIST args;

	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.var = var;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args);
	}
	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName) {
		this.var = var;
		this.funcName = funcName;
		this.args = null;
	}
	public AST_STMT_FUNCTION_CALL(String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.var = null;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args);
	}
	public AST_STMT_FUNCTION_CALL(String funcName) {
		this.var = null;
		this.funcName = funcName;
		this.args = null;
	}

	public void PrintMe(){
		System.out.format("Stmt_FuncCall(%s)", this.funcName);

		if (this.var != null) this.var.PrintMe();
		if (this.args != null) this.args.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_FuncCall(%s)", this.funcName));
	
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		if (this.args != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.args.SerialNumber);
	}
}