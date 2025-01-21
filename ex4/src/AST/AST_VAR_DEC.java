package AST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_VAR_DEC extends AST_DEC {
	public AST_TYPE type;
	public String name;
	public AST_EXP exp;
	public AST_NEW_EXP newExp;

	public AST_VAR_DEC(AST_TYPE t, String n, AST_EXP e, AST_NEW_EXP ne, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();
		
		this.type = t;
		this.name = n;
		this.exp = e;
		this.newExp = ne;
		this.lineNum = line;
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
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: variable cannot be of type void");
			System.exit(0);
		}

		if (sTable.findInScope(this.name) != null){ // a variable with this name exists
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: variable name is taken");
			System.exit(0);
		}

		if (sTable.find(this.type.myType) == null){ // type does not exist
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: type "+this.type.myType+" does not exist");
			System.exit(0);
		}

		if (sTable.inClassDef()){
			// assignment in class must be constant
			if (this.newExp != null || (this.exp != null && !(this.exp instanceof AST_INT) && !(this.exp instanceof AST_STRING) && !(this.exp instanceof AST_NIL))){
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: variable declaration inside class must be assigned a constant expression");
				System.exit(0);
			}

			// shadowing is handled in the second semantme()
		}

		// type of assignment must match declaration type 
		TYPE tLeft = sTable.find(this.type.myType);
		if(tLeft == null){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: type does not exist");
			System.exit(0);
		}
		// check that the given type is an actual type and not an instance
		if (!(tLeft.name.equals(this.type.myType))){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: type does not exist");
			System.exit(0);
		}
		
		TYPE tRight;
		if (this.newExp == null){
			if (this.exp == null){ // both null "int x;"
				sTable.enter(this.name, tLeft);
				return new TYPE_VAR_DEC(tLeft, this.name);
			}

			tRight = this.exp.SemantMe(); // "int x := 32 + 98;"
		}else{ // new assign
			tRight = this.newExp.SemantMe(); // "Car x := new Car;"
		}

		if((tLeft instanceof TYPE_ARRAY || tLeft instanceof TYPE_CLASS) && tRight == TYPE_NIL.getInstance()){
			sTable.enter(this.name, tLeft);
			return new TYPE_VAR_DEC(tLeft, this.name);
		}

		if (tLeft instanceof TYPE_ARRAY lArr && tRight instanceof TYPE_ARRAY rArr){
			if (rArr.name.equals(rArr.dataType.name) && lArr.dataType.name.equals(rArr.dataType.name)){ // arr1 := new int[8];
				sTable.enter(this.name, tLeft);
				return new TYPE_VAR_DEC(tLeft, this.name);
			}
			if (!(tLeft.name.equals(tRight.name))){ // two arrays must be of exactly the same type
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: assignment type mismatch");
				System.exit(0);
			}
			
			sTable.enter(this.name, tLeft);
			return new TYPE_VAR_DEC(tLeft, this.name);
		}

		if (!(tRight.inheritsFrom(tLeft))){ // Check inheritance and type mismatch
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: assignment type mismatch");
			System.exit(0);
		}

		sTable.enter(this.name, tLeft);
		return new TYPE_VAR_DEC(tLeft, this.name);
	}

	// called when declaring a variable inside a function, checks shadowing
	public TYPE SemantMe(TYPE_CLASS parent){
		if (parent != null && parent.findField(this.name) != null){
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: shadowing is not allowed");
			System.exit(0);
		}

		return this.SemantMe();
	}


	public TEMP IRme(){
		IR.getInstance().Add_IRcommand(new IRcommand_Allocate(name));
		
		if (this.exp != null){ // no need to add handling for new command since we are in targil 4 (oh no)
			IR.getInstance().Add_IRcommand(new IRcommand_Store(name,this.exp.IRme()));
		}
		return null;
	}
}