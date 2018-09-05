package functions.basic;

import functions.FunctionImpl;

/**
 * Класс описывающий тригонометрические функции
 *
 * @see FunctionImpl
 */
public abstract class TrigonometricFunction implements FunctionImpl {
    /**
     * @see FunctionImpl#getLeftDomainBorder()
     */
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }
    /**
     * @see FunctionImpl#getRightDomainBorder()
     */
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }
    /**
     * @see FunctionImpl#getFunctionValue(double)
     */
    public abstract double getFunctionValue(double x);
}
