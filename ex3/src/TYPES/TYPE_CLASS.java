package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	public TYPE findField(String fieldName){
		TYPE_LIST t = this.data_members;
		if (t == null){
			return null;
		}

		while (t != null){
			if (t.head == null && this.father != null){
				return this.father.findField(fieldName);	
			}
			if (t.head.name.equals(fieldName)){
				return t.head;
			}
			t = t.tail;
		}
		if (this.father != null){
			return this.father.findField(fieldName);
		}
		return null;
	}
}
