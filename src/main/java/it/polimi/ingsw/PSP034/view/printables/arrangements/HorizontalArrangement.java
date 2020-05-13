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
                        StringBuilder constructorLine = new StringBuilder();
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                                continue;

                            if (index != 0)
                                constructorLine.append(new String(new char[super.getBorder()]).replace('\u0000', ' '));

                            int above = (maxHeight - currentObject.getHeight()) / 2;
                            int under = above + currentObject.getHeight();
                            if (line < above || line >= under)
                                constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', ' '));
                            else {
                                if(line - (maxHeight - currentObject.getHeight()) / 2 == 0)
                                    constructorLine.append("@").append(new String(new char[currentObject.getWidth() - 1]).replace('\u0000', '#'));
                                else
                                    constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', '#'));
                            }
                        }
                        constructorArray.add(constructorLine.toString());
                    }
                    break;
                case 1:
                    for (int line = 0; line < maxHeight; line++) {
                        StringBuilder constructorLine = new StringBuilder();
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                                continue;

                            if (index != 0)
                                constructorLine.append(new String(new char[super.getBorder()]).replace('\u0000', ' '));

                            if (line >= currentObject.getHeight())
                                constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', ' '));
                            else {
                                if(line == 0)
                                    constructorLine.append("@").append(new String(new char[currentObject.getWidth()-1]).replace('\u0000', '#'));
                                else
                                    constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', '#'));
                            }
                        }
                        constructorArray.add(constructorLine.toString());
                    }
                    break;
                case 2:
                    for (int line = 0; line < maxHeight; line++) {
                        StringBuilder constructorLine = new StringBuilder();
                        for (int index = 0; index < super.getObjects().size(); index++) {
                            PrintableObject currentObject = super.getObjects().get(index);

                            if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                                continue;

                            if (index != 0)
                                constructorLine.append(new String(new char[super.getBorder()]).replace('\u0000', '#'));

                            if (line < maxHeight - currentObject.getHeight())
                                constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', ' '));
                            else{
                                if(line -(maxHeight - currentObject.getHeight()) == 0)
                                    constructorLine.append("@").append(new String(new char[currentObject.getWidth() - 1]).replace('\u0000', '#'));
                                else
                                    constructorLine.append(new String(new char[currentObject.getWidth()]).replace('\u0000', '#'));
                            }
                        }
                        constructorArray.add(constructorLine.toString());
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
        super.updateAlignment();
    }

    @Override
    public void print(int line, int column) {
        if(super.getVisibility()) {
            int index = 0;
            while (super.getObjects().get(index).getHeight() == 0  ||  super.getObjects().get(index).getWidth() == 0){
                index++;
            }
            external:
            for (int currColumn = 0; currColumn < super.getWidth(); currColumn++) {
                for (int currLine = 0; currLine < super.getHeight(); currLine++) {
                    if (super.getObject()[currLine].charAt(currColumn) == '@') {
                        super.getObjects().get(index).print(line + currLine, column + currColumn);
                        index++;
                        if (index >= super.getObjects().size())
                            break external;
                        while (super.getObjects().get(index).getHeight() == 0  ||  super.getObjects().get(index).getWidth() == 0){
                            index++;
                            if (index >= super.getObjects().size())
                                break external;
                        }
                    }
                }
            }
        }
        super.setStartLine(line);
        super.setStartColumn(column);

    }
}
