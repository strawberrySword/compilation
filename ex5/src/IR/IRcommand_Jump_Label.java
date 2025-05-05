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

public class IRcommand_Jump_Label extends IRcommand_Branch {
	public IRcommand_Jump_Label(String label_name) {
		this.label_name = label_name;
	}

	public void MIPSme() {
		MIPSGenerator.getInstance().jump(label_name);
	}
}
