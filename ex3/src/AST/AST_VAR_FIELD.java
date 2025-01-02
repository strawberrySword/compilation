package AST;
import TYPES.*;

public class AST_VAR_FIELD extends AST_VAR {

	public AST_VAR var;
	public String fieldName;

	public AST_VAR_FIELD(AST_VAR v, String f) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = v;
		this.fieldName = f;
	}

	public void PrintMe(){
		System.out.format("Var_field(%s)", this.fieldName);

		if (this.var != null) this.var.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_field(%s)", this.fieldName));
	
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
	}
}