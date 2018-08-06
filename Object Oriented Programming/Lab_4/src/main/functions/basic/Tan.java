package functions.basic;

/**
 * Класс описывающий функцию тангенса
 *
 * @see TrigonometricFunction
 */
public class Tan extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.tan(x);
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
