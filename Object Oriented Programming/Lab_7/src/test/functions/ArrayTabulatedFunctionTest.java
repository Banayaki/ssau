package functions;

import functions.exceptions.InappropriateFunctionPointException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@SuppressWarnings("WeakerAccess")
public class ArrayTabulatedFunctionTest {
    ArrayTabulatedFunction first = new ArrayTabulatedFunction(0, 10e-5, 1000);
    ArrayTabulatedFunction second = new ArrayTabulatedFunction(0, 1, 5);
    ArrayTabulatedFunction third = new ArrayTabulatedFunction(0, 10, new double[]{ 10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
    ArrayTabulatedFunction fourth = new ArrayTabulatedFunction(-10, 10, 2);
    ArrayTabulatedFunction fifth = new ArrayTabulatedFunction(-100, 100, 20);

    @Test
    public void getLeftDomainBorder() {
        assertEquals(0, first.getLeftDomainBorder(), Utils.EPS);
        assertEquals(-10, fourth.getLeftDomainBorder(), Utils.EPS);
        assertEquals(-100, fifth.getLeftDomainBorder(), Utils.EPS);
    }

    @Test
    public void getRightDomainBorder() {
        assertEquals(10e-5, first.getRightDomainBorder(), Utils.EPS);
        assertEquals(1, second.getRightDomainBorder(), Utils.EPS);
        assertEquals(10, third.getRightDomainBorder(), Utils.EPS);
    }

    @Test
    public void getFunctionValue() {
        assertEquals(12.5, third.getFunctionValue(1), Utils.EPS);
        assertEquals(15, third.getFunctionValue(2), Utils.EPS);
        assertEquals(17.5, third.getFunctionValue(3), Utils.EPS);
        assertEquals(25, third.getFunctionValue(5), Utils.EPS);
        assertNotEquals(third.getFunctionValue(9.735), Double.NaN, 0.0);
    }

    @Test
    public void getPointsCount() {
        assertEquals(1000, first.getPointsCount());
        assertEquals(6, third.getPointsCount());
    }

    @Test
    public void getPoint() {
        assertEquals(0, first.getPoint(999).getY(), Utils.EPS);
        assertEquals(30.0, third.getPoint(3).getY(), Utils.EPS);
    }

    @Test
    public void setPoint() throws InappropriateFunctionPointException {
        third.setPoint(2, new FunctionPoint(2.25, 1000));
        assertEquals(1000, third.getPoint(2).getY(), Utils.EPS);
    }

    @Test
    public void addPoint() throws InappropriateFunctionPointException {
        third.addPoint(new FunctionPoint(20, -10));
        assertEquals(7, third.getPointsCount());
        assertEquals(45.0, third.getFunctionValue(15), Utils.EPS);
    }

    @Test
    public void deletePoint() {
        third.deletePoint(5);
        assertEquals(20.0, third.getPointY(2), Utils.EPS);
        assertEquals(5, third.getPointsCount());
        System.out.println(third.toString());
    }

    @Test
    public void copyTest() throws CloneNotSupportedException, InappropriateFunctionPointException {
        TabulatedFunctionImpl copy = (TabulatedFunctionImpl) second.clone();
        assertEquals(copy, second);
        second.addPoint(new FunctionPoint(10, 10));
        assertNotEquals(copy, second);
        System.out.println(second.toString());
        System.out.println(copy.toString());
    }
}