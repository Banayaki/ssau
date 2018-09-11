package functions.meta;

import functions.FunctionImpl;

public class Mult implements FunctionImpl {
    private FunctionImpl firstFunc;
    private FunctionImpl secondFunc;

    public Mult(FunctionImpl firstFunc, FunctionImpl secondFunc) {
        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
    }

    @Override
    public double getLeftDomainBorder() {
        return firstFunc.getLeftDomainBorder() <= secondFunc.getLeftDomainBorder() ?
                firstFunc.getLeftDomainBorder() : secondFunc.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return firstFunc.getRightDomainBorder() <= secondFunc.getRightDomainBorder() ?
                firstFunc.getRightDomainBorder() : secondFunc.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return firstFunc.getFunctionValue(x) * secondFunc.getFunctionValue(x);
    }
}
