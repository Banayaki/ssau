package functions.basic;

/**
 * Класс описывающий функцию синуса
 *
 * @see TrigonometricFunction
 */
public class Sin extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.sin(x);
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
