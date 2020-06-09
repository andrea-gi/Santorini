package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.HeartBeatAnswer;
import it.polimi.ingsw.PSP034.messages.HeartBeatRequest;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

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
            socket.close();
        } catch (IOException ignored){
        }finally {
            if (futureHeartBeat != null)
                futureHeartBeat.cancel(true);
            if (!silentEnded) {
                requestManager.showError(new ErrorMessage("C002", "Connection error. Could not receive message."));
            }
        }
    }

    private final ScheduledThreadPoolExecutor waitingHeartBeat = new ScheduledThreadPoolExecutor(1);

    private ScheduledFuture<Boolean> waitHeartBeat(){
        return waitingHeartBeat.schedule(()->
        {
            silentEnded = true;
            clientGameHandler.setSilentEnded(true);
            closeStreams();
            requestManager.showError(new ErrorMessage("S002", "Server is not responding."));
            return false;
        }, 10, TimeUnit.SECONDS);
    }

    private ScheduledFuture<Boolean> futureHeartBeat;

    @Override
    public void run() {
        boolean canManageMessages = true;
        while(true){
            try{
                Object receivedMessage = in.readObject();
                if (receivedMessage instanceof Request && canManageMessages){
                    if (receivedMessage instanceof HeartBeatRequest){
                        if (futureHeartBeat != null)
                            futureHeartBeat.cancel(true);
                        clientGameHandler.send(new HeartBeatAnswer());
                        futureHeartBeat = waitHeartBeat();
                    } else {
                        requestQueue.offer((Request) receivedMessage); //TODO -- cosa succede se supero la capacità?
                        if (Request.isSilentCloseRequest((Request) receivedMessage)) {
                            this.silentEnded = true;
                            clientGameHandler.setSilentEnded(true);
                        }
                    }
                }
            }
            catch (IOException | ClassNotFoundException e){
                //requestQueue.clear();
                canManageMessages = false;
                closeStreams();
            }
            if(clientEnded)
                return;
        }
    }
}
