package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;


/**
 * Класс реализующий табулируемую функцию в виде двусвязного циклического списка
 *
 * @author Artem Mukhin #6209
 * @see java.util.LinkedList
 * @see functions.TabulatedFunction
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class LinkedListTabulatedFunction implements TabulatedFunction {

    /**
     * Внутренний класс - член, имеет доступ ко всем полям и методам обрабляющего класса (объекта), класс объявлен
     * е статическим, поскольку в нём не используются никакие поля внешнего класса. И для внешнего класса и
     * вложенный класс -> сохранение инкапсуляции
     *
     * @see java.util.LinkedList
     */
    private static class FunctionNode {
        /**
         * Значение узла
         */
        FunctionPoint item;
        /**
         * Ссылка на предыдущий узел
         */
        FunctionNode prev;
        /**
         * Ссылка не следуюзий узел
         */
        FunctionNode next;

        /**
         * Конструктор по умолчанию
         */
        FunctionNode() {
            this.item = null;
            this.prev = null;
            this.next = null;
        }

        /**
         * Конструктор создающий элемент списка
         *
         * @param prev - пердыдущий узел
         * @param item - значение узла
         * @param next - следующий узел
         */
        FunctionNode(FunctionNode prev, FunctionPoint item, FunctionNode next) {
            this.item = new FunctionPoint(item);
            this.prev = prev;
            this.next = next;
        }

        /**
         * Конструктор копирующий
         *
         * @param node - копируемый узел
         * @deprecated
         */
        @Deprecated
        FunctionNode(FunctionNode node) {
            this.item = new FunctionPoint(node.item);
            this.next = node.next;
            this.prev = node.prev;
        }

    }

    /**
     * Узел листа, не имеющий значения, но указывающий на начло и конец списка
     */
    private FunctionNode head;
    /**
     * Переменная хранящее количество точек
     */
    private int countOfPoints;
    /**
     * Кешируемая нода, для быстрого нахождения близнаходящихся нод
     */
    private FunctionNode cache;
    /**
     * Номер кешируемой ноды
     */
    private int cacheIndex = 0;

    /**
     * Конструктор - создание нового объекта с определенным количеством точек, значения которых равны 0
     *
     * @param leftX      - левая граница области определения функции
     * @param rightX     - правая граница области определения функции
     * @param pointCount - количество точек функции
     * @throws InappropriateFunctionPointException - добавление точки несоотвествующим образом
     * @see ArrayTabulatedFunction#ArrayTabulatedFunction(double, double, int)
     */
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointCount) throws InappropriateFunctionPointException {
        if (rightX < leftX || pointCount < 2 || Math.abs(leftX - rightX) < Utils.EPS) {
            throw new IllegalArgumentException("Illegal argument: pointCount = " + pointCount + ", leftX = " + leftX
                    + ", rightX = " + rightX);
        }
        double length = (rightX - leftX) / (pointCount - 1.);
        countOfPoints = 2;
        firstHeadInit(new FunctionPoint(leftX, 0), new FunctionPoint(leftX + length, 0));
        for (int i = 2; i < pointCount; ++i) {
            addPoint(new FunctionPoint(leftX + length * i, 0));
        }
        cache = head.next;
    }

    /**
     * Конструктор - создаение нового объекта с определенными значениями функции
     *
     * @param leftX  - левая граница области определения функции
     * @param rightX - правая граница области определения функции
     * @param values - массив значенией функции
     * @throws InappropriateFunctionPointException - добавление точки несоотвествующим образом
     * @see ArrayTabulatedFunction#ArrayTabulatedFunction(double, double, double[])
     */
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        if (rightX < leftX || values.length < 2 || Math.abs(leftX - rightX) < Utils.EPS) {
            throw new IllegalArgumentException("Illegal argument: pointCount = " + values.length + ", leftX = " + leftX
                    + ", rightX = " + rightX);
        }
        double length = (rightX - leftX) / (values.length - 1.);
        countOfPoints = 2;
        firstHeadInit(new FunctionPoint(leftX, values[0]), new FunctionPoint(leftX + length, values[1]));
        for (int i = 2; i < values.length; ++i) {
            addPoint(new FunctionPoint(leftX + length * i, values[i]));
        }
        cache = head.next;
    }

    /**
     * Конструктор создающий копию функции из {@link ArrayTabulatedFunction} в виде списка
     *
     * @param arrayTabulatedFunction - копируемая функция
     * @throws InappropriateFunctionPointException - некорректное добавление точки
     * @see ArrayTabulatedFunction
     */
    public LinkedListTabulatedFunction(ArrayTabulatedFunction arrayTabulatedFunction) throws InappropriateFunctionPointException {
        countOfPoints = 2;
        firstHeadInit(new FunctionPoint(arrayTabulatedFunction.getPoint(0)), new FunctionPoint(arrayTabulatedFunction.getPoint(1)));
        for (int i = 2; i < arrayTabulatedFunction.getPointsCount(); ++i) {
            addPoint(new FunctionPoint(arrayTabulatedFunction.getPoint(i)));
        }
        cache = head.next;
    }

    /**
     * Метод позволяющий получить ноду по соотвествующему индексу
     *
     * @param index - индекс искомой ноды
     * @return - возвращает искомую ноду
     */
    private FunctionNode getNodeByIndex(int index) {
        isNotOutOfBounds(index);
        if (Math.abs(index - cacheIndex) < index && Math.abs(index - cacheIndex) < countOfPoints - index) {
            if (cacheIndex > index) {
                for (; index != cacheIndex; --cacheIndex) {
                    cache = cache.prev;
                }
            } else {
                for (; index != cacheIndex; ++cacheIndex) {
                    cache = cache.next;
                }
            }
        } else if (index < countOfPoints - index) {
            for (cacheIndex = 0, cache = head.next; cacheIndex != index; ++cacheIndex) {
                cache = cache.next;
            }
        } else {
            for (cacheIndex = countOfPoints - 1, cache = head.prev; cacheIndex != index; --cacheIndex) {
                cache = cache.prev;
            }
        }
        return cache;
    }

    /**
     * Метод добавляющий новый узел в конец списка
     *
     * @return - возвращает созданную ноду
     */
    private FunctionNode addNodeToTail() {
        ++countOfPoints;
        FunctionNode newNode = new FunctionNode(head.prev, new FunctionPoint(), head.next);
        head.next.prev = newNode;
        head.prev.next = newNode;
        head.prev = newNode;
        return newNode;
    }

    /**
     * Метод добавляющий новый узел в начало списка
     *
     * @return - возвращает созданную ноду
     */
    private FunctionNode addNodeToHead() {
        ++countOfPoints;
        FunctionNode newNode = new FunctionNode(head.prev, new FunctionPoint(), head.next);
        head.next.prev = newNode;
        head.prev.next = newNode;
        head.next = newNode;
        return newNode;
    }

    /**
     * Метод добавляющий новый узел в указанной позиции
     *
     * @return - возвращает созданную ноду
     */
    private FunctionNode addNodeByIndex(int index) {
        if (index == 0) return addNodeToHead();
        if (index == countOfPoints) return addNodeToTail();
        ++countOfPoints;
        FunctionNode oldNode = getNodeByIndex(index);
        FunctionNode newNode = new FunctionNode(oldNode.prev, new FunctionPoint(), oldNode);
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        return newNode;
    }

    /**
     * Метод удаляющий узел с указанным индексом
     * Так же стоит заметить, что у функции не может быть меньше двух точек
     *
     * @param index - номер удаляемой ноды
     * @throws IllegalStateException - указывает на невозможность удаления точки
     */
    private void deleteNodeByIndex(int index) {
        if (countOfPoints == 2) {
            throw new IllegalStateException("Can't remove the point, because function must have at least two points");
        }
        --countOfPoints;
        FunctionNode deletedNode = getNodeByIndex(index);
        if (index == 0) head.next = deletedNode.next;
        else if (index == countOfPoints - 1) head.prev = deletedNode.prev;
        deletedNode.prev.next = deletedNode.next;
        deletedNode.next.prev = deletedNode.prev;
    }

    /**
     * Первичная инициализация {@link LinkedListTabulatedFunction#head}
     *
     * @param first  - первая точка функции
     * @param second - вторая точка функции
     */
    private void firstHeadInit(FunctionPoint first, FunctionPoint second) {
        head = new FunctionNode();
        FunctionNode firstNode = new FunctionNode(null, first, null);
        FunctionNode secondNode = new FunctionNode(null, second, null);
        head.next = firstNode;
        head.prev = secondNode;
        firstNode.next = secondNode;
        firstNode.prev = secondNode;
        secondNode.next = firstNode;
        secondNode.prev = firstNode;
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
     * @return - возвращает левую границу области определения
     * @see ArrayTabulatedFunction#getLeftDomainBorder()
     */
    @Override
    public double getLeftDomainBorder() {
        return head.next.item.getX();
    }

    /**
     * @return - возвращает првую границу области определения
     * @see ArrayTabulatedFunction#getRightDomainBorder()
     */
    @Override
    public double getRightDomainBorder() {
        return head.prev.item.getX();
    }

    /**
     * @return - возвращает значение функции в точке х
     * @see ArrayTabulatedFunction#getFunctionValue(double)
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public double getFunctionValue(double x) {
        int i;
        if (this.head.next.item.getX() > x || this.head.prev.item.getX() < x) return Double.NaN;
        for (i = 1; i < this.countOfPoints && this.getPointX(i) < x; ++i) ;
        double leftX = this.getPointX(i - 1);
        double leftY = this.getPointY(i - 1);
        double rightX = this.getPointX(i - 1);
        double rightY = this.getPointY(i - 1);
        return ((rightY - leftY) * (x - leftX)) / (rightX - leftX) + leftY;
    }

    /**
     * @return - возвращает количество точек
     * @see ArrayTabulatedFunction#getPointsCount()
     */
    @Override
    public int getPointsCount() {
        return this.countOfPoints;
    }

    /**
     * @return - возвращает указанную индексом точку
     * @see ArrayTabulatedFunction#getPoint(int)
     */
    @Override
    public FunctionPoint getPoint(int index) {
        return getNodeByIndex(index).item;
    }

    /**
     * @see ArrayTabulatedFunction#setPoint(int, FunctionPoint)
     */
    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode temp = getNodeByIndex(index);
        if (temp.prev.item.getX() <= point.getX() && point.getX() <= temp.next.item.getX()) {
            temp.item = point;
        } else {
            throw new InappropriateFunctionPointException("Incorrect x = " + point.getX() +
                    " value. X must be in range (" + temp.prev.item.getX() + "; " + temp.next.item.getX() + ")");
        }
    }

    /**
     * @return - возвращает прообраз точки
     * @see ArrayTabulatedFunction#getPointX(int)
     */
    @Override
    public double getPointX(int index) {
        return getNodeByIndex(index).item.getX();
    }

    /**
     * @see ArrayTabulatedFunction#setPointX(int, double)
     */
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index == countOfPoints - 1 && head.prev.prev.item.getX() < x) {
            head.prev.item.setX(x);
            return;
        } else if (index == 0 && head.next.next.item.getX() > x) {
            head.next.item.setX(x);
            return;
        }
        FunctionNode temp = getNodeByIndex(index);
        getNodeByIndex(index).item.setX(x);
        if (temp.prev.item.getX() < x && x < temp.next.item.getX()) {
            temp.item.setX(x);
        } else {
            throw new InappropriateFunctionPointException("Incorrect x = " + x +
                    " value. X must be in range (" + temp.prev.item.getX() + "; " + temp.next.item.getX() + ")");
        }
    }

    /**
     * @see ArrayTabulatedFunction#getPointY(int)
     */
    @Override
    public double getPointY(int index) {
        return getNodeByIndex(index).item.getY();
    }

    /**
     * @see ArrayTabulatedFunction#setPointY(int, double)
     */
    @Override
    public void setPointY(int index, double y) {
        getNodeByIndex(index).item.setY(y);
    }

    /**
     * @see ArrayTabulatedFunction#deletePoint(int)
     */
    @Override
    public void deletePoint(int index) {
        deleteNodeByIndex(index);
    }

    /**
     * @see ArrayTabulatedFunction#addPoint(FunctionPoint)
     * @throws InappropriateFunctionPointException - неверное изменение точки
     */
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (head.prev.item.getX() < point.getX()) {
            addNodeToTail().item = new FunctionPoint(point);
        } else {
            throw new InappropriateFunctionPointException("Incorrect x = " + point.getX() +
                    " value. X must be bigger then: " + head.prev.item.getX());
        }
    }

    /**
     * @see ArrayTabulatedFunction#addPoint(int, FunctionPoint)
     */
    @Override
    public void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode temp = addNodeByIndex(index);
        if (index == countOfPoints && head.prev.item.getX() < point.getX()) {
            temp.item = point;
        }
        else if (index == 0 && head.next.item.getX() > point.getX()) {
            temp.item = point;
        }
        else if (temp.prev.item.getX() <= point.getX() && point.getX() <= temp.next.item.getX()) {
            temp.item = point;
        } else {
            throw new InappropriateFunctionPointException("Incorrect x = " + point.getX() +
                    " value. X must be in range (" + temp.prev.item.getX() + "; " + temp.next.item.getX() + ")");
        }
    }

    /**
     * @see ArrayTabulatedFunction#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        FunctionNode node = head.next;
        for (int i = 0; i < this.countOfPoints; ++i) {
            builder.append(i).append(": ").append(node.item.toString()).append('\n');
            node = node.next;
        }
        return builder.toString();
    }
}
