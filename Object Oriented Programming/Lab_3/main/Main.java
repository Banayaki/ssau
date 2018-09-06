import functions.ArrayTabulatedFunction;
import functions.FunctionPoint;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.exceptions.InappropriateFunctionPointException;

public class Main {
    private static int n;

    public static void main(String[] args) throws InappropriateFunctionPointException {
        TabulatedFunction test = new ArrayTabulatedFunction(0, 10, new double[]{10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
        try {
            test.addPoint(new FunctionPoint(-1, 0));
            test.addPoint(0, new FunctionPoint(10, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            test.addPoint(6, new FunctionPoint(20, 0));
            test.addPoint(6, new FunctionPoint(9, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            test.setPoint(0, new FunctionPoint(99, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            for (int i = 6; i > 0; --i) {
                test.deletePoint(i);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            test = new ArrayTabulatedFunction(0, 0, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            test = new ArrayTabulatedFunction(0, 0, 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}