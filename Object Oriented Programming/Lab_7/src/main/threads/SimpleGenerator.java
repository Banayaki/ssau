package threads;

import functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable {
    private Task task;
    private int countOfTasks;

    public SimpleGenerator(Task task, int countOfTasks) {
        this.task = task;
        this.countOfTasks = countOfTasks;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run() {
        Random generator = new Random();
        for (int i = 0; i < this.countOfTasks; ++i) {
            if (Thread.currentThread().isInterrupted()) {
                System.err.println("Thread was interrupted");
                break;
            }
            double base = Math.abs(generator.nextGaussian() * 10);
            task.setFunction(new Log(base));
            task.setLeftBorder(generator.nextDouble() * 100);
            task.setRightBorder(generator.nextDouble() * 100 + 100);
            task.setSamplingStep(generator.nextDouble());
            System.out.println("Run Generator, test #" + i);
            String testParams = "Source: {LeftBorder = " + task.getLeftBorder() + ", RightBorder = " + task.getRightBorder() +
                    ", SamplingStep = " + task.getSamplingStep() + ", base = " + base + "}";
            System.out.println(testParams);
            SimpleIntegrator integrator = new SimpleIntegrator(task, testParams);

            Thread thread = new Thread(integrator);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
