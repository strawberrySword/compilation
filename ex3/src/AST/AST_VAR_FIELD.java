package AST;
import TYPES.*;

public class AST_VAR_FIELD extends AST_VAR {

	public AST_VAR var;
	public String fieldName;

	public AST_VAR_FIELD(AST_VAR v, String f) {
		this.var = v;
		this.fieldName = f;
	}
}