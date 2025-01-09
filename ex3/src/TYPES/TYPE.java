package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}

	public boolean inheritsFrom(TYPE other){
		if(this == other) return true; // two primitives

		if (this instanceof TYPE_CLASS && !(other instanceof TYPE_CLASS)) return false;
		if (!(this instanceof TYPE_CLASS) && other instanceof TYPE_CLASS) return false;

		if (this instanceof TYPE_ARRAY && !(other instanceof TYPE_ARRAY)) return false;
		if (!(this instanceof TYPE_ARRAY) && other instanceof TYPE_ARRAY) return false;
		
		
		if (this instanceof TYPE_CLASS thisClass){ // two class types
			if (this.name.equals(other.name)) return true;

			if (thisClass.father == null) return false;

			return thisClass.father.inheritsFrom(other);
		}

		if (this instanceof TYPE_ARRAY thisArray){ // two array typs
			return thisArray.dataType.name.equals(((TYPE_ARRAY)other).dataType.name);
		}


		return false;
	}
}
