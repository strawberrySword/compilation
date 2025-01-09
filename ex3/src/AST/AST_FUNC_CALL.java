package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_FUNC_CALL extends AST_EXP {

	public AST_VAR var;
	public String fName;
	public AST_EXP_LIST args;

	/* Call without args */
	public AST_FUNC_CALL(AST_VAR v, String f) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = v;
		this.fName = f;
		this.args =  null;
	}
	
	/* Call with args */
	public AST_FUNC_CALL(AST_VAR v, String f, AST_EXP a1, AST_EXP_LIST l) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = v;
		this.fName = f;
		this.args = new AST_EXP_LIST(a1,l);
	}

	@Override
	public void PrintMe(){
		System.out.format("FuncCall(%s)", this.fName);

		if(this.var != null) this.var.PrintMe();
		if(this.args != null) this.args.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("FuncCall(%s)", this.fName));
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		if (this.args != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.args.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		// 1. make sure function is defined and get its type
		TYPE function;
		function = SYMBOL_TABLE.getInstance().find(fName);
		if(var != null){
			TYPE varClass = var.SemantMe();
			if(!(varClass instanceof TYPE_CLASS)){
				// var is not a class
				// TODO report error to file
				System.exit(0);
				return null;
			}
			function = ((TYPE_CLASS)varClass).findField(fName);
		}

		if(!(function instanceof TYPE_FUNCTION)){
			// funcName is not a function
			// TODO report error to file
			System.exit(0);
			return null;
		}
		// 2. make sure args match function signature
		TYPE_LIST argsTypes = args.SemantMe();

		if(!argsTypes.matches(((TYPE_FUNCTION)function).params)){
			// args do not match function signature
			// TODO report error to file: args do not match function signature
			System.exit(0);
			return null;
		}
		// 3. return function return type

		if(((TYPE_FUNCTION)function).returnType == null){
			// TODO report error
			System.exit(0);
			return null;
		}
		return ((TYPE_FUNCTION)function).returnType;
	}
}