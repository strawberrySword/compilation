package IR;
import TEMP.*;

public class IRcommand_Binop_Div_Integers extends IRcommand_Binop {
	
	public IRcommand_Binop_Div_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
}
