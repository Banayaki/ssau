package functions.meta;

import functions.Function;

public class Mult implements Function {
    private Function firstFunc;
    private Function secondFunc;

    public Mult(Function firstFunc, Function secondFunc) {
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
