package AST;
import TYPES.*;

public class AST_BINOP_EXP extends AST_EXP {

	public AST_EXP left;
	public String op;
	public AST_EXP right;

	public AST_BINOP_EXP(AST_EXP l,String o,AST_EXP r) {
		this.left = l;
		this.right = r;
		this.op = o;
	}
}