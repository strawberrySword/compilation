package IR;

import java.util.HashSet;

public abstract class IRcommand
{
	public HashSet<assignment> in;
    public HashSet<assignment> out;

    public class assignment {
        String var;
        boolean isAssigned;

        public assignment(String v, boolean b) {
            this.var = v;
            this.isAssigned = b;
        }

		public assignment copy(){
			return new assignment(this.var, this.isAssigned);
		}
    }

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
			res.add(new assignment(cInt.t.toString(), true));
			res.add(new assignment(cInt.t.toString(), false));
		}
		else if (this instanceof IRcommand_Allocate alloc){
			res.add(new assignment(alloc.var_name, true));
			res.add(new assignment(alloc.var_name, false));
		}
		else if (this instanceof IRcommand_Load load){
			res.add(new assignment(load.dst.toString(), true));
			res.add(new assignment(load.dst.toString(), false));
		}
		else if (this instanceof IRcommand_Store store){
			res.add(new assignment(store.var_name, true));
			res.add(new assignment(store.var_name, false));
		}
		else if (this instanceof IRcommand_Binop binop){
			res.add(new assignment(binop.dst.toString(), true));
			res.add(new assignment(binop.dst.toString(), false));
		}
		return res;
	}

	// This computes gen set for this IRcommand object
	public HashSet<assignment> computeGenSet(){
		
		HashSet<assignment> res = new HashSet<>();

		if (this instanceof IRcommandConstInt cInt){
			res.add(new assignment(cInt.t.toString(), true));
		}
		else if (this instanceof IRcommand_Allocate alloc){
			res.add(new assignment(alloc.var_name, false));
		}
		else if (this instanceof IRcommand_Load load){
			res.add(new assignment(load.dst.toString(), this.isInitialized(load.var_name)));
		}
		else if (this instanceof IRcommand_Store store){
			res.add(new assignment(store.var_name, this.isInitialized(store.src.toString())));
		}
		else if (this instanceof IRcommand_Binop binop){
			boolean ans = this.isInitialized(binop.t1.toString()) && this.isInitialized(binop.t2.toString());
			res.add(new assignment(binop.dst.toString(), ans)); // will be true iff both are initialized
		}
		return res;
	}

	// This function determines wether a variable (or temp) is initialized at IN
	private boolean isInitialized(String vName){
		for (assignment a : this.in){
			if (a.var.equals(vName)){
				return a.isAssigned;
			}
		}

		return false;
	}

	public void initInSet(){
		this.in = new HashSet<>();
	}

	protected static int label_counter=0;
	public    static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}
}
