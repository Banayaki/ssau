package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;
import org.jetbrains.annotations.NotNull;

/**
 * Класс - реализует табулированную функцию в виде стандартного java массива.
 *
 * @author Mukhin Artem #6209
 * @see functions.TabulatedFunction
 * @see FunctionPoint
 */

@SuppressWarnings({"WeakerAccess", "unused", "StatementWithEmptyBody"})
public class ArrayTabulatedFunction implements TabulatedFunction {
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
            throw new IllegalArgumentException("Illegal argument, check them: pointCount = " + pointCount + ", leftX = " + leftX
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
    public ArrayTabulatedFunction(double leftX, double rightX, @NotNull double[] values) {
        if (values.length == 0 || Math.abs(leftX - rightX) < 10e-14 || rightX < leftX) {
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
     *
     * @return - возвращает левую границу области определения
     */
    public double getLeftDomainBorder() {
        return this.values[0].getX();
    }

    /**
     * Метод получения правой границы области определения функции
     *
     * @return - возвращает правую границу области определения
     */
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
    public int getPointsCount() {
        return this.countOfPoints;
    }

    /**
     * Метод получения точки по её номеру
     *
     * @param index номер точки, которую хотим получить
     * @return возвращает искомую точку
     */
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
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        isNotOutOfBounds(index);
        if (index == 0) {
            if (point.getX() < this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point have incorrect parameter x = " + point.getX());
            }
        } else if (index == this.countOfPoints - 1) {
            if (point.getX() >= this.values[index - 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point have incorrect parameter x = " + point.getX());
            }
        } else {
            if (point.getX() >= this.values[index - 1].getX() && point.getX() <= this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new InappropriateFunctionPointException("Your point have incorrect parameter x = " + point.getX());
            }
        }
    }

    /**
     * Метод возвращающий значение точки по ОХ.
     *
     * @param index - индекс нужной точки
     * @return - возвращает знаение прообраза точки
     */
    public double getPointX(int index) {
        isNotOutOfBounds(index);
        return values[index].getX();
    }

    /**
     * Метод позволяющий изменить значение прообраза точки
     *
     * @param index - номер изменяемой точки
     * @param x     - новое значение прообраза
     * @throws InappropriateFunctionPointException - изменение точки несоотвествующим образом
     */
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
    public double getPointY(int index) {
        isNotOutOfBounds(index);
        return values[index].getY();
    }

    /**
     * Метод позволяющий изменить значение образа точки
     *
     * @param index - номер изменяемой точки
     * @param y     - новое значение образа
     */
    public void setPointY(int index, double y) {
        isNotOutOfBounds(index);
        getPoint(index).setY(y);
    }

    /**
     * Метод, который удаляет точку
     *
     * @param index - номер удаляемой точки
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
     * Добавить точку в в конец функции
     *
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
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
     * Добавление точки в определенное место
     *
     * @param index - место в которую нужно вставить точку
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
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
        for (int i = this.countOfPoints; this.countOfPoints > index && i > 0; --i) {
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
