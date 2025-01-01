package AST;
import TYPES.*;

public class AST_VAR_DEC extends AST_DEC /*TODO: determine inheritance*/ {
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	public AST_NEW_EXP newExp;

	public AST_VAR_DEC(AST_TYPE t, String n, AST_EXP e, AST_NEW_EXP ne) {
		this.type = t;
		this.name = n;
		this.exp = e;
		this.newExp = ne;
	}
}