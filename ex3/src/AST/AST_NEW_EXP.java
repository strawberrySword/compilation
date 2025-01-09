package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_INT;
import TYPES.TYPE_NIL;

public class AST_NEW_EXP extends AST_EXP {

	public AST_TYPE type;
	public AST_EXP len;

	public AST_NEW_EXP(AST_TYPE t, AST_EXP e) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.type = t;
		this.len = e;
	}

	public void PrintMe(){
		System.out.format("NewExp(%s)", this.type.myType);

		this.len.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("NewExp(%s)", this.type.myType));
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.len.SerialNumber);
	}

	public TYPE SemantMe(){
		// check if a class is instantiated.
		// or array type
		// may be Nil
		
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE instanceType = t.find(this.type.myType);

		//check that the thing I got is a real type and not "(x, TYPE_INT)"
		if (!(instanceType.name.equals(this.type.myType))){
			System.out.println("Semantic error: no such type");
			System.exit(0);
		}

		// only class, array, or NIL instantiation is allowed
		if (!(instanceType instanceof TYPE_CLASS) && !(instanceType instanceof TYPE_ARRAY) && !(instanceType instanceof TYPE_NIL)){
			System.out.println("Semantic error: can only instantiate class or array");
			System.exit(0);
		}

		if (instanceType instanceof TYPE_CLASS){
			return (TYPE_CLASS)instanceType;
		}
		if (instanceType instanceof TYPE_ARRAY){
			TYPE lenType = this.len.SemantMe();

			if (this.len instanceof AST_INT && ((AST_INT)this.len).val < 0){
				System.out.println("Semantic error: array subscript is less than 0");
				System.exit(0);
			}

			if (!(lenType instanceof TYPE_INT)){
				System.out.println("Semantic error: array subscript must be of type int");
				System.exit(0);
			}
			
			return (TYPE_ARRAY)instanceType;
		}
		if (instanceType instanceof TYPE_NIL){
			return (TYPE_NIL)instanceType;
		}

		return null;
	}
}