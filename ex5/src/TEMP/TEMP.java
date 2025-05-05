package TEMP;

public class TEMP {
	public int serial = 0;
	public String reg = "";

	public TEMP(int serial) {
		this.serial = serial;
	}

	public TEMP(String reg) {
		this.reg = reg;
	}

	public int getSerialNumber() {
		return serial;
	}

	public String getRegister() {
		return reg;
	}

	@Override
	public String toString() {
		return "" + this.serial;
	}
}
