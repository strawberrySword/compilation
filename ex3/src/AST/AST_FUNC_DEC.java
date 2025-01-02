package AST;

public class AST_FUNC_DEC extends AST_DEC {

	public AST_TYPE retType;
	public String fName;
	public AST_ARG_LIST argList;
	public AST_STMT_LIST body;

	public AST_FUNC_DEC(AST_TYPE t, String n, AST_STMT_LIST b) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.retType = t;
		this.fName = n;
		this.body = b;
		this.argList = null;
	}
	public AST_FUNC_DEC(AST_TYPE t, String n, AST_TYPE t1, String a1, AST_ARG_LIST args, AST_STMT_LIST b) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.retType = t;
		this.fName = n;
		this.body = b;
		this.argList = new AST_ARG_LIST(t1,a1,args);		
	}

	public void PrintMe(){
		System.out.format("FuncDec(%s, %s)", this.retType.myType, this.fName);

		if(this.argList != null) this.argList.PrintMe();
		if(this.body != null) this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("FuncDec(%s, %s)", this.retType.myType, this.fName));
		
		if(this.argList != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.argList.SerialNumber);
		if(this.body != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}
}