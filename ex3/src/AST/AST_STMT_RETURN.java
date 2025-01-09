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
		if(!(SYMBOL_TABLE.getInstance().inFunctionDef())) {
			System.out.println("Semantic error: return statement outside of function");
			System.exit(0);
		}

		TYPE retType = null;
		if (retVal != null){
			retType = retVal.SemantMe();
		}

		if(retType == null) {
			if(t != TYPE_VOID.getInstance()) {
				System.out.format("Semantic error: this function must return void");
				System.exit(0);
			}
			return TYPE_VOID.getInstance();
		}
		
		if(!(retType.name.equals(((TYPE_FUNCTION)t).returnType.name))) {
			System.out.format("Semantic error: returned value must of the said type");
			System.exit(0);
		}

		return t;
	}
}