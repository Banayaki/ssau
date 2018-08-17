package functions.meta;

import functions.Function;

public class Sum implements Function {
    private Function firstFunc;
    private Function secondFunc;

    public Sum(Function firstFunc, Function secondFunc) {
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
