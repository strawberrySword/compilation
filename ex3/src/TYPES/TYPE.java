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
		if (!(this instanceof TYPE_CLASS) && !(other instanceof TYPE_CLASS)){ // two primitives
			return this.name.equals(other.name);
		}
		if (this instanceof TYPE_NIL && other instanceof TYPE_CLASS){
			return true;
		}
		if (this instanceof TYPE_NIL && other instanceof TYPE_ARRAY){
			return true;
		}
		if( (this instanceof TYPE_CLASS && !(other instanceof TYPE_CLASS)) || !(this instanceof TYPE_CLASS) && other instanceof TYPE_CLASS){ // one class one primitive
			return false;
		}

		// Two class types
		if (this.name == other.name){ // two identical types
			return true;
		}
		if (((TYPE_CLASS)this).father == null){ // types not equal and this doesn't inherit from anything
			return false;
		}

		return ((TYPE_CLASS)this).father.inheritsFrom(other); // recursive step up
	}
}
