package AST;
import TYPES.*;

public class AST_VAR_DEC extends AST_DEC /*TODO: determine inheritance*/ {
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	public AST_NEW_EXP newExp;

	public AST_VAR_DEC(AST_TYPE t, String n, AST_EXP e, AST_NEW_EXP ne) {
		this.type = t;
		this.name = n;
		this.exp = e;
		this.newExp = ne;
	}

	public void PrintMe(){
		System.out.format("VarDec(%s, %s)", this.type.myType, this.name);

		if (this.exp != null) this.exp.PrintMe();
		if (this.newExp != null) this.newExp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("VarDec(%s, %s)", this.type.myType, this.name));
	
		if (this.exp != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.exp.SerialNumber);
		if (this.newExp != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.newExp.SerialNumber);
	}
}