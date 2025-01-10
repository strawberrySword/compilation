package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_BINOP_EXP extends AST_EXP {

	public AST_EXP left;
	public String op;
	public AST_EXP right;
	
	public AST_BINOP_EXP(AST_EXP l,String o,AST_EXP r, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.left = l;
		this.right = r;
		this.op = o;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("BinopExp(%s)", this.op);

		this.left.PrintMe();
		this.right.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("BinopExp(%s)", this.op));

		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.left.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.right.SerialNumber);
	}

    @Override
	public TYPE SemantMe(){
		TYPE rightType = this.right.SemantMe();
		TYPE leftType = this.left.SemantMe();

		if(this.op.equals("=")){
			boolean isLeftArray = leftType instanceof TYPE_ARRAY;
			boolean isRightArray = rightType instanceof TYPE_ARRAY;
			boolean isLeftClass = leftType instanceof TYPE_CLASS;
			boolean isRightClass = rightType instanceof TYPE_CLASS;
			boolean isLeftNil = leftType == TYPE_NIL.getInstance();
			boolean isRightNil = rightType == TYPE_NIL.getInstance();

			if(rightType.inheritsFrom(leftType) || leftType.inheritsFrom(rightType)){
				return TYPE_INT.getInstance();
			}
			if((isRightNil && (isLeftArray || isLeftClass))|| (isLeftNil && (isRightArray || isRightClass))){
				return TYPE_INT.getInstance();
			}
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> Error: cannot compare those vars\n");
			System.exit(0);
			return null;
		}
		if(rightType != leftType){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> ERROR type mismatch for binop\n");
			System.exit(0);
			return null;
		}
		if(!(rightType == TYPE_INT.getInstance()) && !((rightType == TYPE_STRING.getInstance()) && this.op.equals("+"))){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.format(">> ERROR type mismatch for binop\n");
				System.exit(0);
			return null;
		}
		if(this.op.equals("/") && this.right instanceof AST_INT ){
			if(((AST_INT)this.right).val == 0){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.format(">> ERROR division by zero\n");
				System.exit(0);
				return null;
			}
		}

		return rightType;
	}
}