package AST;

import TYPES.*;

public class AST_BINOP_EXP extends AST_EXP {

	public AST_EXP left;
	public String op;
	public AST_EXP right;

	public AST_BINOP_EXP(AST_EXP l,String o,AST_EXP r) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.left = l;
		this.right = r;
		this.op = o;
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
		if(!(rightType == leftType)){
			// TODO report error and stop program!!
			System.exit(0);
			return null;
		}
		if(!(rightType == TYPE_INT.getInstance()) && !((rightType == TYPE_STRING.getInstance()) && this.op.equals("+"))){
			// TODO report error and stop program!!
				System.exit(0);
			return null;
		}
		if(this.op.equals("/") && this.right instanceof AST_INT ){
			if(((AST_INT)this.right).val == 0){
				// TODO report error and stop program!!
				System.exit(0);
				return null;
			}
		}

		return rightType;
	}
}