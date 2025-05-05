package IR;

import MIPS.MIPSGenerator;
import TEMP.*;

public class IRcommand_Binop_Div_Integers extends IRcommand_Binop {

	public IRcommand_Binop_Div_Integers(TEMP dst, TEMP t1, TEMP t2) {
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}

	public void MIPSme() {
		String label = IRcommand.getFreshLabel("div_by_zero_err");

		MIPSGenerator.getInstance().beqz(t2, MIPSGenerator.ERROR_DIVIDE_BY_ZERO);

		MIPSGenerator.getInstance().div(dst, t1, t2);

		IRcommand_Binop.constraintValue();
	}
}
