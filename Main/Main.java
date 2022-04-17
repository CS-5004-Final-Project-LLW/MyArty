package Main;

import java.util.Locale;
import java.util.Scanner;

/**
 * The entrance of the program
 */
public class Main {

    // for game control
    API gameAPI;
    // for user input
    Scanner scanner = new Scanner(System.in);

    public boolean isSkip = false;

    public Main() {
        start();
    }

    private void start() {
        // Request username input
        System.out.println("Please enter the user name: ");
        String userName = scanner.nextLine();
        gameAPI = new API(userName);
    }

    private void restart(String[] inputs) {
        gameAPI = new API(gameAPI.getUserName());
    }

    private void shoot(String[] inputs) {
        if (inputs.length != 4) {
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
            gameAPI.shoot(angleDegree, powerPercentage, inputs[3]);
    }

    // wrong input promot user to re-entry
    private void wrongInput(String[] inputs) {
        System.out.println("Wrong input.");
    }

    private void exitGame() {
        System.out.println("Thank you for playing!");
        System.exit(0);
    }

    public static void main(String[] args) {

        Main theMain = new Main();  

        while (true) {
            // prompt user input and display game instructions
            System.out.println("Game instructions: ");
            System.out.println("* Exit: enter \"e\" or \"exit\" to exit the game");
            System.out.println("* Restart: enter \"r\" or \"restart\" to restart the game");
            System.out.println(
                    "* Shoot: enter \"s\" angle power or just angle power (separated by space) to start to play! ");
            System.out.println("* Angle range from 0 to 90 degree.");
            System.out.println("* Shooting power range from 0 to 100.");
            System.out.println("* Add \"y\" or \"Y\"to skip animation, if not, just ignore");


            // read a line
            String input = theMain.scanner.nextLine();

            // split line by space
            String[] inputs = input.split(" ");

            if (inputs[0].toLowerCase(Locale.ENGLISH).equals("e")
                    || inputs[0].toLowerCase(Locale.ENGLISH).equals("exit")) {
                // Exit case, handles both "e" and "exit" not case sensitive
                theMain.exitGame();

            } else if (inputs[0].toLowerCase(Locale.ENGLISH).equals("r")
                    || inputs[0].toLowerCase(Locale.ENGLISH).equals("restart")) {
                // Restart case handles both "r" and "restart" not case sensitive
                theMain.restart(inputs);

            } else if (inputs.length == 2) {
                // shoot case with only two user input angle and power.
                // Number parsing error is handled inside the shoot method
                String[] newInputs = new String[4];
                newInputs[0] = "s";
                newInputs[1] = inputs[0];
                newInputs[2] = inputs[1];
                newInputs[3] = "n";
                theMain.shoot(newInputs);

            } else if (inputs.length == 3 && inputs[0].toLowerCase(Locale.ENGLISH).equals("s")) {
                // Normal shoot case contains 3 inputs with the first one being "s"
                // the rest are angle and power
                String[] newInputs2 = new String[4];
                newInputs2[0] = inputs[0];
                newInputs2[1] = inputs[1];
                newInputs2[2] = inputs[2];
                newInputs2[3] = "n";
                theMain.shoot(newInputs2);
        
            } else if (inputs.length == 4 && inputs[0].toLowerCase(Locale.ENGLISH).equals("s")) {
            // Normal shoot case contains 4 inputs with the first one being "s" and last ont being "y"
            // the rest are angle and power
                // if("y".equals(inputs[3])){
                //     isSkip = false;
                // }
                // theMain.setisSkip(true);
                theMain.shoot(inputs);
                
            } else {
                // Wrong answer, simply ask user to re-input
                theMain.wrongInput(inputs);

            }
        }
            
    }
}
