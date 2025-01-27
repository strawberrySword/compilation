package AST;
import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.*;
import TYPES.*;

public class AST_VAR_SIMPLE extends AST_VAR {

	public String name;

	public AST_VAR_SIMPLE(String n, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.name = n;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("var_simple(%s)", this.name);

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Var_simple(%s)", this.name));
	}

	@Override
	public TYPE SemantMe(){
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		TYPE_CLASS thisClass = null;

		TYPE t = table.findInScope(this.name);

		if (t == null && table.inClassDef()){ // Prioritize scope over class
			thisClass = (TYPE_CLASS)table.WhichClassAmIIn();
			t = thisClass.findField(this.name);
		}

		if (t == null){ // Prioritize class over general search
			t = table.find(this.name);
		}

		if(t == null){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: variable not found");
			System.exit(0);
			return null;
		}

		if (t instanceof TYPE_VAR_DEC type_var_dec){
			t = type_var_dec.t;
		}
		return t;
	}

	@Override
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load(t,name));
		return t;
	}
}