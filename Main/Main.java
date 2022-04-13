package Main;

import java.util.Scanner;

/**
 * The entrance of the program
 */
public class Main {
    // for game control
    API gameAPI;
    // for user input
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
            // wrong input if the input does not contain exactly 3 words
            wrongInput(inputs);
            return;
        }
        double angleDegree, powerPercentage;
        try {
            // read angle
            angleDegree = Double.parseDouble(inputs[1]);
            // read power
            powerPercentage = Double.parseDouble(inputs[2]);
        } catch (NullPointerException | NumberFormatException e) {
            // wrong input
            wrongInput(inputs);
            return;
        }
        // perform shooting
        gameAPI.shoot(angleDegree, powerPercentage);
    }

    // wrong input promot user to re-entry 
    private void wrongInput(String[] inputs) {
        System.out.println("Wrong input.");
    }


    public static void main(String[] args) {
        Main theMain = new Main();

        while (true) {
            // read a line
            String input = theMain.scanner.nextLine();
            // split line by space
            String[] inputs = input.split(" ");

            switch (inputs[0]) {
                case "s":
                    // shoot
                    theMain.shoot(inputs);
                    break;
                case "r":
                    // restart
                    theMain.restart(inputs);
                    break;
                default:
                    // wrong input
                    theMain.wrongInput(inputs);
                    break;
                case "e":
                    // exit
                    System.exit(0);
                    break;
            }
        }
    }
}
