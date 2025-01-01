package AST;
import TYPES.*;

public class AST_DEC_LIST extends AST_Node {
	public AST_DEC value;
	public AST_DEC_LIST next;

	public AST_DEC_LIST(d, n) {
		this.value = d;
		this.next = n;
	}
}