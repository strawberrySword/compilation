package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT {
	AST_VAR var;
	AST_EXP exp;

	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = var;
		this.exp = exp;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_asgn");


		this.var.PrintMe();
		this.exp.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_asgn"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.exp.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		TYPE t1 = var.SemantMe();
		TYPE t2 = exp.SemantMe();
		if(t1 instanceof TYPE_VAR_DEC type_var_dec){
			t1 = type_var_dec.t;
		}
		if(t2 instanceof TYPE_VAR_DEC type_var_dec){
			t2 = type_var_dec.t;
		}
		if((t1 instanceof TYPE_ARRAY || t1 instanceof TYPE_CLASS) && t2 == TYPE_NIL.getInstance()){
			return null;
		}

		if (t1 instanceof TYPE_ARRAY && t2 instanceof TYPE_ARRAY){
			if (!(t1.name.equals(t2.name))){ // two arrays must be of exactly the same type
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: assignment type mismatch");
				System.exit(0);
			}

			return null;
		}

		if (!t1.inheritsFrom(t2)) {
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println(">> ERROR type mismatch for var := exp\n  var: " + t1 + "\n   exp: " + t2);
			System.exit(0);
		}
		return null;
	}
}