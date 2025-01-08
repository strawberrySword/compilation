package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_VAR_SIMPLE extends AST_VAR {

	public String name;

	public AST_VAR_SIMPLE(String n) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.name = n;
	}

	public void PrintMe(){
		System.out.format("var_simple(%s)", this.name);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_simple(%s)", this.name));
	}

	public TYPE SemantMe(){
		return SYMBOL_TABLE.getInstance().find(name);
	}
}