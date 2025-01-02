package AST;
import TYPES.*;

public class AST_EXP_LIST extends AST_Node /*TODO: determine inheritance*/ {

	public AST_EXP value;
	public AST_EXP_LIST next;
	
	public AST_EXP_LIST(AST_EXP e, AST_EXP_LIST l) {
		this.value = e;
		this.next = l;
	}

	public void PrintMe(){
		System.out.format("ExpList");

		if(this.next != null) this.next.PrintMe();

		this.value.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ExpList"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}
}