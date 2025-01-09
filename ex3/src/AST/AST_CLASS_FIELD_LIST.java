package AST;
import TYPES.*;

public class AST_CLASS_FIELD_LIST extends AST_Node {

	public AST_DEC value;
	public AST_CLASS_FIELD_LIST next;

	public AST_CLASS_FIELD_LIST(AST_DEC d, AST_CLASS_FIELD_LIST l) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.value = d;
		this.next = l;
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

		if (this.next == null){
			l = new TYPE_LIST(h, null);
		}else{
			l = new TYPE_LIST(h, (TYPE_LIST)this.next.SemantMe(father, l));
		}

		return l;
	}
}