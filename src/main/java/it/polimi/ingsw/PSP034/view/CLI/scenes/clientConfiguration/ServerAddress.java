package it.polimi.ingsw.PSP034.view.CLI.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.CLI.printables.*;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.CLI.scenes.Scene;


/**
 * This class creates the scene to be printed when the user has to input the IP address of the server to connect to.
 */
public class ServerAddress extends Scene {
    private final VerticalArrangement va1;

    private final Font title;
    private final VerticalArrangement va2;

    private final Message enterAddress;
    private final Message errorMessage;
    private final HorizontalArrangement ha1;

    private final Message address;
    private final TextBox addressPicker;


    /**
     * Creates the scene and organizes the objects.
     * @param error Whether there was an error last time the client tried to connect to the server.
     */
    public ServerAddress(boolean error){
        va1 = new VerticalArrangement();
        va1.setCentreAlignment();
        va1.setBorder(1);

        title = new Font("server choice");
        va2 = new VerticalArrangement();
        va2.setLeftAlignment();
        va2.setBorder(1);
        va1.addObjects(title, va2);

        enterAddress = new Message("Enter the address of the server you want to join", -1);
        errorMessage = new Message(ANSI.FG_red + "Couldn't reach the server. Please try again", -1);
        errorMessage.setVisible(error);
        ha1 = new HorizontalArrangement();
        ha1.setBottomAlignment();
        ha1.setBorder(1);
        va2.addObjects(enterAddress,errorMessage,ha1);

        address = new Message("Address (default localhost) : ", -1);
        addressPicker = new TextBox(enterAddress.getWidth()- address.getWidth());
        ha1.addObjects(address, addressPicker);

    }

    /**
     * {@inheritDoc}
     * @return The IP address entered by the use.
     */
    @Override
    public String show() {
        super.clearFrame();

        super.printMain(va1);

        RegexCondition regex = new RegexCondition("^((localhost)|((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])))?$", "Invalid IP address.");

        return addressPicker.waitAnswer(regex);
    }
}
