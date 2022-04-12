package Main;

import java.util.Scanner;

public class Main {
    API gameAPI;
    Scanner scanner = new Scanner(System.in);

    public Main() {
        start();
    }

    private void start() {
        restart(null);
    }

    private void restart(String[] inputs) {
        gameAPI = new API();
    }

    private void shoot(String[] inputs) {
        if (inputs.length != 3) {
            wrongInput(inputs);
            return;
        }
        double angleDegree, powerPercentage;
        try {
            angleDegree = Double.parseDouble(inputs[1]);
            powerPercentage = Double.parseDouble(inputs[2]);
        } catch (NullPointerException | NumberFormatException e) {
            wrongInput(inputs);
            return;
        }
        gameAPI.shoot(angleDegree, powerPercentage);
    }

    private void wrongInput(String[] inputs) {
        System.out.println("Wrong input.");
    }


    public static void main(String[] args) {
        Main theMain = new Main();

        while (true) {
            String input = theMain.scanner.nextLine();
            String[] inputs = input.split(" ");

            switch (inputs[0]) {
                case "s":
                    theMain.shoot(inputs);
                    break;
                case "r":
                    theMain.restart(inputs);
                    break;
                default:
                    theMain.wrongInput(inputs);
                    break;
                case "e":
                    System.exit(0);
                    break;
            }
        }
    }
}
