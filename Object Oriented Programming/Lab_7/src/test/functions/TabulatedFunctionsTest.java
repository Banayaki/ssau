package functions;

import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.exceptions.InappropriateFunctionPointException;
import functions.meta.Power;
import functions.meta.Sum;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class TabulatedFunctionsTest {

    @Test
    public void tabulate() throws InappropriateFunctionPointException {
        System.out.println(TabulatedFunctions.tabulate(new Sin(), 0, 2 * Math.PI, 11).toString());
        System.out.println(TabulatedFunctions.tabulate(new Cos(), 0, 2 * Math.PI, 11).toString());
        System.out.println(TabulatedFunctions.tabulate(new Sum(new Power(new Sin(), 2), new Power(new Cos(), 2)), 0, 2 * Math.PI, 11).toString());
    }

    @Test
    public void outputTabulatedFunction() throws FileNotFoundException, InappropriateFunctionPointException {
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
    public void writerTabulatedFunction() throws IOException, InappropriateFunctionPointException {
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
    public void serializableTabulatedFunction() throws IOException, ClassNotFoundException, InappropriateFunctionPointException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serial.txt"));
        TabulatedFunctionImpl f = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
        out.writeObject(f);
        out.close();
        System.out.println(f.toString());
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("serial.txt"));
        f = (TabulatedFunctionImpl) in.readObject();
        System.out.println(f.toString());

    }
}
