package AST;
import TYPES.*;

public class AST_STMT_LIST extends AST_Node /*TODO: determine inheritance*/ {
	public AST_STMT value;
	public AST_STMT_LIST next;

	public AST_STMT_LIST(AST_STMT d, AST_STMT_LIST l) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
	}

	public void PrintMe(){
		System.out.format("StmtList");

		this.value.PrintMe();
		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("StmtList"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);
		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}
}