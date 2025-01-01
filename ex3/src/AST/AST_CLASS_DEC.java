package AST;
import TYPES.*;

public class AST_CLASS_DEC extends AST_DEC {

	public String cName;
	public String parentName;
	public AST_CLASS_FIELD_LIST fields;

	public AST_CLASS_DEC(String myName, String pName, AST_CLASS_FIELD_LIST f) {
		this.cName = myName;
		this.parentName = pName;
		this.fields = f;
	}
}