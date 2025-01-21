/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_return extends IRcommand{
	TEMP retVal;
	
	public IRcommand_return(TEMP t)
	{
		this.retVal = t;
	}
}
