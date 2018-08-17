package functions;

import org.junit.*;
import static org.junit.Assert.*;


@SuppressWarnings("WeakerAccess")
public class FunctionPointTest {
    FunctionPoint copyFromNotEmptyPoint;
    FunctionPoint copyFromEmptyPoint;
    FunctionPoint notEmptyPoint;
    FunctionPoint emptyPoint;

    @Before
    public void init() {
        emptyPoint = new FunctionPoint();
        notEmptyPoint = new FunctionPoint(8,8);
        copyFromEmptyPoint = new FunctionPoint(emptyPoint);
        copyFromNotEmptyPoint = new FunctionPoint(notEmptyPoint);
        copyFromNotEmptyPoint.setX(9);
        copyFromNotEmptyPoint.setY(10);
    }

    @Test
    public void getX() throws CloneNotSupportedException {
        assertEquals(0, emptyPoint.getX(), 0.0);
        assertEquals(8, notEmptyPoint.getX(), 0.0);
        assertNotNull(copyFromEmptyPoint);
        assertEquals(0, copyFromEmptyPoint.getX(), 0.0);
        assertEquals(9, copyFromNotEmptyPoint.getX(), 0.0);
    }

    @Test
    public void getY() {
        assertEquals(0, emptyPoint.getY(), 0.0);
        assertEquals(8, notEmptyPoint.getY(), 0.0);
        assertNotNull(copyFromEmptyPoint);
        assertEquals(0, copyFromEmptyPoint.getY(), 0.0);
        assertEquals(10, copyFromNotEmptyPoint.getY(), 0.0);
    }

    @Test
    public void setX() {
        emptyPoint.setX(999);
        assertEquals(999, emptyPoint.getX(), 0.0);
    }

    @Test
    public void setY() {
        emptyPoint.setY(900);
        assertEquals(900, emptyPoint.getY(), 0.0);
    }

    @Test
    public void copyTest() throws CloneNotSupportedException {
        FunctionPoint fp = (FunctionPoint) notEmptyPoint.clone();
        assertEquals(fp, notEmptyPoint);
        fp.setX(999);
        assertNotEquals(fp,notEmptyPoint);
        System.out.println(fp.toString());
    }
}
