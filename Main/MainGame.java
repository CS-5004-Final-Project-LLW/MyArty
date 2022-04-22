package Main;

import javax.swing.JFrame;

public class MainGame {
    public static void main(String[] args) {
        int width = 1000;
        int height = 800;
        int fps = 60;
        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setContentPane(new GUI(width, height, fps));
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);
        mainWindow.pack();
    }
}
