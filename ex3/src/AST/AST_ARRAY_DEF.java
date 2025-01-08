package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_ARRAY_DEF extends AST_DEC {

	public String arrName;
	public AST_TYPE type;

	public AST_ARRAY_DEF(String s, AST_TYPE t) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.arrName = s;
		this.type = t;
	}

	public void PrintMe(){
		System.out.format("ArrayDef(%s, %s)", this.type.myType, this.arrName);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("ArgList(%s, %s)", this.type.myType, this.arrName));
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE data_type = t.find(this.type.myType);
		if (data_type == null){
			System.out.println("Semantic error: array type undefined");
			System.exit(0);
		}
		TYPE_ARRAY arr = new TYPE_ARRAY(arrName, data_type);
		t.enter(arrName, arr);
		return arr;
	}
}