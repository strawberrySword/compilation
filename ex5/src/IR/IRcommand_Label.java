package IR;

import MIPS.MIPSGenerator;

public class IRcommand_Label extends IRcommand {
	String label_name;

	public IRcommand_Label(String label_name) {
		this.label_name = label_name;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().label(label_name);
	}
}