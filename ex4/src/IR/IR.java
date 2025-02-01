package IR;

import java.util.ArrayList;
import java.util.HashSet;

public class IR
{
	public IRcommand head=null;
	public IRcommandList tail=null;
	public HashSet<String>allVars = new HashSet<>();


	public void Add_IRcommand(IRcommand cmd){
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
	}
	
	private static IR instance = null;

	protected IR() {}
	
	public static IR getInstance(){
		if (instance == null)
		{
			instance = new IR();
		}
		return instance;
	}


	public static void addVariables(){
		IR me = getInstance();

		IRcommand curr = getInstance().head;
		IRcommandList next = getInstance().tail;
		while(curr != null){
			if (curr instanceof IRcommand_Binop bin){
				me.allVars.add(bin.t1.toString());
				me.allVars.add(bin.t2.toString());
				me.allVars.add(bin.dst.toString());
			}
			else if (curr instanceof IRcommand_Allocate alloc){
				me.allVars.add(alloc.var_name);
			}
			else if (curr instanceof IRcommand_Load load){
				me.allVars.add(load.var_name);
				me.allVars.add(load.dst.toString());
			}
			else if (curr instanceof IRcommand_Store store){
				me.allVars.add(store.var_name);
				me.allVars.add(store.src.toString());
			}
			else if (curr instanceof IRcommandConstInt cInt){
				me.allVars.add(cInt.t.toString());
			}

			curr = next != null ? next.head : null;
			next = next != null ? next.tail : null;
		}
	}


	public Graph<IRcommand> CFGme(){
		Graph<IRcommand> cfg = new Graph<>();

		IRcommand curr = getInstance().head;
		IRcommandList next = getInstance().tail;
		while(curr != null){
			cfg.addNode(curr);

			if (curr instanceof IRcommand_Jump_Label irJump){
				cfg.addEdge(curr, findNodeByLabel(irJump.label_name));
				// Move to the next command
				curr = next != null ? next.head : null;
				next = next != null ? next.tail : null;
				continue; // here we don't want to add the edge to the next command
			}

			if (next != null){
				cfg.addEdge(curr,next.head);
			}

			if(curr instanceof IRcommand_Branch irBranch){
				cfg.addEdge(curr, findNodeByLabel(irBranch.label_name));
			}

			// Move to the next command
			curr = next != null ? next.head : null;
			next = next != null ? next.tail : null;
		}

		return cfg;
	}



	private static HashSet<assignment> computeInitialSetForEx4(){
		HashSet<assignment> res = new HashSet<>();

		for (String s : getInstance().allVars){
			res.add(new assignment(s, false));
		}

		return res;
	}

	public static void ChaoticIterate(Graph<IRcommand> cfg){
		for (IRcommand node : cfg.adjacencyList.keySet()) {
			node.initInSet();
    	}

		IRcommand first = cfg.getFirstCommand();
		IRcommand curr;

		first.in = computeInitialSetForEx4();

		ArrayList<IRcommand> workList = new ArrayList<>();
		ArrayList<IRcommand> next;

		HashSet<assignment> temp;

		workList.add(first);

		while (!(workList.isEmpty())){
			curr = workList.get(0); // take out the first command (doesn't matter which really)
			workList.remove(0); // remove the command we're working on

			curr.computeAndAssignOutSetForEx4(); // compute transfer function

			next = (ArrayList<IRcommand>)cfg.getNeighbors(curr); // the "next" in the cfg

			for (IRcommand n: next){
				temp = new HashSet<>();

				for (assignment a: n.in){
					temp.add(a.copy());
				}
				// now temp is n.in before addition

				n.in.addAll(curr.out); // in_{v} = in_{v} U out_{u}

				if (!(temp.equals(n.in))){
					workList.add(n);
				}
			}
		}
	}



	public static boolean CheckUsedBeforeAssigned(){

		IRcommand curr = getInstance().head;
		IRcommandList next = getInstance().tail;

		while(curr != null){
			if (curr instanceof IRcommand_Binop bin){
				if (!curr.isInitialized(bin.t1.toString())){
					return false;
				}
				if (!curr.isInitialized(bin.t2.toString())){
					return false;
				}
			}
			else if (curr instanceof IRcommand_Load load){
				if (!curr.isInitialized(load.var_name)){
					return false;
				}
			}
			else if (curr instanceof IRcommand_Store store){
				if (!curr.isInitialized(store.src.toString())){
					return false;
				}
			}

			curr = next != null ? next.head : null;
			next = next != null ? next.tail : null;
		}

		return true;
	}



	private IRcommand findNodeByLabel(String label){
		IRcommand curr = getInstance().head;
		IRcommandList next = getInstance().tail;
		while(curr != null)
		{
			if(curr instanceof IRcommand_Label irLabel && irLabel.label_name.equals(label))
			{
				return curr;
			}

			curr = next != null ? next.head : null;
			next = next != null ? next.tail : null;
		}

		return null;
	}
}
