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

	public TYPE SemantMe(){
		TYPE t = this.var.SemantMe();
		if (!(t instanceof TYPE_CLASS)){
			System.out.format(">> ERROR [%d:%d] access field of non-class variable\n",6,6);
			System.exit(0);
			return null;
		}
		TYPE_CLASS tc = (TYPE_CLASS)t;
		TYPE tf = tc.findField(this.fieldName);
		if (tf == null){
			System.out.format(">> ERROR [%d:%d] field %s does not exist in class\n",6,6,this.fieldName);
			System.exit(0);
			return null;
		}
		return tf;
	}
}