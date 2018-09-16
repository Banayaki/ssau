package threads;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;
    private String params;

    public Integrator(Task task, Semaphore semaphore, String params) {
        this.task = task;
        this.params = params;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Run Integrator, with params:\n" + params);
        double result = task.work();
        semaphore.release();
        System.out.println("Result: {" + result + "}");
    }
}
