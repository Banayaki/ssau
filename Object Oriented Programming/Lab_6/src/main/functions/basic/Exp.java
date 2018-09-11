package functions.basic;

import functions.FunctionImpl;

/**
 * Класс описывающий экспоненциальную функцию
 *
 * @see FunctionImpl
 */
public class Exp implements FunctionImpl {
    /**
     * @see FunctionImpl#getLeftDomainBorder()
     */
    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
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
        return Math.exp(x);
    }
}
