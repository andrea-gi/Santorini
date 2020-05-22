package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.view.CLI.CLI;
import it.polimi.ingsw.PSP034.view.GUI.MainGUI;
import javafx.application.Application;

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
