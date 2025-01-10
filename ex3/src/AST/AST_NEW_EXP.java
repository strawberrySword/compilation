package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_INT;

public class AST_NEW_EXP extends AST_EXP {

	public AST_TYPE type;
	public AST_EXP len;

	public AST_NEW_EXP(AST_TYPE t, AST_EXP e, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.type = t;
		this.len = e;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("NewExp(%s)", this.type.myType);


		if (this.len != null) this.len.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("NewExp(%s)", this.type.myType));
		if (this.len != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.len.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		// check if a class is instantiated.
		// or array type
		// may be Nil
		
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE instanceDataType = t.find(this.type.myType);

		if(len == null){ // class instance
			if(!(instanceDataType instanceof TYPE_CLASS)){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: no such class");
				System.exit(0);
			}
			return instanceDataType;
		}

		if (this.len instanceof AST_INT && ((AST_INT)this.len).val < 0){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: array subscript is less than 0");
			System.exit(0);
		}
		TYPE lenType = this.len.SemantMe(); 
		if (!(lenType instanceof TYPE_INT)){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: array subscript must be of type int");
				System.exit(0);
		}
		
		if(instanceDataType == null){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: no such type");
			System.exit(0);
		}

		return new TYPE_ARRAY(instanceDataType.name,instanceDataType);
	}
}