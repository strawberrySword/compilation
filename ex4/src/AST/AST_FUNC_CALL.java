package AST;
import IR.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import TYPES.*;

public class AST_FUNC_CALL extends AST_EXP {

	public AST_VAR var;
	public String fName;
	public AST_EXP_LIST args;

	/* Call without args */
	public AST_FUNC_CALL(AST_VAR v, String f, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = v;
		this.fName = f;
		this.args =  null;
		this.lineNum = line;
	}
	
	/* Call with args */
	public AST_FUNC_CALL(AST_VAR v, String f, AST_EXP a1, AST_EXP_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = v;
		this.fName = f;
		this.args = new AST_EXP_LIST(a1,l, line);
		this.lineNum = line;
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
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		TYPE_CLASS thisClass = null;

		TYPE function = table.findInScope(this.fName);

		if (function == null && table.inClassDef()){
			thisClass = (TYPE_CLASS)table.WhichClassAmIIn();
			function = thisClass.findField(this.fName);
		}
		if (function == null){
			function = table.find(this.fName);
		}

		if(var != null){
			TYPE varClass = var.SemantMe();
			if(!(varClass instanceof TYPE_CLASS)){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: var is not a class");
				System.exit(0);
				return null;
			}
			function = ((TYPE_CLASS)varClass).findField(fName);
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

    @Override
	public TEMP IRme()
	{
		TEMP t=null;
		
		if (args != null) { t = args.value.IRme(); }
		
		IR.getInstance().Add_IRcommand(new IRcommand_PrintInt(t));
		
		return null;
	}

}