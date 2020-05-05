package it.polimi.ingsw.PSP034.server;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server(2020);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
