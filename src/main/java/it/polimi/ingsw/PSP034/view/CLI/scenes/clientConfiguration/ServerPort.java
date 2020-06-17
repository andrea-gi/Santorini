package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class ServerPort extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final VerticalArrangement va2;

    private final Message enterPort;
    private final HorizontalArrangement ha1;

    private final Message port;
    private final TextBox portPicker;

    public ServerPort(){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("server port");
        va2 = new VerticalArrangement();
        va2.setLeftAlignment();
        va2.setBorder(1);
        va1.addObjects(title, va2);

        enterPort = new Message("Enter the port of the server you want to join", -1);
        ha1 = new HorizontalArrangement();
        va2.addObjects(enterPort, ha1);

        port = new Message("Port (default 2020) : ", -1);
        portPicker = new TextBox(enterPort.getWidth()-port.getWidth());
        ha1.addObjects(port, portPicker);
        ha1.setBottomAlignment();
        ha1.setBorder(1);
    }

    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        RegexCondition regex = new RegexCondition("^([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-5][0-9][0-9][0-9][0-9]|6[0-4][0-9][0-9][0-9]|65[0-4][0-9][0-9]|655[0-2][0-9]|6553[0-5])?$", "Invalid server port.");
        return portPicker.waitAnswer(regex);
    }
}
