package it.polimi.ingsw.PSP034.messages.clientConfiguration;

/**
 * Message internal to the client to select the address of the server to connect to.
 */
public class AnswerIP extends AnswerClientConfig{
    static final long serialVersionUID = 98112518520L;
    private final String ip;
    private final int port;

    /**
     * Initializes the message with the desired values.
     * @param ip IP address of the server.
     * @param port port of of the server.
     */
    public AnswerIP(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
