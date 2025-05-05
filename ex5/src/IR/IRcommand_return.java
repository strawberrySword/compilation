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

public class IRcommand_return extends IRcommand {
	TEMP retVal;

	public IRcommand_return(TEMP t) {
		this.retVal = t;
	}

	public void MIPSme() {
		TEMP retReg = new TEMP("v0");
		MIPSGenerator.getInstance().addi(retReg, retVal, 0);
	}
}
