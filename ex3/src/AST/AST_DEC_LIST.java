package AST;
import TYPES.*;

public class AST_DEC_LIST extends AST_Node {
	public AST_DEC value;
	public AST_DEC_LIST next;

	public AST_DEC_LIST(AST_DEC d, AST_DEC_LIST l) {
		this.value = d;
		this.next = l;
	}
}