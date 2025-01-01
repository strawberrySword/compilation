package AST;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT {
	AST_VAR var;
	AST_EXP exp;

	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp) {
		this.var = var;
		this.exp = exp;
	}
}