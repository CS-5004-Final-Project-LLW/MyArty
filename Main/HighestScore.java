package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HighestScore {
    private int highestScore = 0;
    private final String fileName = "highest_score.log";

    HighestScore() {
        read();
    }

    private void read() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Scanner scan = new Scanner(new FileInputStream(fileName))) {
                    highestScore = Integer.parseInt(scan.next());
                } catch (FileNotFoundException e) {
                    createFile();
                } catch (NoSuchElementException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void write() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (FileWriter output = new FileWriter(fileName, false)) {
                    output.write("%d".formatted(highestScore));
                } catch (FileNotFoundException e) {
                    createFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void createFile() {
        // TODO createFile();
    }

    void set(int highestScore) {
        this.highestScore = highestScore;
        write();
    }

    public int get() {
        return highestScore;
    }

}
