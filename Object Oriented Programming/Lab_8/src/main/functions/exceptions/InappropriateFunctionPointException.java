package functions.exceptions;

/**
 * Исключение выбрасываемое при попытке добавления или изменения точки функции несоответсвтующим образом
 */
public class InappropriateFunctionPointException extends Exception {

    public InappropriateFunctionPointException(String msg) {
        super(msg);
    }
}
