package functions;

import java.io.*;

public final class TabulatedFunctions {

    private TabulatedFunctions() {
        throw new UnsupportedOperationException("Нельзя создать объект класса: " + Functions.class.getName());
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() || pointsCount < 2) {
            throw new IllegalArgumentException("Некорректные аргументы. Проверьте значения: pointsCount = "
                    + pointsCount + ", leftX = " + leftX + ", rightX = " + rightX);
        }
        FunctionPoint[] values = new FunctionPoint[pointsCount + 10];
        double length = (rightX - leftX) / (pointsCount - 1.);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + length * i;
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return new ArrayTabulatedFunction(values, pointsCount);
    }

    public static void outputTabulatedFunction(TabulatedFunction functions, OutputStream out) {
        try (DataOutputStream stream = new DataOutputStream(out)) {
            int pointsCount = functions.getPointsCount();
            stream.writeInt(pointsCount);
            for (int i = 0; i < pointsCount; ++i) {
                stream.writeDouble(functions.getPointX(i));
                stream.writeDouble(functions.getPointY(i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) {
        try (DataInputStream stream = new DataInputStream(in)) {
            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount + 10];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return new ArrayTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer writer) {
        try (BufferedWriter stream = new BufferedWriter(writer)) {
            int pointsCount = function.getPointsCount();
            stream.write(String.valueOf(pointsCount) + " ");
            for (int i = 0; i < pointsCount; i++) {
                stream.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointsCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointsCount + 10];
            for (int i = 0; stream.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return new ArrayTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
