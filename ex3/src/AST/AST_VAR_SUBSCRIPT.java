package AST;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR {

	public AST_VAR var;
	public AST_EXP exp;

	public AST_VAR_SUBSCRIPT(AST_VAR v, AST_EXP e) {
		this.var = v;
		this.exp = e;
	}
}