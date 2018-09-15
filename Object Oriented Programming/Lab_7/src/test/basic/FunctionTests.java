package basic;

import functions.basic.*;
import org.junit.Test;

public class FunctionTests {

    @Test
    public void Cos() {
        Cos cosinus = new Cos();
        System.out.println(Cos.class.getName());
        System.out.println(cosinus.getLeftDomainBorder());
        System.out.println(cosinus.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.5) {
            System.out.println(cosinus.getFunctionValue(i));
        }
        System.out.println();
    }

    @Test
    public void Sin() {
        Sin sinus = new Sin();
        System.out.println(Sin.class.getName());
        System.out.println(sinus.getLeftDomainBorder());
        System.out.println(sinus.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            System.out.println(sinus.getFunctionValue(i));
        }
        System.out.println();
    }

    @Test
    public void Exp() {
        Exp exp = new Exp();
        System.out.println(Exp.class.getName());
        System.out.println(exp.getLeftDomainBorder());
        System.out.println(exp.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.5) {
            System.out.println(exp.getFunctionValue(i));
        }
        System.out.println();
    }

    @Test
    public void Log() {
        Log log = new Log(10);
        System.out.println(Log.class.getName());
        System.out.println(log.getLeftDomainBorder());
        System.out.println(log.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.5) {
            System.out.println(log.getFunctionValue(i));
        }
        System.out.println();
    }

    @Test
    public void Tan() {
        Tan tan = new Tan();
        System.out.println(Tan.class.getName());
        System.out.println(tan.getLeftDomainBorder());
        System.out.println(tan.getRightDomainBorder());
        for (double i = 0; i < 2 * Math.PI; i += 0.5) {
            System.out.println(tan.getFunctionValue(i));
        }
        System.out.println();
    }
}
