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

public class IRcommand_Jump_Label extends IRcommand_Branch
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
}
