package AST;
import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_INT extends AST_EXP {

	public int val;

	public AST_INT(int i, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.val = i;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Int(%d)", this.val);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Int(%s)", this.val));
	}

	@Override
	public TYPE SemantMe(){
		return TYPE_INT.getInstance();
	}

    @Override
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t, val));
		return t;
	}
}