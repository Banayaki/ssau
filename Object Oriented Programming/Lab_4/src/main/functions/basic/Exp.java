package functions.basic;

import functions.Function;

/**
 * Класс описывающий экспоненциальную функцию
 *
 * @see Function
 */
public class Exp implements Function {
    /**
     * @see Function#getLeftDomainBorder()
     */
    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
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
        return Math.exp(x);
    }
}
