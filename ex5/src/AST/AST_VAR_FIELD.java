package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VAR_FIELD extends AST_VAR {

	public AST_VAR var;
	public String fieldName;

	public AST_VAR_FIELD(AST_VAR v, String f, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.var = v;
		this.fieldName = f;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Var_field(%s)", this.fieldName);

		if (this.var != null) this.var.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_field(%s)", this.fieldName));
	
		if (this.var != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.var.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		TYPE t = this.var.SemantMe();
		if (!(t instanceof TYPE_CLASS)){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> ERROR access field of non-class variable\n");
			System.exit(0);
			return null;
		}
		TYPE_CLASS tc = (TYPE_CLASS)t;
		TYPE tf = tc.findField(this.fieldName);
		if (tf == null){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> ERROR field does not exist in class\n");
			System.exit(0);
			return null;
		}
		if (tf instanceof TYPE_VAR_DEC type_var_dec){
			return type_var_dec.t;
		}
		return tf;
	}
}