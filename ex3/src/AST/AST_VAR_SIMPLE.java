package AST;
import TYPES.*;

public class AST_VAR_SIMPLE extends AST_VAR {

	public String name;

	public AST_VAR_SIMPLE(String n) {
		this.name = n;
	}

	public void PrintMe(){
		System.out.format("var_simple(%s)", this.name);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_simple(%s)", this.name));
	}
}