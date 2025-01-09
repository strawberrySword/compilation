package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

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

	@Override
	public void PrintMe(){
		System.out.format("ClassDec(%s, %s)", this.cName, this.parentName);

		if(this.fields != null) this.fields.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ClassDec(%s, %s)", this.cName, this.parentName));

		if (this.fields != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.fields.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();
		
		TYPE father = null;
		if (this.parentName != null){
			father = t.find(this.parentName);
		}
		
		if (father == null && this.parentName != null){ // "class son extends father{}; class father{};"
			System.out.println("Semantic error: parent class undefined");
			System.exit(0);
		}
		if (father != null && !(father instanceof TYPE_CLASS)){
			System.out.println("Semantic error: cannot extend non-class type");
			System.exit(0);
		}

		t.beginScope("Class");
		TYPE_LIST f = new TYPE_LIST(null, null);
		TYPE_CLASS c = new TYPE_CLASS((TYPE_CLASS)father, cName, f);
		t.enter(cName, c);
		fields.SemantMe((TYPE_CLASS)father, c.data_members);

		TYPE_LIST templist = c.data_members;
		while(templist != null){
			System.out.println("filed####################"+templist.head.name+"\n");
			templist = templist.tail;
		}

		t.endScope();

		t.enter(cName, c);

		return c;
	}
}