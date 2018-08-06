package functions.basic;

import functions.Function;

/**
 * Класс описывающий логарифмическую функцию
 *
 * @see Function
 */
public class Log implements Function {
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
     * @see Function#getLeftDomainBorder()
     */
    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    /**
     * @see Function#getRightDomainBorder()
     */
    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * @see Function#getFunctionValue(double)
     */
    @Override
    public double getFunctionValue(double x) {
        return Math.log(x) / Math.log(base);
    }
}
