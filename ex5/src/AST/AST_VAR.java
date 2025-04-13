package AST;
import TYPES.*;

public abstract class AST_VAR extends AST_EXP{

	/* Abstract class to allow for polymorphism */

	@Override
	public void PrintMe(){}

	@Override
	public TYPE SemantMe(){return null;}
}