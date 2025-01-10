package AST;
import TYPES.*;

public class AST_CLASS_FIELD_LIST extends AST_Node {

	public AST_DEC value;
	public AST_CLASS_FIELD_LIST next;

	public AST_CLASS_FIELD_LIST(AST_DEC d, AST_CLASS_FIELD_LIST l, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
		this.lineNum = line;
	}

    @Override
	public void PrintMe(){
		System.out.format("CfieldList");

		if(this.next != null) this.next.PrintMe();

		this.value.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("CfieldList"));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.value.SerialNumber);

		if (this.next != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.next.SerialNumber);
	}

	public TYPE SemantMe(TYPE_CLASS father, TYPE_LIST l){
		TYPE h;

		if (this.value instanceof AST_FUNC_DEC ast_func_dec){
			h = ast_func_dec.SemantMe(father);
		}else{
			h = this.value.SemantMe(); 
		}

		l.head = h;
		if (this.next == null){
			return l;
		}
		l.tail = new TYPE_LIST(null, null);
		l.tail = (TYPE_LIST)this.next.SemantMe(father, l.tail);
		return l;
	}
}