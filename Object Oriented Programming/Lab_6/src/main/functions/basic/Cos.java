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

    @Override
    public double getRightDomainBorder() {
        return super.getRightDomainBorder();
    }

    @Override
    public double getLeftDomainBorder() {
        return super.getLeftDomainBorder();
    }
}
