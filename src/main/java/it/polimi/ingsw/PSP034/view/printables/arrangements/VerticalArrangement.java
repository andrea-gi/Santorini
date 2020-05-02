package it.polimi.ingsw.PSP034.view.printables.arrangements;

import it.polimi.ingsw.PSP034.view.printables.PrintableObject;

import java.util.ArrayList;

public class VerticalArrangement extends Arrangement {
    public VerticalArrangement(){
        super();
    }

    public void setCentreAlignment(){
        super.setAlignment(0);
        updateAlignment();
    }

    public void setLeftAlignment(){
        super.setAlignment(1);
        updateAlignment();
    }

    public void setRightAlignment(){
        super.setAlignment(2);
        updateAlignment();
    }

    @Override
    public void updateAlignment(){
        ArrayList<String> constructorArray = new ArrayList<>();
        int maxWidth = 0;
        int totalHeight = 0;
        for(PrintableObject object : super.getObjects()){
            if(object.getHeight() > maxWidth)
                maxWidth = object.getHeight();
            totalHeight += object.getHeight();
        }
        if(super.getObjects().size() != 0) {
            switch (super.getAlignment()) {
                case 0:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if (index != 0) {
                            for (int j = 1; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";
                            if(currentObject.getWidth() < maxWidth){
                                constructorLine += new String(new char[(maxWidth-currentObject.getWidth())/2]).replace('\u0000', ' ');
                            }
                            constructorLine += currentObject.getObject()[line];
                            if(currentObject.getWidth() < maxWidth){
                                constructorLine += new String(new char[maxWidth-currentObject.getWidth()-(maxWidth-currentObject.getWidth())/2]).replace('\u0000', ' ');
                            }
                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
                case 1:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if (index != 0) {
                            for (int j = 1; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";

                            constructorLine += currentObject.getObject()[line];

                            if(currentObject.getWidth() < maxWidth){
                                constructorLine += new String(new char[maxWidth-currentObject.getWidth()]).replace('\u0000', ' ');
                            }
                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
                case 2:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if (index != 0) {
                            for (int j = 1; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";
                            if(currentObject.getWidth() < maxWidth){
                                constructorLine += new String(new char[maxWidth-currentObject.getWidth()]).replace('\u0000', ' ');
                            }
                            constructorLine += currentObject.getObject()[line];

                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
            }
            super.setObjectSize(constructorArray.size());
            for(int i = 0; i<constructorArray.size(); i++){
                super.setObjectLine(i, constructorArray.get(i));
            }
        }else{
            super.setObjectSize(0);
        }
    }
}
