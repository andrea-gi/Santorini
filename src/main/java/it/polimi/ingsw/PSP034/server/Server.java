package it.polimi.ingsw.PSP034.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(12);
    private final Map<String, IClientConnection> waitingConnections = new HashMap<>();
    private final ArrayList<IClientConnection> activeConnections = new ArrayList<>();

    public Server(int port){
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e){
            System.out.println("Cannot open server socket");
            System.exit(1);
        }
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                ClientHandler socketConnection = new ClientHandler(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
