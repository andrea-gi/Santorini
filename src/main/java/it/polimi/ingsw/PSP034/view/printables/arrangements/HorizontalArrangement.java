package it.polimi.ingsw.PSP034.view.printables.arrangements;


import it.polimi.ingsw.PSP034.view.printables.PrintableObject;

import java.util.ArrayList;

public class HorizontalArrangement extends Arrangement{

    public HorizontalArrangement(){
        super();
    }

    public void setCentreAlignment(){
        super.setAlignment(0);
        updateAlignment();
    }

    public void setTopAlignment(){
        super.setAlignment(1);
        updateAlignment();
    }

    public void setBottomAlignment(){
        super.setAlignment(2);
        updateAlignment();
    }

    @Override
    public void updateAlignment(){
        ArrayList<String> constructorArray = new ArrayList<>();
        int maxHeight = 0;
        for(PrintableObject object : super.getObjects()){
            if(object.getHeight() > maxHeight)
                maxHeight = object.getHeight();
        }
        if(super.getObjects().size() != 0) {
            switch (super.getAlignment()) {
                case 0:
                    for (int line = 0; line < maxHeight; line++) {
                        String constructorLine = "";
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if (index != 0)
                                constructorLine += new String(new char[super.getBorder()]).replace('\u0000', ' ');

                            if ((line < (maxHeight - currentObject.getHeight()) / 2) || (line >= (maxHeight + currentObject.getHeight()) / 2))
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', ' ');
                            else
                                constructorLine += currentObject.getObject()[line-(maxHeight - currentObject.getHeight()) / 2];
                        }
                        constructorArray.add(constructorLine);
                    }
                    break;
                case 1:
                    for (int line = 0; line < maxHeight; line++) {
                        String constructorLine = "";
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if (index != 0)
                                constructorLine += " ";

                            if (line >= currentObject.getHeight())
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', ' ');
                            else
                                constructorLine += currentObject.getObject()[line];
                        }
                        constructorArray.add(constructorLine);
                    }
                    break;
                case 2:
                    for (int line = 0; line < maxHeight; line++) {
                        String constructorLine = "";
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if (index != 0)
                                constructorLine += " ";

                            if (line < maxHeight - currentObject.getHeight())
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', ' ');
                            else
                                constructorLine += currentObject.getObject()[line -(maxHeight - currentObject.getHeight())];
                        }
                        constructorArray.add(constructorLine);
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

    @Override
    public void print(int line, int column) {
        int currColumn = column;
        for(int i = 0; i < super.getObjects().size(); i++){
            getObjects().get(i).print(line+(super.getHeight()-getObjects().get(i).getHeight())/2, currColumn);
            currColumn += getObjects().get(i).getWidth() + getBorder();
        }
        super.print(line, column);
    }
}
