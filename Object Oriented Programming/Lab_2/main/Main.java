import functions.FunctionPoint;
import functions.TabulatedFunction;

public class Main {

    public static void main(String[] args) {
        TabulatedFunction function = new TabulatedFunction(-3, 3, new double[]{9, 4, 1, 0, 1, 4, 9});
        System.out.println(function.getPoint(3).toString());
        System.out.println(function.getPoint(0).toString());
        function.addPoint(new FunctionPoint(-3.5, -1));
        function.addPoint(new FunctionPoint(0.5, 10));
        function.addPoint(new FunctionPoint(3.5, -1));
        System.out.println(function.toString());
        function.deletePoint(4);
        function.deletePoint(4);
        System.out.println(function.toString());
    }
}
