package AST;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR {

	public AST_VAR var;
	public AST_EXP exp;

	public AST_VAR_SUBSCRIPT(AST_VAR v, AST_EXP e) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = v;
		this.exp = e;
	}

	@Override
	public void PrintMe(){
		System.out.format("Var_subscript");

		if (this.var != null) this.var.PrintMe();
		if (this.exp != null) this.exp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_subscript"));
	
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
		if (this.exp != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.exp.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		TYPE t = var.SemantMe();

		if(!(t instanceof TYPE_ARRAY)){
			System.out.format(">> ERROR [%d:%d] access operator [] applied to non-array\n",6,6);
			System.exit(0);
		}

		return ((TYPE_ARRAY)t).dataType;
	}
}