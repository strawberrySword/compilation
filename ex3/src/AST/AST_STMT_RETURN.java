package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

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

	public TYPE SemantMe(String fName, TYPE t) {
		if(!SYMBOL_TABLE.getInstance().inFunctionDef()) {
			System.out.format(">> ERROR at line %d\n", 3);
			System.exit(0);
		}

		if (this.retVal != null) {
			TYPE retType = this.retVal.SemantMe();
			if (!t.equals(retType)) {
				System.out.format(">> ERROR at line %d\n", 3);
				System.exit(0);
			}
		}
		else {
			if (t != TYPE_VOID.getInstance()) {
				System.out.format(">> ERROR at line %d\n", 3);
				System.exit(0);
			}
		}
		if (this.retVal != null) {
			t = this.retVal.SemantMe();
		}
		return t;
	}
}