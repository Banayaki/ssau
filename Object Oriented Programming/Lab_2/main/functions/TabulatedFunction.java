package functions;

import org.jetbrains.annotations.NotNull;

/**
 * Класс - реализует табулированную функцию в виде стандартного java массива.
 *
 * @author Mukhin Artem #6209
 * @see functions.TabulatedFunction
 * @see FunctionPoint
 */
@SuppressWarnings({"WeakerAccess", "unused", "StatementWithEmptyBody"})
public class TabulatedFunction {
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
    public TabulatedFunction(double leftX, double rightX, int pointCount) {
        if (pointCount < 2 || Double.compare(leftX, rightX) == 0 || rightX < leftX) {
            throw new IllegalArgumentException();
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
    public TabulatedFunction(double leftX, double rightX, @NotNull double[] values) {
        if (values.length == 0 || Double.compare(leftX, rightX) == 0 || rightX < leftX) {
            throw new IllegalArgumentException();
        }
        this.countOfPoints = values.length;
        this.values = new FunctionPoint[countOfPoints + 10];
        double length = (rightX - leftX) / (countOfPoints - 1.);
        for (int i = 0; i < countOfPoints; ++i) {
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
     * Метод позволяющий получить точку по её номеру
     *
     * @param index номер точки, которую хотим получить
     * @return возвращает искомую точку
     */
    public FunctionPoint getPoint(int index) {
        isNotOutOfBounds(index);
        return new FunctionPoint(this.values[index]);
    }

    /**
     * Метод заменяющий определенную точку на новую
     *
     * @param index - номер точки, которую хотим изменить
     * @param point - точка, на которую хотим заменить
     */
    public void setPoint(int index, FunctionPoint point) {
        isNotOutOfBounds(index);
        if (index == 0) {
            if (point.getX() < this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new IllegalArgumentException();
            }
        } else if (index == this.countOfPoints - 1) {
            if (point.getX() >= this.values[index - 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            if (point.getX() >= this.values[index - 1].getX() && point.getX() <= this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new IllegalArgumentException();
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
     */
    public void setPointX(int index, double x) {
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
        values[index].setY(y);
    }

    /**
     * Метод, который удаляет точку
     *
     * @param index - номер удаляемой точки
     */
    public void deletePoint(int index) {
        if (this.countOfPoints < 3) {
            throw new IllegalStateException();
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
     */
    public void addPoint(FunctionPoint point) {
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        int index = 0;
        for (; index < countOfPoints && point.getX() > values[index].getX(); ++index) ;
        if (index != this.countOfPoints && values[index].getX() == point.getX()) {
            System.out.println("Warning: use set instead of add. X value have equal in point set");
            this.setPoint(index, point);
        } else {
            rightShift(index);
            values[index] = point;
            this.countOfPoints += 1;
        }
    }

    /**
     * Переопределим метод, отпимизируя под наши нужды
     *
     * @return humanReadable строка
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.countOfPoints; i++) {
            builder.append(i).append(": ").append(values[i].toString()).append('\n');
        }
        return builder.toString();
    }

    /**
     * Проверяем не выходит ли за границу значений число index
     *
     * @param index - номер точки
     */
    private void isNotOutOfBounds(int index) {
        if (index < 0 || index >= countOfPoints) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Сдвиг влево, спомогательный метод, используемый при удалении
     */
    private void leftShift(int index) {
        for (; index < this.countOfPoints; ++index) {
            this.values[index] = this.values[index + 1];
        }
    }

    /**
     * Сдвиг вправо, спомогательный метод, используемый при удалении
     */
    private void rightShift(int index) {
        System.arraycopy(this.values, index, this.values, index + 1, this.countOfPoints - index);
    }

    /**
     * Метод вызываемый в случае равенства capacity и размера массива,
     * выделяется новый, больший участок памяти, в который копируются все значения старого массива
     */
    private void moveInBiggerArray() {
        FunctionPoint[] newArray = new FunctionPoint[(int) (this.values.length * 1.6)];
        System.arraycopy(this.values, 0, newArray, 0, this.countOfPoints);
        this.values = newArray;
    }
}
