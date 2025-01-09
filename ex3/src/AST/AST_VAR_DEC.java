package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_VAR_DEC extends AST_DEC {
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	public AST_NEW_EXP newExp;

	public AST_VAR_DEC(AST_TYPE t, String n, AST_EXP e, AST_NEW_EXP ne) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.type = t;
		this.name = n;
		this.exp = e;
		this.newExp = ne;
	}

	@Override
	public void PrintMe(){
		System.out.format("VarDec(%s, %s)", this.type.myType, this.name);

		if (this.exp != null) this.exp.PrintMe();
		if (this.newExp != null) this.newExp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber, String.format("VarDec(%s, %s)", this.type.myType, this.name));
	
		if (this.exp != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.exp.SerialNumber);
		if (this.newExp != null) AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.newExp.SerialNumber);
	}

	@Override
	public TYPE SemantMe(){
		// type != void
		// Name not taken
		// type exists
		// type matches with assignment (exp or newExp) (Check inheritance)
		// if inside class, must be a constant exp!!!! (of type AST_INT/AST_STRING/AST_NIL)
		
		SYMBOL_TABLE sTable = SYMBOL_TABLE.getInstance();
		

		if (this.type.myType.equals("void")){ // variable declaration of type void
			System.out.println("Semantic error: variable cannot be of type void");
			System.exit(0);
		}

		if (sTable.findInScope(this.name) != null){ // a variable with this name exists
			System.out.println("Semantic error: variable name is taken");
			System.exit(0);
		}

		if (sTable.find(this.type.myType) == null){ // type does not exist
			System.out.println("Semantic error: type "+this.type.myType+" does not exist");
			System.exit(0);
		}

		if ((sTable.inClassDef() && this.newExp != null) || (sTable.inClassDef() && this.exp != null && !(this.exp instanceof AST_INT) && !(this.exp instanceof AST_STRING) && !(this.exp instanceof AST_NIL))){ // variables in class definition must be assigned a constant expression
			System.out.println("Semantic error: variable declaration inside class must be assigned a constant expression");
			System.exit(0);
		}

		// type of assignment must match declaration type 
		TYPE tLeft = sTable.find(this.type.myType);
		if(tLeft == null){
			System.out.println("Semantic error: type does not exist");
			System.exit(0);
		}
		// check that the given type is an actual type and not an instance
		if (!(tLeft.name.equals(this.type.myType))){
			System.out.println("Semantic error: type does not exist");
			System.exit(0);
		}
		
		TYPE tRight;
		if (this.newExp == null){
			if (this.exp == null){ // both null "int x;"
				sTable.enter(this.name, tLeft);
				return tLeft;
			}

			tRight = this.exp.SemantMe(); // "int x := 32 + 98;"
		}else{ // new assign
			tRight = this.newExp.SemantMe(); // "Car x := new Car;"
		}

		if (!(tRight.inheritsFrom(tLeft))){ // Check inheritance and type mismatch
			System.out.println("Semantic error: assignment type mismatch");
			System.exit(0);
		}

		sTable.enter(this.name, tLeft);
		return new TYPE_VAR_DEC(tLeft, this.name);
	}
}