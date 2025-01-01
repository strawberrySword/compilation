package AST;
import TYPES.*;

public class AST_FUNC_DEC extends AST_DEC {

	public AST_TYPE retType;
	public String fName;
	public AST_ARG_LIST argList;
	public STMT_LIST body;

	public AST_FUNC_DEC(AST_TYPE t, String n, STMT_LIST b) {
		this.retType = t;
		this.fName = n;
		this.body = b;
	}
	public AST_FUNC_DEC(AST_TYPE t, String n, AST_TYPE t1, String a1, AST_ARG_LIST args, STMT_LIST b) {
		this.retType = t;
		this.fName = n;
		this.body = b;
		this.argList = new AST_ARG_LIST(t1,a1,args);		
	}
}