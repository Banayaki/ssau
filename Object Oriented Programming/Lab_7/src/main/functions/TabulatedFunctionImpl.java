package functions;

import functions.exceptions.InappropriateFunctionPointException;

/**
 * Интерфейс, определяющий поведение табулированной функции
 *
 * @see FunctionImpl
 */
public interface TabulatedFunctionImpl extends FunctionImpl, Cloneable {

    /**
     * Метод получения количества точек
     *
     * @return - возвращает количество точек табултируемой функции
     */
    int getPointsCount();

    /**
     * Метод получения точки по её номеру
     *
     * @param index номер точки, которую хотим получить
     * @return возвращает искомую точку
     */
    FunctionPoint getPoint(int index);

    /**
     * Метод заменяющий определенную точку на новую
     *
     * @param index - номер точки, которую хотим изменить
     * @param point - точка, на которую хотим заменить
     * @throws InappropriateFunctionPointException - изменение точки несоотвествующим образом
     */
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    /**
     * Метод возвращающий значение точки по ОХ.
     *
     * @param index - индекс нужной точки
     * @return - возвращает знаение прообраза точки
     */
    double getPointX(int index);

    /**
     * Метод позволяющий изменить значение прообраза точки
     *
     * @param index - номер изменяемой точки
     * @param x     - новое значение прообраза
     * @throws InappropriateFunctionPointException - изменение точки несоотвествующим образом
     */
    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    /**
     * Метод вовзращающий значение точки по ОY
     *
     * @param index - номер нужной точки
     * @return - возвращает значение образа выбранной точки
     */
    double getPointY(int index);

    /**
     * Метод позволяющий изменить значение образа точки
     *
     * @param index - номер изменяемой точки
     * @param y     - новое значение образа
     */
    void setPointY(int index, double y);

    /**
     * Метод, который удаляет точку
     *
     * @param index - номер удаляемой точки
     */
    void deletePoint(int index);

    /**
     * Добавить точку в в конец функции
     *
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
     */
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    /**
     * Добавление точки в определенное место
     *
     * @param index - место в которую нужно вставить точку
     * @param point - новая точка
     * @throws InappropriateFunctionPointException - добавление точки несоответствующим образом
     */
    void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
}
