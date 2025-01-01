package AST;
import TYPES.*;

public class AST_ARG_LIST extends AST_Node /*TODO: determine inheritance*/ {

	public AST_TYPE type;
	public String name;
	public AST_ARG_LIST next;

	public AST_ARG_LIST(AST_TYPE t, String n, AST_ARG_LIST l) {
		this.type = t;
		this.name = n;
		this.next = l;
	}
}