package ca.ulaval.glo2004;


import javax.swing.*;

public class App {

    public static void main(String[] args) {
        ca.ulaval.glo2004.gui.MainWindow mainWindow = new ca.ulaval.glo2004.gui.MainWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
    }
}

