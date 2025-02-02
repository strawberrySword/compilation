package IR;

import java.util.HashSet;

public abstract class IRcommand
{
	public HashSet<assignment> in;
    public HashSet<assignment> out = new HashSet<>();
	public int irNumber;

	public void computeAndAssignOutSetForEx4(){
		out = new HashSet<>();

		for (assignment a: in){
			out.add(a.copy());
		}

		HashSet<assignment> killSet = this.computeKillSet();
		HashSet<assignment> genSet= this.computeGenSet();

		out.removeAll(killSet);
		out.addAll(genSet);
	}

	// This computes kill set for this IRcommand object
	public HashSet<assignment> computeKillSet(){

		HashSet<assignment> res = new HashSet<>();

		if (this instanceof IRcommandConstInt cInt){
			res.add(new assignment(cInt.t.toString(), true, this.irNumber));
			res.add(new assignment(cInt.t.toString(), false, this.irNumber));
		}
		else if (this instanceof IRcommand_Allocate alloc){
			res.add(new assignment(alloc.var_name, true, this.irNumber));
			res.add(new assignment(alloc.var_name, false, this.irNumber));
		}
		else if (this instanceof IRcommand_Load load){
			res.add(new assignment(load.dst.toString(), true, this.irNumber));
			res.add(new assignment(load.dst.toString(), false, this.irNumber));
		}
		else if (this instanceof IRcommand_Store store){
			res.add(new assignment(store.var_name, true, this.irNumber));
			res.add(new assignment(store.var_name, false, this.irNumber));
		}
		else if (this instanceof IRcommand_Binop binop){
			res.add(new assignment(binop.dst.toString(), true, this.irNumber));
			res.add(new assignment(binop.dst.toString(), false, this.irNumber));
		}
		return res;
	}

	// This computes gen set for this IRcommand object
	public HashSet<assignment> computeGenSet(){
		
		HashSet<assignment> res = new HashSet<>();

		if (this instanceof IRcommandConstInt cInt){
			res.add(new assignment(cInt.t.toString(), true, this.irNumber));
		}
		else if (this instanceof IRcommand_Allocate alloc){
			res.add(new assignment(alloc.var_name, false, this.irNumber));
		}
		else if (this instanceof IRcommand_Load load){
			res.add(new assignment(load.dst.toString(), this.isInitialized(load.var_name), this.irNumber));
		}
		else if (this instanceof IRcommand_Store store){
			res.add(new assignment(store.var_name, this.isInitialized(store.src.toString()), this.irNumber));
		}
		else if (this instanceof IRcommand_Binop binop){
			boolean ans = this.isInitialized(binop.t1.toString()) && this.isInitialized(binop.t2.toString());
			res.add(new assignment(binop.dst.toString(), ans, this.irNumber)); // will be true iff both are initialized
		}
		return res;
	}

	// This function determines wether a variable (or temp) is initialized at IN
	public boolean isInitialized(String vName){
		boolean ans = false;
		for (assignment a : this.in){
			if (a.var.equals(vName)){
				if (a.isAssigned == false){
					return false;
				}
				ans = a.isAssigned;
			}
		}
		return ans;
	}

	public void initInSet(){
		this.in = new HashSet<>();
	}

	protected static int label_counter=0;
	public    static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}


	public String toString(){
		String res = "";

		if (this instanceof IRcommandConstInt c){
			res += "const. int: t"+c.t.toString()+" = "+c.value+"\n";
		}
		else if (this instanceof IRcommand_Allocate alloc){
			res += "allocate: "+alloc.var_name+"\n";
		}
		else if (this instanceof IRcommand_Load load){
			res += "load: t"+load.dst.toString()+" = "+load.var_name+"\n";
		}
		else if (this instanceof IRcommand_Store store){
			res += "store: "+store.var_name+" = t"+store.src.toString()+"\n";
		}
		else if (this instanceof IRcommand_Binop binop){
			res += "binop: t"+binop.dst.toString()+" = t"+binop.t1.toString()+"op t"+binop.t2.toString()+"\n";
		}
		else if (this instanceof IRcommand_Label label){
			res += "Label: "+label.label_name+"\n";
		}
		else if (this instanceof IRcommand_Branch br){
			res += "Branch: "+br.label_name+"\n";
		}

		res += "in: {";
		for (assignment a : this.in){
			res += "("+a.var+",";
			res += a.isAssigned+") , ";
		}
		res += "}\n";

		res += "out: {";
		for (assignment a : this.out){
			res += "("+a.var+",";
			res += a.isAssigned+") , ";
		}
		res += "}\n";


		return res;
		
	}
}
