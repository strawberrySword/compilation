package AST;
import TYPES.*;
import TEMP.*;

public class AST_EXP_LIST extends AST_Node {

	public AST_EXP value;
	public AST_EXP_LIST next;
	
	public AST_EXP_LIST(AST_EXP e, AST_EXP_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = e;
		this.next = l;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("ExpList");

		if(this.next != null) this.next.PrintMe();

		this.value.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ExpList"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}

	public TYPE_LIST SemantMe(){		
		TYPE expType = value.SemantMe();
		
		if (this.next == null){
			return new TYPE_LIST(expType, null);
		}

		return new TYPE_LIST(expType, this.next.SemantMe());
	}

	public TEMP IRme(){ // no returns here since we just want to generate the IR 
		this.value.IRme();

		if (this.next != null){
			this.next.IRme();
		}

		return null;
	}
}