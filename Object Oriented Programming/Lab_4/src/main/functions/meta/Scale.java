package functions.meta;

import functions.FunctionImpl;

public class Scale implements FunctionImpl {
    private FunctionImpl funcToScale;
    private double scaleX;
    private double scaleY;

    public Scale(FunctionImpl funcToScale, double scaleX, double scaleY) {
        this.funcToScale = funcToScale;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        return funcToScale.getLeftDomainBorder() * scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        return funcToScale.getRightDomainBorder() * scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return funcToScale.getFunctionValue(x * scaleX) * scaleY;
    }
}
