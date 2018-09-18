package functions;

/**
 * Интерфейс помгающий реализовать паттерн factoryMethod
 */
public interface TabulatedFunctionFactory {

    TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, int countOfPoints);

    TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, double[] values);

    TabulatedFunctionImpl createTabulatedFunction(FunctionPoint[] points);

}
