package functions.basic;

import functions.FunctionImpl;

/**
 * Класс описывающий логарифмическую функцию
 *
 * @see FunctionImpl
 */
public class Log implements FunctionImpl {
    /**
     * 2.718...
     */
    private double base = Math.E;

    /**
     * @param base - основание логарифма
     */
    public Log(double base) {
        this.base = base;
    }

    /**
     * @see FunctionImpl#getLeftDomainBorder()
     */
    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    /**
     * @see FunctionImpl#getRightDomainBorder()
     */
    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * @see FunctionImpl#getFunctionValue(double)
     */
    @Override
    public double getFunctionValue(double x) {
        return Math.log(x) / Math.log(base);
    }
}
