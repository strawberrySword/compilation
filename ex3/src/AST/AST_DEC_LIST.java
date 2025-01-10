package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import java.io.PrintWriter;

public class AST_DEC_LIST extends AST_Node {
	public AST_DEC value;
	public AST_DEC_LIST next;

	public AST_DEC_LIST(AST_DEC d, AST_DEC_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
		this.lineNum = line;
	}
	@Override
	public void PrintMe(){
		System.out.format("DecList");

		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("DecList"));
		this.value.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);
		
		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}

	public TYPE SemantMe(PrintWriter file_writer){
		SYMBOL_TABLE.getInstance().registerWriter(file_writer);
		return SemantMe();
	}

	public TYPE SemantMe(){
		this.value.SemantMe();

		if(this.next != null){
			this.next.SemantMe();
		}

		return null;
	}
}