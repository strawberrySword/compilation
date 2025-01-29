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

	// TODO: COMPUTE KILL & GEN SETS!
	public HashSet<assignment> computeKillSet(){return null;}
	public HashSet<assignment> computeGenSet(){return null;}

	public void initInSet(){
		this.in = new HashSet<>();
	}

	protected static int label_counter=0;
	public    static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}
}
