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

	public void PrintMe(){
		System.out.format("ArgList(%s, %s)", this.type.myType, this.name);

		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ArgList(%s, %s)", this.type.myType, this.name));

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}
}