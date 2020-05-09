package it.polimi.ingsw.PSP034.view.scenes.clientConfiguration;

import it.polimi.ingsw.PSP034.view.printables.*;
import it.polimi.ingsw.PSP034.view.printables.arrangements.HorizontalArrangement;
import it.polimi.ingsw.PSP034.view.printables.arrangements.VerticalArrangement;
import it.polimi.ingsw.PSP034.view.scenes.Scene;


/**
 * This class creates the scene to be printed when the user has to input the IP address of the server to connect to.
 */
public class ServerAddress extends Scene {
    @Override
    public String show() {
        super.clearFrame();

        //Initialization of the elements of the scene
        Font title = new Font("server choice");
        Message enterAddress = new Message("Enter the address of the server you want to join", -1);
        Message address = new Message("Address (default localhost) : ", -1);
        TextBox addressPicker = new TextBox(enterAddress.getWidth()-address.getWidth());

        //Spacial organization of the elements
        HorizontalArrangement ha1 = new HorizontalArrangement();
        ha1.addObjects(address, addressPicker);
        ha1.setBottomAlignment();

        VerticalArrangement va1 = new VerticalArrangement();
        va1.addObjects(title, enterAddress, ha1);
        va1.setCentreAlignment();

        //Print the scene
        super.printMain(va1);

        //Regex of the answer and error message
        RegexCondition regex = new RegexCondition("^((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))?$", "Invalid IP address.");

        //Request and return the answer
        return addressPicker.waitAnswer(regex);
    }
}
