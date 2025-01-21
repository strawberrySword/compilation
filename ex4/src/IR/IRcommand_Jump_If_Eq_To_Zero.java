package IR;

import TEMP.*;

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand_Branch
{
	TEMP t;
	String label_name;
	
	public IRcommand_Jump_If_Eq_To_Zero(TEMP t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}
}
