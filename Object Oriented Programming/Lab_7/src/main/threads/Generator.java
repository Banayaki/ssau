package threads;

import functions.basic.Log;

import java.util.Random;
import java.util.concurrent.Semaphore;

@SuppressWarnings("Duplicates")
public class Generator extends Thread {
    private Semaphore semaphore;
    private Task task;
    private int countOfTasks;

    public Generator(Task task, Semaphore semaphore, int countOfTasks) {
        this.task = task;
        this.semaphore = semaphore;
        this.countOfTasks = countOfTasks;
    }

    @Override
    public void run() {
        Random generator = new Random();
        for (int i = 0; i < this.countOfTasks; ++i) {
            if (this.isInterrupted()) {
                System.err.println("Thread was interrupted");
                break;
            }
            try {
                semaphore.acquire();

                double base = Math.abs(generator.nextGaussian() * 10);
                task.setFunction(new Log(base));
                task.setLeftBorder(generator.nextDouble() * 100);
                task.setRightBorder(generator.nextDouble() * 100 + 100);
                task.setSamplingStep(generator.nextDouble());
                System.out.println("Run Generator, test #" + i);
                String testParams = "Source: {LeftBorder = " + task.getLeftBorder() + ", RightBorder = " + task.getRightBorder() +
                        ", SamplingStep = " + task.getSamplingStep() + ", base = " + base + "}";
                System.out.println(testParams);
                Integrator integrator = new Integrator(task, semaphore, testParams);

                integrator.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
