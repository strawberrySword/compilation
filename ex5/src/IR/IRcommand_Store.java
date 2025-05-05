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

public class IRcommand_Store extends IRcommand {
	String var_name;
	TEMP src;

	public IRcommand_Store(String var_name, TEMP src) {
		this.src = src;
		this.var_name = var_name;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().store(var_name, src);
	}
}
