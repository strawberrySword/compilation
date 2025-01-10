package AST;
import TYPES.*;

public class AST_STMT_LIST extends AST_Node {
	public AST_STMT value;
	public AST_STMT_LIST next;

	public AST_STMT_LIST(AST_STMT d, AST_STMT_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("StmtList");

		this.value.PrintMe();
		if(this.next != null) this.next.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("StmtList"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);
		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}

	public TYPE SemantMe(){
		value.SemantMe();

		if (this.next != null){
			next.SemantMe();
		}
		
		return null;
	}
	public TYPE SemantMe(String fName, TYPE t){
		value.SemantMe();

		if (this.next != null){
			if(this.next.value instanceof AST_STMT_RETURN ast_stmt_return){
				ast_stmt_return.SemantMe(fName, t);
			}
			else{
				next.SemantMe();
			}
		}
		
		return null;
	}
}