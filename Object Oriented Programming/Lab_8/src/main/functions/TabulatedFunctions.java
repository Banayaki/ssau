package functions;

import functions.exceptions.InappropriateFunctionPointException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс для работы с табулированными функциями
 *
 * @see TabulatedFunctionImpl
 */
@SuppressWarnings({"WeakerAccess", "Duplicates", "unused"})
public final class TabulatedFunctions {
    private static TabulatedFunctionFactory functionFactory = new ArrayTabulatedFunction.ArrayTabFuncFactory();

    private TabulatedFunctions() {
        throw new UnsupportedOperationException("Нельзя создать объект класса: " + Functions.class.getName());
    }

    /**
     * Статический метод, создающий табулированную функцию на основе обычной, например cos(x)
     *
     * @param function    - функция к которой применим табулирование
     * @param leftX       - левая граница
     * @param rightX      - правая граница
     * @param pointsCount - количество точек табулированной функции
     * @return - возвращает объект табулированной функции
     */
    public static TabulatedFunctionImpl tabulate(FunctionImpl function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() || pointsCount < 2) {
            throw new IllegalArgumentException("Некорректные аргументы. Проверьте значения: pointsCount = "
                    + pointsCount + ", leftX = " + leftX + ", rightX = " + rightX);
        }

        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double length = (rightX - leftX) / (pointsCount - 1.);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + length * i;
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(values);
    }

    /**
     * Статический метод, создающий табулированную функцию на основе обычной, например cos(x)
     *
     * @param clazz       - Указывает с каким типом хранения функцию нужно создать
     * @param function    - функция к которой применим табулирование
     * @param leftX       - левая граница
     * @param rightX      - правая граница
     * @param pointsCount - количество точек табулированной функции
     * @return - возвращает объект табулированной функции
     */
    public static TabulatedFunctionImpl tabulate(Class<? extends TabulatedFunctionImpl> clazz,
                                                 FunctionImpl function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() || pointsCount < 2) {
            throw new IllegalArgumentException("Некорректные аргументы. Проверьте значения: pointsCount = "
                    + pointsCount + ", leftX = " + leftX + ", rightX = " + rightX);
        }

        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double length = (rightX - leftX) / (pointsCount - 1.);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + length * i;
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(clazz, values);
    }

    /**
     * Статический метод используемый для вывода в байтовый поток
     * Data*Stream т.к. записываем и считываем мы только примитивы, сосбственно этот класс и предназначен для таких вещей
     *
     * @param functions - выводимая функция
     * @param out       - байтовый поток
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
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return createTabulatedFunction(points);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Статический метод используемый для чтения из байтового потока
     *
     * @param clazz - Указывает с каким типом хранения функцию нужно создать
     * @param in - байтовый поток
     * @return - возвращает табулированную функцию из точек файла
     */
    public static TabulatedFunctionImpl inputTabulatedFunction(Class<? extends TabulatedFunctionImpl> clazz, InputStream in) {
        try (DataInputStream stream = new DataInputStream(in)) {
            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; ++i) {
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            return createTabulatedFunction(clazz, points);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Статический метод используемый для записи в символьный поток
     *
     * @param function - функция которую будем записывать
     * @param writer   - символьный поток
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
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; stream.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return createTabulatedFunction(points);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Статический метод используемый для чтения из символьного потока
     *
     * @param clazz - Указывает с каким типом хранения функцию нужно создать
     * @param in - символьный поток
     * @return - готовая табулированная функция
     */
    public static TabulatedFunctionImpl readTabulatedFunction(Class<? extends TabulatedFunctionImpl> clazz, Reader in) {
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointsCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; stream.nextToken() != StreamTokenizer.TT_EOF; i++) {
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return createTabulatedFunction(clazz, points);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    /**
     * Устанавливает тип создаваемой функции
     *
     * @param functionFactory - тип
     */
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory) {
        TabulatedFunctions.functionFactory = functionFactory;
    }

    public static TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, int pointCount) {
        return functionFactory.createTabulatedFunction(leftX, rightX, pointCount);
    }

    public static TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, double[] values) {
        return functionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunctionImpl createTabulatedFunction(FunctionPoint[] ponits) {
        return functionFactory.createTabulatedFunction(ponits);
    }

    public static TabulatedFunctionImpl createTabulatedFunction(Class<? extends TabulatedFunctionImpl> clazz,
                                                                double leftX, double rightX, int pointCount)
                                                                throws IllegalArgumentException{
        TabulatedFunctionImpl function;
        try {
            Constructor<? extends TabulatedFunctionImpl> constructor = clazz.getConstructor(
                    double.class, double.class, int.class);
            function = constructor.newInstance(leftX, rightX, pointCount);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
        return function;
    }

    public static TabulatedFunctionImpl createTabulatedFunction(Class<? extends TabulatedFunctionImpl> clazz,
                                                                double leftX, double rightX, double[] values)
                                                                throws IllegalArgumentException{
        TabulatedFunctionImpl function;
        try {
            Constructor<? extends TabulatedFunctionImpl> constructor = clazz.getConstructor(
                    double.class, double.class, double[].class);
            function = constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
        return function;
    }

    public static TabulatedFunctionImpl createTabulatedFunction(Class<? extends TabulatedFunctionImpl> clazz, FunctionPoint[] points)
                                                                throws IllegalArgumentException{
        TabulatedFunctionImpl function;
        try {
            Constructor<? extends TabulatedFunctionImpl> constructor = clazz.getConstructor(FunctionPoint[].class);
            function = constructor.newInstance((Object) points);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }
}
