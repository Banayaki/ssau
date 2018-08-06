package functions.exceptions;

/**
 *  Исключение выхода за границы набора точек при обращении к ним по номеру
 *
 * @see IndexOutOfBoundsException
 */
public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {

    public FunctionPointIndexOutOfBoundsException(String msg) {
        super(msg);
    }
}
