package IR;

import MIPS.MIPSGenerator;
import TEMP.*;

public class IRcommand_Binop_Add_Integers extends IRcommand_Binop {
	public IRcommand_Binop_Add_Integers(TEMP dst, TEMP t1, TEMP t2) {
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().add(dst, t1, t2);

		IRcommand_Binop.constraintValue();
	}
}
