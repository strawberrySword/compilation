package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public abstract class IRcommand_Binop extends IRcommand {
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public void constraintMax() {
		String label = IRcommand.getFreshLabel("end");
		TEMP t = new TEMP("s0");

		MIPSGenerator.getInstance().li(t, IRcommand.MAX_INT + 1);
		MIPSGenerator.getInstance().blt(dst, t, label);
		MIPSGenerator.getInstance().li(dst, IRcommand.MAX_INT);
		MIPSGenerator.getInstance().label(label);
	}

	public void constraintMin() {
		String label_end = IRcommand.getFreshLabel("end");
		TEMP t = new TEMP("s0");

		MIPSGenerator.getInstance().li(t, IRcommand.MIN_INT);
		MIPSGenerator.getInstance().bge(dst, t, label_end);
		MIPSGenerator.getInstance().li(dst, IRcommand.MIN_INT);
		MIPSGenerator.getInstance().label(label_end);
	}

	public void constraintValue() {
		constraintMax();
		constraintMin();
	}
}
