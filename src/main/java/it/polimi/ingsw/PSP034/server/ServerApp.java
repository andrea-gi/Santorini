package it.polimi.ingsw.PSP034.server;

public class ServerApp {
    public static void main(String[] args) {
        ServerLogger.getInstance().setPrintStreams(System.out);
        int parsedArgs = -1;
        if (args.length > 0) {
            try {
                parsedArgs = Integer.parseInt(args[0]);
            } catch (Exception e){
                ServerLogger.getInstance().printString(args[0] + " is not a valid port. Using the default port (2020)");
            }
        }
        parsedArgs = (parsedArgs >= 0 && parsedArgs <= 65535) ? parsedArgs : 2020;
        Server server = new Server(parsedArgs);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
