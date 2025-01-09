package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_FUNCTION_CALL extends AST_STMT {
	AST_VAR var;
	String funcName;
	AST_EXP_LIST args;

	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args);
	}
	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
		this.funcName = funcName;
		this.args = null;
	}
	public AST_STMT_FUNCTION_CALL(String funcName, AST_EXP arg1, AST_EXP_LIST args) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = null;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args);
	}
	public AST_STMT_FUNCTION_CALL(String funcName) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = null;
		this.funcName = funcName;
		this.args = null;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_FuncCall(%s)", this.funcName);

		if (this.var != null) this.var.PrintMe();
		if (this.args != null) this.args.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_FuncCall(%s)", this.funcName));
	
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		if (this.args != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.args.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		// 1. make sure function is defined and get its type
		TYPE function;
		function = SYMBOL_TABLE.getInstance().find(funcName);
		if(var != null){
			TYPE varClass = var.SemantMe();
			if(!(varClass instanceof TYPE_CLASS)){
				System.out.println("Semantic error: var is not a class");
				System.exit(0);
				return null;
			}
			function = ((TYPE_CLASS)varClass).findField(funcName);
		}

		if(!(function instanceof TYPE_FUNCTION)){
			System.out.println("Semantic error: funcName is not a function");
			System.exit(0);
			return null;
		}

		// 2. make sure args match function signature
		if(args != null) {
			TYPE_LIST argsTypes = args.SemantMe();
		 
			if(!(argsTypes.matches(((TYPE_FUNCTION)function).params))){
				System.out.println("args do not match function signature");
				System.exit(0);
				return null;
			}
		} else {
			if(((TYPE_FUNCTION)function).params != null){
				System.out.println("args do not match function signature");
				System.exit(0);
				return null;
			}
		}

		return ((TYPE_FUNCTION)function).returnType;
	}
}