package it.polimi.ingsw.PSP034.view.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;

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

        Spacer spacerUP = new Spacer(super.getFrameWidth(), (super.getFrameHeight()-va1.getHeight())/2);
        Spacer spacerDOWN = new Spacer(super.getFrameWidth(), super.getFrameHeight()-va1.getHeight()-spacerUP.getHeight());
        VerticalArrangement mainVA = new VerticalArrangement();
        mainVA.addObjects(spacerUP, va1, spacerDOWN);

        mainVA.print(super.getFrameStartLine(), super.getFrameStartColumn());

        RegexCondition regex = new RegexCondition("^([0-65535])?$", "Invalid server port.");

        return portPicker.waitAnswer(regex);
    }
}
