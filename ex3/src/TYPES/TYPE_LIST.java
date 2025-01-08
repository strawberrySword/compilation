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
		if (this.head != other.head){ // type mismatch
			return false;
		}

		if (this.tail == null){
			if (other.tail == null){ // both lists end
				return true;
			}else{ // only this list ended
				return false;
			}
		}else if (other.tail == null){ // only other list ended
			return false;
		}

		return this.tail.equals(other.tail);
	}
}
