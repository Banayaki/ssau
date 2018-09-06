package functions;

import functions.exceptions.InappropriateFunctionPointException;
import org.junit.*;

import static org.junit.Assert.assertEquals;


@SuppressWarnings("WeakerAccess")
public class LinkedListTabulatedFunctionTest {
    ArrayTabulatedFunction test = new ArrayTabulatedFunction(0, 10, new double[]{10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
    LinkedListTabulatedFunction first = new LinkedListTabulatedFunction(0, 10, 5);
    LinkedListTabulatedFunction second = new LinkedListTabulatedFunction(0, 10, new double[]{
            0.0, 0.2, 0.5, 0.9, 1.5, 5.0, 11.0, 25.0, 35.0, 909.0, 0.0, 1.1
    });
    LinkedListTabulatedFunction third = new LinkedListTabulatedFunction(test);

    public LinkedListTabulatedFunctionTest() throws InappropriateFunctionPointException {

    }

    @Test
    public void getTests() throws InappropriateFunctionPointException {
        assertEquals(5, first.getPointsCount());
        assertEquals(12, second.getPointsCount());
        assertEquals(6, third.getPointsCount());
        assertEquals(10, first.getRightDomainBorder(), 0.0);
        assertEquals(0, first.getLeftDomainBorder(), 0.0);
        assertEquals(8.0, third.getPoint(4).getX(), 0.0);
        assertEquals(50.0, third.getPoint(4).getY(), 0.0);
        assertEquals(8.0, third.getPointX(4), 0.0);
        assertEquals(50.0, third.getPointY(4), 0.0);
    }

    @Test
    public void setTests() throws InappropriateFunctionPointException {
        FunctionPoint point = new FunctionPoint(8, 999);
        first.setPoint(3, point);
        point.setY(0);
        assertEquals(8, first.getPointX(3), 0.0);
        assertEquals(0, first.getPointY(3), 0.0);
        first.setPointX(2, 6);
        first.setPointY(2, 10);
        assertEquals(6, first.getPointX(2), 0.0);
        assertEquals(10, first.getPointY(2), 0.0);
    }

    @Test
    public void addDeleteTest() throws InappropriateFunctionPointException {
        second.addPoint(new FunctionPoint(50, 100));
        second.addPoint(new FunctionPoint(100, 500));
        assertEquals(14, second.getPointsCount());
        second.addPoint(0, new FunctionPoint(-1, 0));
        second.addPoint(4, new FunctionPoint(2, 10));
        assertEquals(0, second.getPointY(0), 0.0);
        assertEquals(2, second.getPointX(4), 0.0);
        second.deletePoint(0);
        assertEquals(15, second.getPointsCount());
        assertEquals(0.0, second.getPointX(0), 0.0);
    }

    public void print() {
        System.out.println(first.toString());
        System.out.println(second.toString());
        System.out.println(third.toString());
        System.out.println("0-----------------------------0");
    }
}
