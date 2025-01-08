package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CLASS_DEC extends AST_DEC {

	public String cName;
	public String parentName;
	public AST_CLASS_FIELD_LIST fields;

	public AST_CLASS_DEC(String myName, String pName, AST_CLASS_FIELD_LIST f) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.cName = myName;
		this.parentName = pName;
		this.fields = f;
	}

	public void PrintMe(){
		System.out.format("ClassDec(%s, %s)", this.cName, this.parentName);

		if(this.fields != null) this.fields.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ClassDec(%s, %s)", this.cName, this.parentName));

		if (this.fields != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.fields.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE.getInstance().beginScope();

		TYPE_CLASS t = new TYPE_CLASS(null, cName, fields.SemantMe());

		SYMBOL_TABLE.getInstance().endScope();

		SYMBOL_TABLE.getInstance().enter(cName,t);
		return null;
	}
}