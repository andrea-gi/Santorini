package it.polimi.ingsw.PSP034.server;

/**
 * Main class for the Server side. If there are no parameters, it automatically creates a server application
 * listening on the default port (2020).
 * If the parameter specifies differently, it launches the application on the required port.
 */
public class ServerApp {
    /**
     * Starts the socket server, listening to the specified port (if possible). Default port is {@code 2020}.
     *
     * @param args Only single optional parameter: {@code PORT}. If the port is not valid, default {@code 2020} is used.
     */
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
