package TYPES;

public class TYPE_FOR_SCOPE_BOUNDARIES extends TYPE
{
	public TYPE boundType;
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FOR_SCOPE_BOUNDARIES(String name)
	{
		this.name = name;
	}

	public TYPE_FOR_SCOPE_BOUNDARIES(String name, TYPE boundType)
	{
		this.name = name;
		this.boundType = boundType;
	}
}
