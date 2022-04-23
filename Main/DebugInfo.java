package Main;

public class DebugInfo implements Runnable {
    private int timeIntervalMill = 1000;
    private static boolean running = false;

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
        System.out.println();
        System.out.println(Info.getDebugInfo());

        StringBuffer sb = new StringBuffer();
        for (int temp : Info.getSleepTimes()) {
            sb.append(temp);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    public static boolean isRunning() {
        return running;
    }



}
