package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.messages.Request;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestServerConfig extends Request {
    private final ArrayList<RequiredServerConfig> configs;

    public RequestServerConfig(RequiredServerConfig... args){
        configs = new ArrayList<>();
        configs.addAll(Arrays.asList(args));
    }

    public RequiredServerConfig[] getServerConfig(){
        RequiredServerConfig[] returnActions = new RequiredServerConfig[configs.size()];
        for(int i = 0; i < configs.size(); i++){
            returnActions[i] = configs.get(i);
        }
        return returnActions;
    }
}
