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

	public void PrintMe(){
		System.out.format("FuncCall(%s)", this.fName);

		if(this.var != null) this.var.PrintMe();
		if(this.args != null) this.args.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("FuncCall(%s)", this.fName));
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		if (this.args != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.args.SerialNumber);
	}

	public TYPE SemantMe(){
		// 1. make sure function is defined and get its type
		// TODO: funding the function should be scope sensetive maybe should change how we save function or write a method for it
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
		function = (TYPE_FUNCTION)function;
		// 2. make sure args match function signature
		TYPE_LIST argsTypes = args.SemantMe();

		argsTypes.compare()
		// 3. return function return type

		if(retType == null){
			// TODO report error
			System.exit(0);
			return null;
		}
	}
}