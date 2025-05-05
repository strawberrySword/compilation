package IR;

import MIPS.MIPSGenerator;
import TEMP.*;

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand_Branch
{
	TEMP t;
	
	public IRcommand_Jump_If_Eq_To_Zero(TEMP t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().beqz(t, label_name);
	}
}
