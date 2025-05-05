/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.MIPSGenerator;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_PrintInt extends IRcommand {
	TEMP t;

	public IRcommand_PrintInt(TEMP t) {
		this.t = t;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().print_int(t);
	}
}
