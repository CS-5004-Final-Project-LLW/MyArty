package Main;

public class DebugInfo implements Runnable {
    private int timeIntervalMill = 1000;
    private boolean running = false;

    @Override
    public void run() {
        while (true) {
            if (running) {
                printDebugInfo();
            }
            try {
                Thread.sleep(timeIntervalMill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printDebugInfo() {
        System.out.println(Info.getDebugInfo());
    }



}
