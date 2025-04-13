package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.*;
import IR.*;

public class AST_STMT_WHILE extends AST_STMT {
	AST_EXP cond;
	AST_STMT_LIST body;

	public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.cond = cond;
		this.body = body;
		this.lineNum = line;
	}

	@Override
	public void PrintMe(){
		System.out.format("Stmt_while");

		this.cond.PrintMe();
		this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("Stmt_while"));
	
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		SYMBOL_TABLE.getInstance().beginScope("Loop");
		
		TYPE t = cond.SemantMe();
		if (t != TYPE_INT.getInstance()){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.format(">> condition inside while statement is not of type int\n");
			System.exit(0);
			return null;
		}
		
		body.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}


	public TEMP IRme(){
		IR ir = IR.getInstance();

		String label_end   = IRcommand.getFreshLabel("end");
		String label_start = IRcommand.getFreshLabel("start");
	
		ir.Add_IRcommand(new IRcommand_Label(label_start)); // loop:

		TEMP cond_temp = cond.IRme();

		ir.Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end)); // beq cond,0,end

		body.IRme();

		ir.Add_IRcommand(new IRcommand_Jump_Label(label_start)); // jump to loop start

		ir.Add_IRcommand(new IRcommand_Label(label_end)); // end label

		return null;
	}
}