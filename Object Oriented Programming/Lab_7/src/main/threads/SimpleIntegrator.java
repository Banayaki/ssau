package threads;

public class SimpleIntegrator implements Runnable {
    private Task task;
    private String params;

    public SimpleIntegrator(Task task, String params) {
        this.task = task;
        this.params = params;
    }

    @Override
    public void run() {
        System.out.println("Run Integrator, with params:\n" + params);
        double result = task.work();
        System.out.println("Result: {" + result + "}");
    }
}
