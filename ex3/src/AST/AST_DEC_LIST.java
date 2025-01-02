package AST;
import TYPES.*;

public class AST_DEC_LIST extends AST_Node {
	public AST_DEC value;
	public AST_DEC_LIST next;

	public AST_DEC_LIST(AST_DEC d, AST_DEC_LIST l) {
		this.value = d;
		this.next = l;
	}

	public void PrintMe(){
		System.out.format("DecList");

		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("DecList"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}
}