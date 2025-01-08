package TYPES;

import SYMBOL_TABLE.SYMBOL_TABLE;

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

		if( (this instanceof TYPE_CLASS && !(other instanceof TYPE_CLASS)) || !(this instanceof TYPE_CLASS) && other instanceof TYPE_CLASS){ // one class one primitive
			return false;
		}

		if (this.name == other.name){ // two identical types
			return true;
		}

		return ((TYPE_CLASS)this).father.inheritsFrom(other);
	}
}
