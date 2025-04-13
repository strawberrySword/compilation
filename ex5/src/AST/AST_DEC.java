package AST;
import TYPES.*;
import TEMP.*;

public abstract class AST_DEC extends AST_STMT {
    /* Abstract class just to allow for polymorphism */

    @Override
	public void PrintMe(){}
    @Override
    public TYPE SemantMe(){return null;}
    public TEMP IRme(){return null;}
}