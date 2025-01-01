package AST;
import TYPES.*;

public class AST_CLASS_FIELD_LIST extends AST_Node /*TODO: determine inheritance*/ {

	public AST_DEC value;
	public AST_CLASS_FIELD_LIST next;

	public AST_CLASS_FIELD_LIST(AST_DEC d, AST_CLASS_FIELD_LIST l) {
		this.value = d;
		this.next = l;
	}
}