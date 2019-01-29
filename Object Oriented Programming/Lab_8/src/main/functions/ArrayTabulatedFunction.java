package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Класс - реализует табулированную функцию в виде стандартного java массива.
 *
 * @author Mukhin Artem #6209
 * @see TabulatedFunctionImpl
 * @see FunctionPoint
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ArrayTabulatedFunction implements TabulatedFunctionImpl, Serializable, Iterable<FunctionPoint> {
    /**
     * Массив содержащий точки функции
     */
    private FunctionPoint[] values;
    /**
     * Количество точек функции
     */
    private int countOfPoints;

    /**
     * Конструктор - создание нового объекта с определенным количеством точек, значения которых равны 0
     *
     * @param leftX      - задает левую границу области определения функции
     * @param rightX     - задает правую границу области определения функции
     * @param pointCount - задает количество точек функции
     */
    public ArrayTabulatedFunction(double leftX, double rightX, int pointCount) {
        if (pointCount < 2 || Math.abs(leftX - rightX) < Utils.EPS || rightX < leftX) {
            throw new IllegalArgumentException("Illegal argument: pointCount = " + pointCount + ", leftX = " + leftX
                    + ", rightX = " + rightX);
        }
        this.values = new FunctionPoint[pointCount + 10];
        this.countOfPoints = pointCount;
        double length = (rightX - leftX) / (pointCount - 1.);
        for (int i = 0; i < pointCount; ++i) {
            this.values[i] = new FunctionPoint(leftX + length * i, 0);
        }
    }

    /**
     * Конструктор - создаение нового объекта с определенными значениями функции
     *
     * @param leftX  - задает левую границу области определения функции
     * @param rightX - задает правую границу области определения функции
     * @param values - массив значений функции
     */
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (values == null || values.length < 2 || Math.abs(leftX - rightX) < 10e-14 || rightX < leftX) {
            throw new IllegalArgumentException("Illegal argument: leftX = " + leftX + ", rightX = " + rightX
                    + ", values[] - watch yourself");
        }
        int pointCount = values.length;
        this.values = new FunctionPoint[pointCount + 10];
        this.countOfPoints = pointCount;
        double length = (rightX - leftX) / (pointCount - 1.);
        for (int i = 0; i < pointCount; ++i) {
            this.values[i] = new FunctionPoint(leftX + length * i, values[i]);
        }
    }

    /**
     * Конструктор - создание нового объекта из массива точек
     *
     * @param pointsArray - массив точек функции
     */
    public ArrayTabulatedFunction(FunctionPoint[] pointsArray) throws InappropriateFunctionPointException {
        if (pointsArray.length < 2) {
            throw new IllegalArgumentException("Illegal argument FunctionPoint[] - have nor right count of points");
        }
        int countOfPoints = pointsArray.length;
        this.countOfPoints = 1;
        values = new FunctionPoint[countOfPoints + 10];
        values[0] = pointsArray[0];
        for (int i = 1; i < countOfPoints; ++i) {
            this.addPoint(pointsArray[i]);
        }
    }

    /**
     * @see FunctionImpl#getLeftDomainBorder()
     */
    public double getLeftDomainBorder() {
        return this.values[0].getX();
    }

    /**
     * @see FunctionImpl#getRightDomainBorder()
     */
    public double getRightDomainBorder() {
        return this.values[this.countOfPoints - 1].getX();
    }


    /**
     * @see FunctionImpl#getFunctionValue(double)
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public double getFunctionValue(double x) {
        int i;
        if (this.values[0].getX() > x || this.values[this.countOfPoints - 1].getX() < x) return Double.NaN;
        for (i = 1; i < this.countOfPoints && this.values[i].getX() < x; ++i) ;
        double leftX = this.values[i - 1].getX();
        double leftY = this.values[i - 1].getY();
        double rightX = this.values[i].getX();
        double rightY = this.values[i].getY();
        return ((rightY - leftY) * (x - leftX)) / (rightX - leftX) + leftY;
    }

    /**
     * @see TabulatedFunctionImpl#getPointsCount()
     */
    public int getPointsCount() {
        return this.countOfPoints;
    }

    /**
     * @see TabulatedFunctionImpl#getPoint(int)
     */
    public FunctionPoint getPoint(int index) {
        isNotOutOfBounds(index);
        return this.values[index];
    }


    /**
     * @see TabulatedFunctionImpl#setPoint(int, FunctionPoint)
     */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        isNotOutOfBounds(index);
        if (index == 0) {
            if (point.getX() <= this.values[index + 1].getX()) {
                this.values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point is to the left of leftX");
            }
        } else if (index == this.countOfPoints - 1) {
            if (point.getX() >= this.values[index - 1].getX()) {
                this.values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point is to the right of rightX");
            }
        } else {
            if (point.getX() >= this.values[index - 1].getX() && point.getX() <= this.values[index + 1].getX()) {
                this.values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point doesn't fall in right range");
            }
        }
    }

    /**
     * @see TabulatedFunctionImpl#getPointX(int)
     */
    public double getPointX(int index) {
        isNotOutOfBounds(index);
        return values[index].getX();
    }

    /**
     * @see TabulatedFunctionImpl#setPointX(int, double)
     */
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        isNotOutOfBounds(index);
        setPoint(index, new FunctionPoint(x, values[index].getY()));
    }

    /**
     * @see TabulatedFunctionImpl#getPointY(int)
     */
    public double getPointY(int index) {
        isNotOutOfBounds(index);
        return values[index].getY();
    }

    /**
     * @see TabulatedFunctionImpl#setPointY(int, double)
     */
    public void setPointY(int index, double y) {
        isNotOutOfBounds(index);
        getPoint(index).setY(y);
    }

    /**
     * @see TabulatedFunctionImpl#deletePoint(int)
     */
    public void deletePoint(int index) {
        if (this.countOfPoints < 3) {
            throw new IllegalStateException("Can't remove the point, because function must have at least two points");
        }
        isNotOutOfBounds(index);
        this.values[index] = null;
        leftShift(index);
        --this.countOfPoints;
    }

    /**
     * @see TabulatedFunctionImpl#addPoint(FunctionPoint)
     */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        if (this.values[countOfPoints - 1].getX() >= point.getX()) {
            throw new InappropriateFunctionPointException("Your point is to the left of rightX");
        } else if (this.values[countOfPoints - 1].getX() == point.getX()) {
            System.out.println("Warning: use set instead of add. X value have equal in point set");
            this.setPoint(countOfPoints - 1, point);
        } else {
            this.values[countOfPoints++] = new FunctionPoint(point);
        }
    }

    /**
     * @see TabulatedFunctionImpl#addPoint(int, FunctionPoint)
     */
    public void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index > 0 && index > this.countOfPoints) {
            throw new IndexOutOfBoundsException("Your index = " + index + " is out of bounds");
        }
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        //noinspection ConstantConditions
        //Сложно читаемое возможно условие.
        //Ошибкой не считает: 1) если добавлем точку в начало и она лежил левее левой границы
        //2) если точка добавляется в конец и лежит правее правой границы
        //3) если точка добавляется не в края массива, и лежит между двумя соседними точками.
        if (index == 0 && point.getX() <= this.getLeftDomainBorder() ||
                index == this.countOfPoints && point.getX() >= this.getRightDomainBorder() ||
                index != this.countOfPoints && index != 0 &&
                        this.values[index - 1].getX() <= point.getX() && point.getX() <= this.values[index].getX()) {
            rightShift(index);
            if (index != this.countOfPoints && values[index].getX() == point.getX()) {
                System.out.println("Warning: use set instead of add. X value have equal in point set");
                this.setPoint(index, point);
            }
            values[index] = new FunctionPoint(point);
            this.countOfPoints += 1;
        } else {
            throw new InappropriateFunctionPointException("Your point have incorrect X value: x = " + point.getX());
        }
    }

    /**
     * Метод преобразующий объект в humanReadable строку
     *
     * @return - строка
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append(values[0].toString());
        for (int i = 1; i < this.countOfPoints; ++i) {
            builder.append(", ").append(values[i].toString());
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TabulatedFunctionImpl)) return false;
        if (obj instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction aObj = (ArrayTabulatedFunction) obj;
            if (aObj.countOfPoints != this.countOfPoints) return false;
            for (int i = 0; i < this.countOfPoints; ++i) {
                if (!aObj.values[i].equals(this.values[i])) return false;
            }
            return true;
        } else {
            if (((TabulatedFunctionImpl) obj).getPointsCount() != this.getPointsCount()) return false;
            for (int i = 0; i < this.countOfPoints; ++i) {
                if (!((TabulatedFunctionImpl) obj).getPoint(i).equals(this.values[i])) return false;
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(countOfPoints);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Метод соверщающий проверку на обращение к несуществующему индексу, если обращение некорректное - выбрасывем
     * исключение
     *
     * @param index - проверяемый номер
     * @throws FunctionPointIndexOutOfBoundsException - выход за границу допустимых индексов
     */
    private void isNotOutOfBounds(int index) {
        if (index < 0 || index >= countOfPoints) {
            throw new FunctionPointIndexOutOfBoundsException("Index must be in range: [" + 0 + ", " + countOfPoints + ")");
        }
    }

    /**
     * Метод совершающий сдвиг значений массива {@link ArrayTabulatedFunction#values} влево
     *
     * @param index - с какого индекса начинать свдиг
     */
    private void leftShift(int index) {
        for (; index < this.countOfPoints; ++index) {
            this.values[index] = this.values[index + 1];
        }
    }

    /**
     * Метод совершающий сдвиг значений массива {@link ArrayTabulatedFunction#values} вправо
     *
     * @param index - с какого индекса начинать свдиг
     */
    private void rightShift(int index) {
        for (int i = this.countOfPoints; this.countOfPoints > index; --i) {
            this.values[i] = this.values[i - 1];
        }
    }

    /**
     * При заполнении выделенной памяти этот метод переносит массив в новый участок памяти большего размера
     */
    private void moveInBiggerArray() {
        FunctionPoint[] newArray = new FunctionPoint[(int) (this.values.length * 1.6)];
        System.arraycopy(this.values, 0, newArray, 0, this.countOfPoints);
        this.values = newArray;
    }

    /**
     * Метод, создающий итератор позволяющий... итерироваться по функции, нужен по заданию с for each
     *
     * @return возвращает итератор функиции
     */
    @NotNull
    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<>() {
            private int currentIndex = -1;

            @Override
            public boolean hasNext() {
                return currentIndex + 1 != countOfPoints && values[currentIndex + 1] != null;
            }

            @Override
            public FunctionPoint next() {
                if (!hasNext()) {
                    String msg = "Function has no more points";
                    throw new NoSuchElementException(msg);
                }
                return new FunctionPoint(values[++currentIndex]);
            }

            @Override
            public void remove() {
                String msg = "Operation remove is unsupported";
                throw new UnsupportedOperationException(msg);
            }
        };
    }

    /**
     * Класс изготовитель если можно так выразиться, создает объекты нашей функции.
     *
     * @see TabulatedFunctionFactory
     */
    public static class ArrayTabFuncFactory implements TabulatedFunctionFactory{

        @Override
        public TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, int countOfPoints) {
            return new ArrayTabulatedFunction(leftX, rightX, countOfPoints);
        }

        @Override
        public TabulatedFunctionImpl createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public TabulatedFunctionImpl createTabulatedFunction(FunctionPoint[] points) {
            try {
                return new ArrayTabulatedFunction(points);
            } catch (InappropriateFunctionPointException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
