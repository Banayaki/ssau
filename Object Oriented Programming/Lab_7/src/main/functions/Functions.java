package functions;

import functions.meta.*;

public final class Functions {

    private Functions() {
        throw new UnsupportedOperationException("Нельзя создать объект класса: " + Functions.class.getName());
    }

    public static FunctionImpl shift(FunctionImpl f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static FunctionImpl scale(FunctionImpl f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }

    public static FunctionImpl power(FunctionImpl f, double power) {
        return new Power(f, power);
    }

    public static FunctionImpl sum(FunctionImpl f1, FunctionImpl f2) {
        return new Sum(f1, f2);
    }

    public static FunctionImpl mult(FunctionImpl f1, FunctionImpl f2) {
        return new Mult(f1, f2);
    }

    public static FunctionImpl composition(FunctionImpl f1, FunctionImpl f2) {
        return new Composition(f1, f2);
    }

    public static double trapezoidIntegrate(FunctionImpl function, double x1, double x2, double dx) {
        int step = (int) Math.ceil((x2 - x1) / dx);
        double total = 0;
        double x = x1;
        for (int i = 0; i < step - 1; ++i, x += dx) {
            double m = (function.getFunctionValue(x) + function.getFunctionValue(x + dx)) / 2;
            total += dx * m;
        }
        double m = (function.getFunctionValue(x) + function.getFunctionValue(x2)) / 2;
        total += dx * m;
        return total;
//        int repeats = (int) Math.ceil((function.getRightDomainBorder() - function.getLeftDomainBorder()) / step);
//        double left = function.getLeftDomainBorder();
//        double results = 0;
//
//        for (int i = 0; i < repeats; ++i) {
//            results += (function.getFunctionValue(left) + function.getFunctionValue(left + step)) / 2 * step;
//            left += step;
//        }
//        results += (function.getFunctionValue(left) + function.getFunctionValue(function.getRightDomainBorder())) / 2 *
//                (function.getRightDomainBorder() - left);
//
//        return results;
    }

    public static double adaptiveIntegrate(FunctionImpl function, double x1, double x2, double dx) {
        int numIntervals = (int) Math.ceil((x2 - x1) / dx);
        double total = 0;
        double x = x1;
        for (int i = 0; i < numIntervals; ++i) {
            total += sliceArea(function, x, x + dx, 0.1);
            x += dx;
        }
        return total;
    }

    private static double sliceArea(FunctionImpl function, double x1, double x2, double maxSliceError) {
        double y1 = function.getFunctionValue(x1);
        if (x2 > function.getRightDomainBorder()) x2 = function.getRightDomainBorder();
        double y2 = function.getFunctionValue(x2);
        double xm = (x1 + x2) / 2;
        double ym = function.getFunctionValue(xm);

        double area12 = (x2 - x1) * (y2 + y1) / 2.0;
        double area1m = (xm - x1) * (ym + y1) / 2.0;
        double aream2 = (x2 - xm) * (ym + y2) / 2.0;
        double area1m2 = area1m + aream2;
        double error = Math.abs(area1m2 - area12) / area12;

        if (error < maxSliceError) return area1m2;
        return sliceArea(function, x1, xm, maxSliceError) + sliceArea(function, xm, x2, maxSliceError);
    }

    private static double calcArea(double left, double right, double dx) {
        return (left + right) / 2 * dx;
    }
}
