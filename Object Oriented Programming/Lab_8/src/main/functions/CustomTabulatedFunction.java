package functions;

import functions.exceptions.InappropriateFunctionPointException;
import net.objecthunter.exp4j.Expression;


@SuppressWarnings("Duplicates")
public class CustomTabulatedFunction implements TabulatedFunctionImpl {
    private Expression expression;
    private int countOfPoints;
    private double interval;
    private double leftBorder;
    private double rightBorder;

    public CustomTabulatedFunction(Expression expression, double leftX, double rightX, int pointCount) {
        this.expression = expression;
        leftBorder = leftX;
        rightBorder = rightX;
        countOfPoints = pointCount;
        interval = (rightX - leftX) / (pointCount - 1.);
    }

    @Override
    public int getPointsCount() {
        return countOfPoints;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        double x = leftBorder + interval * index;
        return new FunctionPoint(x, getFunctionValue(x));
    }

    @Override
    public void setPoint(int index, FunctionPoint point) {
        System.out.println("Custom function can't change the points");
    }

    @Override
    public double getPointX(int index) {
        return leftBorder + interval * index;
    }

    @Override
    public void setPointX(int index, double x) {
        System.out.println("Custom function can't change the points");
    }

    @Override
    public double getPointY(int index) {
        return getFunctionValue(leftBorder + interval * index);
    }

    @Override
    public void setPointY(int index, double y) {
        System.out.println("Custom function can't change the points");
    }

    @Override
    public void deletePoint(int index) {
        System.out.println("Custom function can't change the points");
    }

    @Override
    public void addPoint(FunctionPoint point) {
        if (rightBorder < point.getX()) {
            rightBorder = point.getX();
        }
    }

    @Override
    public void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        addPoint(point);
    }

    @Override
    public double getLeftDomainBorder() {
        return leftBorder;
    }

    @Override
    public double getRightDomainBorder() {
        return rightBorder;
    }

    @Override
    public double getFunctionValue(double x) {
        try {
            return expression.setVariable("x", x).evaluate();
        } catch (Throwable ex) {
            if (ex instanceof ArithmeticException && "Division by zero".equals(ex.getMessage())) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NaN;
            }
        }
    }
}
