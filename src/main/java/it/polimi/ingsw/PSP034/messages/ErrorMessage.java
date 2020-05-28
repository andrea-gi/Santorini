package it.polimi.ingsw.PSP034.messages;

public class ErrorMessage extends Request{
    static final long serialVersionUID = 999;//TODO --  Correggere UID
    private int code;

    public ErrorMessage(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
