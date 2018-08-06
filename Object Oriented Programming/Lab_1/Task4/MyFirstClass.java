class MyFirstClass { 
	public static void main(String[] arg) { 
		MySecondClass o = new MySecondClass();
		int i, j;
		for (i = 1; i <= 8; ++i) {
			for (j = 1; j <= 8; ++j) {
				o.setValue(i);
				o.setSize(j);
				System.out.print(o.calc() + " ");
			}
			System.out.println();
		}
	} 
}

class MySecondClass {
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

	MySecondClass() {
		value = 0;
		size = 0;
	}

	public int calc() {
			return value + size;
	}
}

