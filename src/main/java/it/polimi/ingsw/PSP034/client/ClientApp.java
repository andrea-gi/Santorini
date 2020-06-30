package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.view.CLI.CLI;
import it.polimi.ingsw.PSP034.view.GUI.MainGUI;
import javafx.application.Application;

/**
 * Main class for the Client side. If there are no parameters, it automatically launches the GUI application.
 * (JavaFX)
 * If the parameter specifies {@code CLI} (case insensitive), it launches the CLI application.
 * Requires terminal with full ANSI, ANSI-escape and UTF-8 support.
 */
public class ClientApp {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
            RequestManager CLI = new CLI();
            Thread cliThread = new Thread((Runnable) CLI);
            cliThread.start();
        }
        else {
            Application.launch(MainGUI.class);
        }
    }
}
