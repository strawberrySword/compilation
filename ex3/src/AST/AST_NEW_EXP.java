package AST;

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
}