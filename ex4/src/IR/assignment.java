package IR;


public class assignment {
    String var;
    boolean isAssigned;

    public assignment(String v, boolean b) {
        this.var = v;
        this.isAssigned = b;
    }

    public assignment copy(){
        return new assignment(new String(this.var), this.isAssigned);
    }
}
