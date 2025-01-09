package TYPES;

public class TYPE_LIST extends TYPE
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public boolean equals(TYPE_LIST other){
		if (this.head != other.head)// type mismatch
			return false;

		if (this.tail == null)
			return other.tail == null; // both lists end
		if (other.tail == null) // only other list ended
			return false;

		return this.tail.equals(other.tail);
	}

	public boolean matches(TYPE_LIST other){
		if (!(this.head.inheritsFrom(other.head))) // type mismatch
			return false;

		if (this.tail == null)
            return other.tail == null;
		if (other.tail == null) // only other list ended
			return false;

		return this.tail.equals(other.tail);
	}
}
