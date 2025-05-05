package AST;

public abstract class AST_Node {
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating */
	/* a graphviz dot format of the AST ... */
	/*******************************************/
	public int SerialNumber;
	public int lineNum;

	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe() {
		System.out.print("AST NODE UNKNOWN\n");
	}

	public int countLocalVarsInScope(AST_STMT scope) {
		AST_STMT_LIST body;
		if (scope instanceof AST_STMT_IF)
			body = ((AST_STMT_IF) scope).body;
		else
			body = ((AST_STMT_WHILE) scope).body;
		int result = 0;
		for (AST_STMT_LIST it = body; it != null; it = it.next) {
			if (it.value instanceof AST_VAR_DEC)
				result += 1;

			if (it.value instanceof AST_STMT_IF || it.value instanceof AST_STMT_WHILE) {
				result += countLocalVarsInScope(it.value);
			}
		}
		return result;
	}
}
