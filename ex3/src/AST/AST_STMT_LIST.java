package AST;
import TYPES.*;

public class AST_STMT_LIST extends AST_Node /*TODO: determine inheritance*/ {
	public AST_STMT value;
	public AST_STMT_LIST next;

	public AST_STMT_LIST(AST_STMT d, AST_STMT_LIST l) {
		this.value = d;
		this.next = l;
	}
}