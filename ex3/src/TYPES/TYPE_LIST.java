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
		boolean isOtherArray = other.head instanceof TYPE_ARRAY;
		boolean isOtherClass = other.head instanceof TYPE_CLASS;
		boolean isThisNil = this.head == TYPE_NIL.getInstance();
		boolean isThisArray = this.head instanceof TYPE_ARRAY;

		if (isThisArray && isOtherArray && !(this.head.name.equals(other.head.name))){
			return false;
		}

		if (!(this.head.inheritsFrom(other.head) || (isThisNil && (isOtherArray || isOtherClass)))) // type mismatch
			return false;

		if (this.tail == null)
            return other.tail == null;
		if (other.tail == null) // only other list ended
			return false;

		return this.tail.matches(other.tail);
	}
}
