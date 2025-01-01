package AST;
import TYPES.*;

public class AST_STRING extends AST_EXP {

	public String val;

	public AST_STRING(String s) {
		this.val = s;
	}
}