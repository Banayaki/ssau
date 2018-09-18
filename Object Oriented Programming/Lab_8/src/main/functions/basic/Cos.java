package functions.basic;

/**
 * Класс описывающий функцию косинуса
 *
 * @see TrigonometricFunction
 */
public class Cos extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.cos(x);
    }
}
