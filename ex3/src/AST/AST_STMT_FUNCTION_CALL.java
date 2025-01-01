package AST;

public class AST_STMT_FUNCTION_CALL extends AST_STMT /*TODO: determine inheritance*/ {
	AST_VAR var;
	String funcName;
	AST_EXP arg1;
	AST_EXP_LIST args;

	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.var = var;
		this.funcName = funcName;
		this.arg1 = arg1;
		this.args = args;
	}
	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName) {
		this.var = var;
		this.funcName = funcName;
	}
	public AST_STMT_FUNCTION_CALL(String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.funcName = funcName;
		this.arg1 = arg1;
		this.args = args;
	}
	public AST_STMT_FUNCTION_CALL(String funcName) {
		this.funcName = funcName;
	}
}