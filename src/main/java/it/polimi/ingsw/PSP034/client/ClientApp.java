package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.view.CLI.CLI;

public class ClientApp {
    public static void main(String[] args) {
        // TODO -- lanciare CLI o GUI
        RequestManager CLI = new CLI();
        Thread cliThread = new Thread((Runnable) CLI);
        cliThread.start();
    }
}
