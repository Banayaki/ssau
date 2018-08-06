package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;

/**
 * Класс - реализует табулированную функцию в виде стандартного java массива.
 *
 * @author Mukhin Artem #6209
 * @see functions.TabulatedFunction
 * @see FunctionPoint
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ArrayTabulatedFunction implements TabulatedFunction {
    /** Массив содержащий точки функции */
    private FunctionPoint[] values;
    /** Количество точек функции */
    private int countOfPoints;

    /**
     * Конструктор - создание нового объекта с определенным количеством точек, значения которых равны 0
     *
     * @param leftX - задает левую границу области определения функции
     * @param rightX - задает правую границу области определения функции
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
     * @param leftX - задает левую границу области определения функции
     * @param rightX - задает правую границу области определения функции
     * @param values - массив значений функции
     */
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (values == null || values.length == 0 || Math.abs(leftX - rightX) < 10e-14 || rightX < leftX) {
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
     * Метод получения левой границы области определения функции
     * @return - возвращает левую границу области определения
     */
    @Override
    public double getLeftDomainBorder() {
        return this.values[0].getX();
    }

    /**
     * Метод получения правой границы области определения функции
     * @return - возвращает правую границу области определения
     */
    @Override
    public double getRightDomainBorder() {
        return this.values[this.countOfPoints - 1].getX();
    }


    /**
     * Находить значение функции по заданной точке
     *
     * @param x - аргумент, в котором нужно найти значение нашей функции
     * @return - возвращает значение функции в точке х
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
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
     * Метод получения количества точек
     *
     * @return - возвращает количество точек табултируемой функции
     */
    @Override
    public int getPointsCount() {
        return this.countOfPoints;
    }

    /**
     * Метод получения точки по её номеру
     *
     * @param index номер точки, которую хотим получить
     * @return возвращает искомую точку
     */
    @Override
    public FunctionPoint getPoint(int index) {
        isNotOutOfBounds(index);
        return this.values[index];
    }


    /**
     * Метод заменяющий определенную точку на новую
     *
     * @param index - номер точки, которую хотим изменить
     * @param point - точка, на которую хотим заменить
     * @throws InappropriateFunctionPointException - изменение точки несоотвествующим образом
     */
    @Override
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
     * Метод возвращающий значение точки по ОХ.
     *
     * @param index - индекс нужной точки
     * @return - возвращает знаение прообраза точки
     */
    @Override
    public double getPointX(int index) {
        isNotOutOfBounds(index);
        return values[index].getX();
    }

    /**
     * Метод позволяющий изменить значение прообраза точки
     *
     * @param index - номер изменяемой точки
     * @param x - новое значение прообраза
     * @throws InappropriateFunctionPointException - изменение точки несоотвествующим образом
     */
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        isNotOutOfBounds(index);
        setPoint(index, new FunctionPoint(x, values[index].getY()));
    }

    /**
     * Метод вовзращающий значение точки по ОY
     *
     * @param index - номер нужной точки
     * @return - возвращает значение образа выбранной точки
     */
    @Override
    public double getPointY(int index) {
        isNotOutOfBounds(index);
        return values[index].getY();
    }

    /**
     * Метод позволяющий изменить значение образа точки
     *
     * @param index - номер изменяемой точки
     * @param y - новое значение образа
     */
    @Override
    public void setPointY(int index, double y) {
        isNotOutOfBounds(index);
        getPoint(index).setY(y);
    }

    /**
     * Метод, который удаляет точку
     *
     * @param index - номер удаляемой точки
     */
    @Override
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
     * Добавить точку в в конец функции
     *
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
     */
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        if (this.values[countOfPoints - 1].getX() >= point.getX()) {
            throw new InappropriateFunctionPointException("Your point is to the left of rightX");
        }
        this.values[countOfPoints++] = new FunctionPoint(point);
    }

    /**
     * Добавление точки в определенное место
     *
     * @param index - место в которую нужно вставить точку
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
     */
    @Override
    public void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        if (this.values[index - 1].getX() <= point.getX() && point.getX() <= this.values[index].getX()) {
            rightShift(index);
            values[index] = new FunctionPoint(point);
        } else {
            throw new InappropriateFunctionPointException("Your point have incorrect X value: x = " + point.getX());
        }
    }

    /**
     * Метод преобразующий объект в humanreadable строку
     * @return - строка
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.countOfPoints; ++i) {
            builder.append(i).append(": ").append(values[i].toString()).append('\n');
        }
        return builder.toString();
    }

    /**
     * Метод соверщающий проверку на обращение к несуществующему индексу, если обращение некорректное - выбрасывем
     * исключение
     *
     * @param index - проверяемый номер
     * @exception  FunctionPointIndexOutOfBoundsException - выход за границу допустимых индексов
     */
    private void isNotOutOfBounds(int index) {
        if (index < 0 || index >= countOfPoints) {
            throw new FunctionPointIndexOutOfBoundsException("Index must be in range: [" + 0 + ", " + countOfPoints + ")");
        }
    }

    /**
     * Метод совершающий сдвиг значений массива {@link ArrayTabulatedFunction#values} влево
     * @param index - с какого индекса начинать свдиг
     */
    private void leftShift(int index) {
        for (; index < this.countOfPoints; ++index) {
            this.values[index] = this.values[index + 1];
        }
    }

    /**
     * Метод совершающий сдвиг значений массива {@link ArrayTabulatedFunction#values} вправо
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
}
