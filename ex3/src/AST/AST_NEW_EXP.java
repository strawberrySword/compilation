package AST;
import TYPES.*;

public class AST_NEW_EXP extends AST_Node /*TODO: determine inheritance*/ {

	public AST_TYPE type;
	public AST_EXP len;

	public AST_NEW_EXP(AST_TYPE t, AST_EXP e) {
		this.type = t;
		this.len = e;
	}
}