package AST;

import TYPES.*;

public abstract class AST_DEC extends AST_STMT {
    /* Abstract class just to allow for polymorphism */


	public void PrintMe(){}
    public TYPE SemantMe(){return null;}
}