package AST;
import TYPES.*;

public class AST_EXP_LIST extends AST_Node /*TODO: determine inheritance*/ {

	public AST_EXP value;
	public AST_EXP_LIST next;
	
	public AST_EXP_LIST(AST_EXP e, AST_EXP_LIST l) {
		this.value = e;
		this.next = l;
	}
}