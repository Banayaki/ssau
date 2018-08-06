import myfirstpackage.*;

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


