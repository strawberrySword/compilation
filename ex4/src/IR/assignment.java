package IR;

import java.util.Objects;

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

    @Override
	public boolean equals(Object O){
        if (O instanceof assignment a){
            return (this.var.equals(a.var) && this.isAssigned == a.isAssigned);
        }
        return false;
	}

    @Override
    public int hashCode(){
        return Objects.hash(var, isAssigned);
    }
}
