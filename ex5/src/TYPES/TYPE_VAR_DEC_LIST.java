package TYPES;

public class TYPE_VAR_DEC_LIST
{
	public TYPE_VAR_DEC head;
	public TYPE_VAR_DEC_LIST tail;
	
	public TYPE_VAR_DEC_LIST(TYPE_VAR_DEC head,TYPE_VAR_DEC_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}	
}
