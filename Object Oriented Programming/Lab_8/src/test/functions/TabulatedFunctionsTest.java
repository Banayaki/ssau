package functions;

import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Power;
import functions.meta.Sum;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class TabulatedFunctionsTest {

    @Test
    public void tabulate() {
        System.out.println(TabulatedFunctions.tabulate(new Sin(), 0, 2 * Math.PI, 11).toString());
        System.out.println(TabulatedFunctions.tabulate(new Cos(), 0, 2 * Math.PI, 11).toString());
        System.out.println(TabulatedFunctions.tabulate(new Sum(new Power(new Sin(), 2), new Power(new Cos(), 2)), 0, 2 * Math.PI, 11).toString());
    }

    @Test
    public void outputTabulatedFunction() throws FileNotFoundException {
        String strForCheck;

        TabulatedFunctionImpl logFunc = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
        strForCheck = logFunc.toString();
        TabulatedFunctions.outputTabulatedFunction(logFunc, new FileOutputStream("output.txt"));
        logFunc = TabulatedFunctions.inputTabulatedFunction(new FileInputStream("output.txt"));
        assert logFunc != null;
        // Не пройдет почти никогда.
        assertEquals(strForCheck, logFunc.toString());
    }

    @Test
    public void writerTabulatedFunction() throws IOException {
        String strForCheck;

        TabulatedFunctionImpl expFunc = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
        strForCheck = expFunc.toString();
        TabulatedFunctions.writeTabulatedFunction(expFunc, new FileWriter("writer.txt"));
        expFunc = TabulatedFunctions.readTabulatedFunction(new FileReader("writer.txt"));
        assert expFunc != null;
        // Не пройдет почти никогда.
        assertEquals(strForCheck, expFunc.toString());
    }

    @Test
    public void serializableTabulatedFunction() throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serial.txt"));
        TabulatedFunctionImpl f = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
        out.writeObject(f);
        out.close();
        System.out.println(f.toString());
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("serial.txt"));
        f = (TabulatedFunctionImpl) in.readObject();
        System.out.println(f.toString());
    }

    @Test
    public void factoryTest() {
        try {
            FunctionImpl function = new Cos();
            TabulatedFunctionImpl tabulatedFunction;
            tabulatedFunction = TabulatedFunctions.tabulate(function, 0, Math.PI, 11);
            System.out.println(tabulatedFunction.getClass().getName());
            TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.ListTabFuncFactory());
            tabulatedFunction = TabulatedFunctions.tabulate(function, 0, Math.PI, 11);
            System.out.println(tabulatedFunction.getClass().getName());
            TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabFuncFactory());
            tabulatedFunction = TabulatedFunctions.tabulate(function, 0, Math.PI, 11);
            System.out.println(tabulatedFunction.getClass().getName());

            System.out.println("---------------------------------------------------------------------");

            tabulatedFunction = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class,
                    0, Math.PI, 11);
            System.out.println(tabulatedFunction);
            System.out.println(tabulatedFunction.getClass().getName());

            tabulatedFunction = TabulatedFunctions.createTabulatedFunction(LinkedListTabulatedFunction.class,
                    0, Math.PI, new double[]{10, 20, 30});
            System.out.println(tabulatedFunction);
            System.out.println(tabulatedFunction.getClass().getName());

            tabulatedFunction = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class,
                    new FunctionPoint[]{new FunctionPoint(0, 0), new FunctionPoint(1, 1)});
            System.out.println(tabulatedFunction);
            System.out.println(tabulatedFunction.getClass().getName());

            System.out.println("---------------------------------------------------------------------");
            TabulatedFunctionImpl f;

            f = TabulatedFunctions.createTabulatedFunction(
                    ArrayTabulatedFunction.class, 0, 10, 3);
            System.out.println(f.getClass());
            System.out.println(f);

            f = TabulatedFunctions.createTabulatedFunction(
                    ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10});
            System.out.println(f.getClass());
            System.out.println(f);

            f = TabulatedFunctions.createTabulatedFunction(
                    LinkedListTabulatedFunction.class,
                    new FunctionPoint[]{
                            new FunctionPoint(0, 0),
                            new FunctionPoint(10, 10)
                    }
            );
            System.out.println(f.getClass());
            System.out.println(f);

            f = TabulatedFunctions.tabulate(
                    LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
            System.out.println(f.getClass());
            System.out.println(f);

        } catch (Throwable throwable) {
            System.err.println(throwable.getMessage());
        }
    }
}
