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

public class IRcommandConstInt extends IRcommand {
	TEMP t;
	int value;

	public IRcommandConstInt(TEMP t, int value) {
		this.t = t;
		this.value = value;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().li(t, value);
	}
}
