package threads;

import functions.FunctionImpl;
import functions.Functions;

public class Task {

    private FunctionImpl function;
    private double leftBorder;
    private double rightBorder;
    private double samplingStep;

    public Task() {

    }

    public Task(FunctionImpl function, double leftBorder, double rightBorder, double samplingStep) {
        this.function = function;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.samplingStep = samplingStep;
    }

    public double work() {
        return Functions.trapezoidIntegrate(function, leftBorder, rightBorder, samplingStep);
    }

    public FunctionImpl getFunction() {
        return function;
    }

    public void setFunction(FunctionImpl function) {
        this.function = function;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
    }

    public double getSamplingStep() {
        return samplingStep;
    }

    public void setSamplingStep(double samplingStep) {
        this.samplingStep = samplingStep;
    }
}
