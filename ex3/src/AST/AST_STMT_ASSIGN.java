package AST;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT {
	AST_VAR var;
	AST_EXP exp;

	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = var;
		this.exp = exp;
	}

	public void PrintMe(){
		System.out.format("Stmt_asgn");

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_asgn"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.exp.SerialNumber);
	}
}