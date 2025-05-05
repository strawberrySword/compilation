/***********/
/* PACKAGE */
/***********/
package IR;

import TEMP.TEMP;

public class IRcommandList {
	public IRcommand head;
	public IRcommandList tail;

	IRcommandList(IRcommand head, IRcommandList tail) {
		this.tail = tail;
		this.head = head;
	}

	public void MIPSme() {
		head.MIPSme();

		if (tail != null)
			tail.MIPSme();
	}
}
