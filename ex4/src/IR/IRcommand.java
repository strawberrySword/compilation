package IR;

public abstract class IRcommand
{
	protected static int label_counter=0;
	public    static String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}
}
