package IR;

public class IRcommand_Func_Dec extends IRcommand_Label {
    public String end_label;

    public IRcommand_Func_Dec(String label_name, String end_label) {
        super(label_name);
        this.end_label = end_label;
    }
}