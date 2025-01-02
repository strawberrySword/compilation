package AST;
import TYPES.*;

public class AST_FUNC_CALL extends AST_EXP {

	public AST_VAR var;
	public String fName;
	public AST_EXP_LIST args;

	/* Call without args */
	public AST_FUNC_CALL(AST_VAR v, String f) {
		this.var = v;
		this.fName = f;
		this.args =  null;
	}
	
	/* Call with args */
	public AST_FUNC_CALL(AST_VAR v, String f, AST_EXP a1, AST_EXP_LIST l) {
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
}