import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.exceptions.InappropriateFunctionPointException;

public class Main {
    private static int n;

    public static void main(String[] args) throws InappropriateFunctionPointException {
        ArrayTabulatedFunction test = new ArrayTabulatedFunction(0, 10, new double[]{10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
        LinkedListTabulatedFunction third = new LinkedListTabulatedFunction(test);
        System.out.println(third.getPoint(0).getX());
        System.out.println(third.getPoint(0).getY());
        System.out.println(third.getPoint(1).getX());
        System.out.println(third.getPoint(1).getY());
        System.out.println(third.getPoint(2).getX());
        System.out.println(third.getPoint(2).getY());
        System.out.println(third.getPoint(3).getX());
        System.out.println(third.getPoint(3).getY());
        System.out.println(third.getPoint(4).getX());
        System.out.println(third.getPoint(4).getY());
        System.out.println(third.getPoint(5).getX());
        System.out.println(third.getPoint(5).getY());
    }
}
