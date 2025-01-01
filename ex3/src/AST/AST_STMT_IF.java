package AST;

public class AST_STMT_IF extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body ) {
		this.cond = cond;
		this.body = body;	
	}
}