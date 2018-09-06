package functions;

import functions.exceptions.InappropriateFunctionPointException;

import java.io.*;

/**
 * Класс для работы с табулированными функциями
 *
 * @see TabulatedFunctionImpl
 */
@SuppressWarnings("WeakerAccess")
public final class TabulatedFunctions {

    private TabulatedFunctions() {
        throw new UnsupportedOperationException("Нельзя создать объект класса: " + Functions.class.getName());
    }

    /**
     * Статический метод, создающий табулированную функцию на основе обычной, например cos(x)
     *
     * @param function - функция к которой применим табулирование
     * @param leftX - левая граница
     * @param rightX - правая граница
     * @param pointsCount - количество точек табулированной функции
     * @return - возвращает объект табулированной функции
     */
    public static TabulatedFunctionImpl tabulate(FunctionImpl function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
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

    /**
     * Статический метод используемый для вывода в байтовый поток
     * Data*Stream т.к. записываем и считываем мы только примитивы, сосбственно этот класс и предназначен для таких вещей
     *
     * @param functions - выводимая функция
     * @param out - байтовый поток
     */
    public static void outputTabulatedFunction(TabulatedFunctionImpl functions, OutputStream out) {
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

    /**
     * Статический метод используемый для чтения из байтового потока
     *
     * @param in - байтовый поток
     * @return - возвращает табулированную функцию из точек файла
     */
    public static TabulatedFunctionImpl inputTabulatedFunction(InputStream in) {
        try (DataInputStream stream = new DataInputStream(in)) {
            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount + 10];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return new ArrayTabulatedFunction(points, pointsCount);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Статический метод используемый для записи в символьный поток
     *
     * @param function - функция которую будем записывать
     * @param writer - символьный поток
     */
    public static void writeTabulatedFunction(TabulatedFunctionImpl function, Writer writer) {
        try (BufferedWriter stream = new BufferedWriter(writer)) {
            int pointsCount = function.getPointsCount();
            stream.write(String.valueOf(pointsCount) + " ");
            for (int i = 0; i < pointsCount; i++) {
                stream.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    /**
     * Статический метод используемый для чтения из символьного потока
     *
     * @param in - символьный поток
     * @return - готовая табулированная функция
     */
    public static TabulatedFunctionImpl readTabulatedFunction(Reader in) {
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
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
        return null;
    }
}
