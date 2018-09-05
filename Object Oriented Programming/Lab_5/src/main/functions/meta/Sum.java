package functions.meta;

import functions.FunctionImpl;

public class Sum implements FunctionImpl {
    private FunctionImpl firstFunc;
    private FunctionImpl secondFunc;

    public Sum(FunctionImpl firstFunc, FunctionImpl secondFunc) {
        this.secondFunc = secondFunc;
        this.firstFunc = firstFunc;
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
        return firstFunc.getFunctionValue(x) + secondFunc.getFunctionValue(x);
    }
}
