package IR;

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
