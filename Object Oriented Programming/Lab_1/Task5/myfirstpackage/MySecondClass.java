package myfirstpackage;

public class MySecondClass {
	private int value;
	private int size;
	
	public int getValue() {
		return value;
	}

	public int  getSize() {
		return size;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MySecondClass() {
		value = 0;
		size = 0;
	}

	public int calc() {
			return value + size;
	}
}

