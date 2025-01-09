package AST;
import TYPES.*;

public abstract class AST_STMT extends AST_Node {
    /* Abstract class just to allow for polymorphism */
    @Override
	public void PrintMe(){}
    public TYPE SemantMe(){return null;}
}