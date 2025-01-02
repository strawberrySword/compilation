package AST;
import TYPES.*;

public class AST_CLASS_FIELD_LIST extends AST_Node /*TODO: determine inheritance*/ {

	public AST_DEC value;
	public AST_CLASS_FIELD_LIST next;

	public AST_CLASS_FIELD_LIST(AST_DEC d, AST_CLASS_FIELD_LIST l) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
	}

	public void PrintMe(){
		System.out.format("CfieldList");

		if(this.next != null) this.next.PrintMe();

		this.value.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("CfieldList"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}
}