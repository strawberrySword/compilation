package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_FUNCTION_CALL extends AST_STMT {
	AST_VAR var;
	String funcName;
	AST_EXP_LIST args;

	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName, AST_EXP arg1, AST_EXP_LIST args, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args, line);
		this.lineNum = line;
	}
	public AST_STMT_FUNCTION_CALL(AST_VAR var, String funcName, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
		this.funcName = funcName;
		this.args = null;
		this.lineNum = line;
	}
	public AST_STMT_FUNCTION_CALL(String funcName, AST_EXP arg1, AST_EXP_LIST args, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = null;
		this.funcName = funcName;
		this.args = new AST_EXP_LIST(arg1, args, line);
		this.lineNum = line;
	}
	public AST_STMT_FUNCTION_CALL(String funcName, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = null;
		this.funcName = funcName;
		this.args = null;
		this.lineNum = line;
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
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		TYPE_CLASS thisClass = null;

		TYPE function = table.findInScope(this.funcName);

		if (function == null && table.inClassDef()){
			thisClass = (TYPE_CLASS)table.WhichClassAmIIn();
			function = thisClass.findField(this.funcName);
		}
		if (function == null){
			function = table.find(this.funcName);
		}

		if(var != null){
			TYPE varClass = var.SemantMe();
			if(!(varClass instanceof TYPE_CLASS)){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: var is not a class");
				System.exit(0);
				return null;
			}
			function = ((TYPE_CLASS)varClass).findField(funcName);
		}

		if(!(function instanceof TYPE_FUNCTION)){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: funcName is not a function");
			System.exit(0);
			return null;
		}

		// 2. make sure args match function signature
		if(args != null) {
			TYPE_LIST argsTypes = args.SemantMe();
		 
			if(!(argsTypes.matches(((TYPE_FUNCTION)function).params))){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("args do not match function signature");
				System.exit(0);
				return null;
			}
		} else {
			if(((TYPE_FUNCTION)function).params != null){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("args do not match function signature");
				System.exit(0);
				return null;
			}
		}

		return ((TYPE_FUNCTION)function).returnType;
	}
}