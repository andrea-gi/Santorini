package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;

public class ServerPort extends Scene {
    @Override
    public String show() {
        super.clearFrame();
        Font title = new Font("server port");
        Message enterPort = new Message("Enter the port of the server you want to join", -1);
        Message port = new Message("Port (default 2020) : ", -1);
        TextBox portPicker = new TextBox(enterPort.getWidth()-port.getWidth());

        HorizontalArrangement ha1 = new HorizontalArrangement();
        ha1.addObjects(port, portPicker);
        ha1.setBottomAlignment();

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, enterPort, ha1);
        va1.setCentreAlignment();

        super.printMain(va1);

        RegexCondition regex = new RegexCondition("^([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-6][0-4][0-9][0-9][0-9]|65[0-4][0-9][0-9]|655[0-2][0-9]|6553[0-5])?$", "Invalid server port.");
        return portPicker.waitAnswer(regex);
    }
}
