package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.AutoCloseRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.EndByDisconnection;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.PersonalDefeatRequest;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.WinnerRequest;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.RequestServerConfig;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.ServerInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable{
    private String address;
    private int socketPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private final RequestManager requestManager;

    private Thread clientGameHandlerThread;
    private ClientGameHandler clientGameHandler;

    private boolean clientEnded = false;
    private boolean silentEnded = false;

    private final BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(64);

    public Client(RequestManager requestManager, String address, int socketPort){
        this.requestManager = requestManager;
        this.address = address;
        this.socketPort = socketPort;
    }

    //TODO -- bisogna prima chiamare questo metodo e poi avviare il thread
    public boolean startConnection(){
        try {
            socket = new Socket(address, socketPort);
            if (!socket.isClosed() && !socket.isConnected())
                return false;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientGameHandler = new ClientGameHandler(requestManager, requestQueue, out);
            clientGameHandlerThread = new Thread(clientGameHandler);
            clientGameHandlerThread.start();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    private void closeStreams(){
        try {
            clientEnded = true;
            in.close();
            if (!silentEnded)
                requestQueue.offer(new AutoCloseRequest());
        } catch (IOException ignored){
        }
    }

    @Override
    public void run() {
        boolean canManageMessages = true;
        while(true){
            try{
                Object receivedMessage = in.readObject();
                if (receivedMessage instanceof Request && canManageMessages){
                    requestQueue.put((Request) receivedMessage);
                    if (Request.isSilentCloseRequest((Request) receivedMessage)){
                        this.silentEnded = true;
                        clientGameHandler.setSilentEnded(true);
                    }
                } //else if PING
            }
            catch (IOException | ClassNotFoundException | InterruptedException e){
                //requestQueue.clear();
                canManageMessages = false;
                closeStreams();
            }
            if(clientEnded)
                return;
        }
    }
}
