package AST;
import TYPES.*;

public class AST_STMT_RETURN extends AST_STMT {
	AST_EXP retVal;

	public AST_STMT_RETURN(AST_EXP retVal) {
		this.retVal = retVal;
	}
}