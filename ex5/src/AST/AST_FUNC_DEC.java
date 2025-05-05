package AST;

import java.util.HashMap;

import IR.IR;
import IR.IRcommand;
import IR.IRcommand_Label;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.*;

public class AST_FUNC_DEC extends AST_DEC {

	public AST_TYPE retType;
	public String fName;
	public AST_ARG_LIST argList;
	public AST_STMT_LIST body;
	public TYPE_CLASS myDad;
	public String unique_label;

	public HashMap<String, String> offSets;

	public AST_FUNC_DEC(AST_TYPE t, String n, AST_STMT_LIST b, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.retType = t;
		this.fName = n;
		this.body = b;
		this.argList = null;
		this.lineNum = line;
	}

	public AST_FUNC_DEC(AST_TYPE t, String n, AST_TYPE t1, String a1, AST_ARG_LIST args, AST_STMT_LIST b, int line) {
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

		this.retType = t;
		this.fName = n;
		this.body = b;
		this.argList = new AST_ARG_LIST(t1, a1, args, line);
		this.lineNum = line;
	}

	@Override
	public void PrintMe() {
		System.out.format("FuncDec(%s, %s)", this.retType.myType, this.fName);

		if (this.argList != null)
			this.argList.PrintMe();
		if (this.body != null)
			this.body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(this.SerialNumber,
				String.format("FuncDec(%s, %s)", this.retType.myType, this.fName));

		if (this.argList != null)
			AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.argList.SerialNumber);
		if (this.body != null)
			AST_GRAPHVIZ.getInstance().logEdge(this.SerialNumber, this.body.SerialNumber);
	}

	// called when a function is declared in global scope
	@Override
	public TYPE SemantMe() {
		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE returnType = t.find(this.retType.myType);
		if (returnType == null) {
			if (!this.retType.myType.equals("void")) {
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: return type not found");
				System.exit(0);
			}
			returnType = TYPE_VOID.getInstance();
		}

		TYPE prevDef = t.findInScope(fName);

		if (prevDef != null) {
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: can't declare function with taken name");
			System.exit(0);
		}

		t.beginScope("Function", returnType);
		TYPE_LIST argTypes = null;
		if (this.argList != null) {
			argTypes = this.argList.SemantMe();
		}
		TYPE_FUNCTION res = new TYPE_FUNCTION(returnType, fName, argTypes);
		res.unique_label = "whatever keiloo ma shtirze";
		t.enter(fName, res); // for recursion
		body.SemantMe();

		t.endScope();
		t.enter(fName, res); // for use of function

		this.myDad = null;
		return res;
	}

	// called when method is declared within class
	public TYPE SemantMe(TYPE_CLASS papa) {
		this.myDad = papa;

		SYMBOL_TABLE t = SYMBOL_TABLE.getInstance();

		TYPE prevDef = t.findInScope(fName);
		if (prevDef != null) { // this is double definition inside same scope (class or function)
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantci error: double definition for " + fName);
			System.exit(0);
		}

		TYPE returnType = t.find(this.retType.myType);
		if (returnType == null) {
			if (!this.retType.myType.equals("void")) {
				SYMBOL_TABLE.getInstance().writeError(lineNum);
				System.out.println("Semantic error: return type not found");
				System.exit(0);
			}
			returnType = TYPE_VOID.getInstance();
		}

		t.beginScope("Function", returnType);

		TYPE_LIST argTypes = null;
		if (this.argList != null) {
			argTypes = this.argList.SemantMe();
		}

		if (!(checkOverride(returnType, fName, argTypes, this.myDad))) {
			SYMBOL_TABLE.getInstance().writeError(lineNum);
			System.out.println("Semantic error: overloading is not allowed");
			System.exit(0);
		}

		TYPE_FUNCTION res = new TYPE_FUNCTION(returnType, fName, argTypes);
		t.enter(fName, res); // for recursion
		body.SemantMe();

		t.endScope();
		t.enter(fName, res); // for use of function

		return res;
	}

	// this function assumes no other method of same name in current scope (checked
	// in SemantMe)
	// true is O.K (no other fields with same name in inheritence, except
	// overrides), false is bad
	private boolean checkOverride(TYPE ret, String name, TYPE_LIST args, TYPE_CLASS parent) {
		if (parent == null) { // function is declared in this class and there is no hierarchy
			return true;
		}

		TYPE otherFunc = parent.findField(name); // searches in hierarchy
		if (otherFunc != null && !(otherFunc instanceof TYPE_FUNCTION)) { // of course an error - overriding a variable
																			// with a function
			return false;
		}

		// otherFunc exists in hierarchy
		if (otherFunc != null) { // overriding is O.K
			boolean sameRetType = (ret == ((TYPE_FUNCTION) otherFunc).returnType);
			boolean sameName = name.equals(((TYPE_FUNCTION) otherFunc).name);

			if (sameRetType && sameName) {
				if (args == null) {
					return (((TYPE_FUNCTION) otherFunc).params == null);
				}
				return args.equals(((TYPE_FUNCTION) otherFunc).params);
			} else {
				return false;
			}
		}
		return true; // this is the first declaration of this function

	}

	public TEMP IRme() {
		// IR.getInstance().Add_IRcommand(new IRcommand_Label(fName)); // changed from
		// skeleton's "main"
		// if (body != null)
		// body.IRme();
		// IR.getInstance().Add_IRcommand(new IRcommand_Label("END_" + fName));
		// return null;

		if (fName.equals("main")) {
			this.fName = "user_main";
		}

		int argCnt = 0; // number of arguments
		if (myDad.name != null)
			argCnt += 1;

		for (AST_ARG_LIST it = argList; it != null; it = it.next) {
			String Foff = String.valueOf(8 + 4 * argCnt);
			offSets.put(it.name, Foff);
			argCnt += 1;
		}

		/* local vars */
		int varCnt = 0;
		for (AST_STMT_LIST it = body; it != null; it = it.next) {
			if (it.value instanceof AST_VAR_DEC) {
				varCnt += 1;
				continue;
			}
			if (it.value instanceof AST_STMT_IF || it.value instanceof AST_STMT_WHILE) {
				varCnt += countLocalVarsInScope(it.value);
			}
		}

		String labelStart;
		if (fName.equals("user_main")) {
			labelStart = fName;
		} else {
			if (myDad.name != null)
				labelStart = myDad.name + "_" + fName;
			else {
				labelStart = IRcommand.getFreshLabel("start_" + fName);
				offSets.put(fName, labelStart);
			}
		}
		// HERE!!! TODO!!! we found out that we dont have the func! it is only in the symbol table rip
		// this.func.startLabel = labelStart; fugging clown

		IR.getInstance().Add_IRcommand(new IRcommand_Label(labelStart));
		IR.getInstance().Add_IRcommand(new IRcommand_Prologue(varCnt));

		// body
		varCnt = 0;
		for (AST_STMT_LIST it = list; it != null; it = it.tail) {
			if (it.head instanceof AST_STMT_VARDEC) {
				varCnt += 1;
				AST_STMT_VARDEC a = (AST_STMT_VARDEC) (it.head);
				AST_VARDEC b = a.v;
				String off = String.valueOf(varCnt * (-4) + -40);
				offsets.put(b.id, off);
			}
			if (it.head instanceof AST_STMT_IF || it.head instanceof AST_STMT_WHILE) {
				varsInFunc = varCnt;
				varCnt += countLocalVarsInScope(it.head);
			}
			it.head.IRme();
		}

		IR.getInstance().Add_IRcommand(new IRcommand_Epilogue());

		System.out.println(offsets);
		return null;
	}
}