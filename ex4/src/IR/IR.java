package IR;

import java.util.ArrayList;
import java.util.HashSet;

import IR.IRcommand.assignment;

public class IR
{
	private IRcommand head=null;
	private IRcommandList tail=null;

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

	public void AnalyzeCFG(Graph<IRcommand> cfg, HashSet<assignment> initialValue){
		for (IRcommand node : cfg.adjacencyList.keySet()) {
			node.initInSet();
    	}

		IRcommand first = cfg.getFirstCommand();
		IRcommand curr;

		first.in = initialValue;

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
				temp = n.in;

				n.in.addAll(curr.out); // in_{v} = in_{v} U out_{u}

				if (!(temp.equals(n.in))){
					workList.add(n);
				}
			}
		}
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
