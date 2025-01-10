package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_RETURN extends AST_STMT {
	AST_EXP retVal;

	public AST_STMT_RETURN(AST_EXP retVal, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.retVal = retVal;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_ret");

		if (this.retVal != null) {this.retVal.PrintMe();}
		else {AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_ret(null)")); return;}
	
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_ret"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.retVal.SerialNumber);
	}

	public TYPE SemantMe(String fName, TYPE t) {
		if(!(SYMBOL_TABLE.getInstance().inFunctionDef())) {
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("\nSemantic error: return statement outside of function\n");
			System.exit(0);
		}

		if(retVal == null) {
			if(((TYPE_FUNCTION)t).returnType != TYPE_VOID.getInstance()) {
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("\nSemantic error: this function must return void\n");
				System.exit(0);
			}
			return TYPE_VOID.getInstance();
		}

		TYPE retType = retVal.SemantMe();
		if(!(retType.name.equals(((TYPE_FUNCTION)t).returnType.name))) {
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("\nSemantic error: returned value must of the said type\n");
			System.exit(0);
		}
		return t;
	}
}