package functions;

/**
 * Класс реализующий точку в декартовой системе координат
 *
 * @author Artem Mukhin #6209
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FunctionPoint {
    /** Значение точки по оси OX (прообраз) */
    private double x;
    /** Значение точки по оси OY (образ) */
    private double y;

    /**
     * Конструктор по умолчанию - создает точку с координатами (0, 0)
     */
    public FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Конструктор создающий точку с координатами
     *
     * @param x - координата по оси ОХ
     * @param y - кооридната по оси ОY
     */
    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Конструктор создающий компию точки
     *
     * @param point - копируемая точка
     */
    public FunctionPoint(FunctionPoint point) {
        super();
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + " y = " + y;
    }
}