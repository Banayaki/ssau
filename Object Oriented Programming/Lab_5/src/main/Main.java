import functions.LinkedListTabulatedFunction;
import functions.exceptions.InappropriateFunctionPointException;

public class Main {
    private static int n;

    public static void main(String[] args) throws InappropriateFunctionPointException {
        Object n = new LinkedListTabulatedFunction(0, 10, 10);
        Object n2 = new LinkedListTabulatedFunction(0, 10, 10);
        System.out.println(n.equals(n2));
    }
}
