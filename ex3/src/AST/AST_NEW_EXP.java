package AST;

public class AST_NEW_EXP extends AST_EXP /*TODO: determine inheritance*/ {

	public AST_TYPE type;
	public AST_EXP len;

	public AST_NEW_EXP(AST_TYPE t, AST_EXP e) {
		this.type = t;
		this.len = e;
	}
}