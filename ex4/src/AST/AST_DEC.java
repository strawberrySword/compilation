package AST;

import TYPES.*;

public abstract class AST_DEC extends AST_STMT {
    /* Abstract class just to allow for polymorphism */

    @Override
	public void PrintMe(){}
    @Override
    public TYPE SemantMe(){return null;}
}