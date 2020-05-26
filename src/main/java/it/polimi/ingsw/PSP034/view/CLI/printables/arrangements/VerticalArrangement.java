package it.polimi.ingsw.PSP034.view.CLI.printables.arrangements;

import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;

import java.util.ArrayList;

public class VerticalArrangement extends Arrangement {
    public VerticalArrangement() {
        super();
    }

    public void setCentreAlignment() {
        super.setAlignment(0);
        updateAlignment();
    }

    public void setLeftAlignment() {
        super.setAlignment(1);
        updateAlignment();
    }

    public void setRightAlignment() {
        super.setAlignment(2);
        updateAlignment();
    }

    @Override
    public void updateAlignment() {
        ArrayList<String> constructorArray = new ArrayList<>();
        int maxWidth = 0;
        int totalHeight = 0;
        for (PrintableObject object : super.getObjects()) {
            if (object.getWidth() > maxWidth)
                maxWidth = object.getWidth();
            totalHeight += object.getHeight();
        }
        if (super.getObjects().size() != 0) {
            switch (super.getAlignment()) {
                case 0:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                            continue;

                        if (index != 0) {
                            for (int j = 0; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        int beforeSpace = (maxWidth - currentObject.getWidth()) / 2;
                        int afterSpace = maxWidth - currentObject.getWidth() - beforeSpace;

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";
                            if (currentObject.getWidth() < maxWidth) {
                                constructorLine += new String(new char[beforeSpace]).replace('\u0000', ' ');
                            }

                            if(line == 0)
                                constructorLine += "@" + new String(new char[currentObject.getWidth()-1]).replace('\u0000', '#');
                            else
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', '#');

                            if (currentObject.getWidth() < maxWidth) {
                                constructorLine += new String(new char[afterSpace]).replace('\u0000', ' ');
                            }
                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
                case 1:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                            continue;

                        if (index != 0) {
                            for (int j = 0; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";

                            if(line == 0)
                                constructorLine += "@" + new String(new char[currentObject.getWidth()-1]).replace('\u0000', '#');
                            else
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', '#');

                            if (currentObject.getWidth() < maxWidth) {
                                constructorLine += new String(new char[maxWidth - currentObject.getWidth()]).replace('\u0000', ' ');
                            }
                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
                case 2:
                    for (int index = 0; index < super.getObjects().size(); index++) {
                        PrintableObject currentObject = super.getObjects().get(index);

                        if(!currentObject.getVisibility()  ||  currentObject.getWidth() == 0  ||  currentObject.getHeight() == 0)
                            continue;

                        if (index != 0) {
                            for (int j = 0; j < super.getBorder(); j++) {
                                constructorArray.add(new String(new char[maxWidth]).replace('\u0000', ' '));
                            }
                        }

                        for (int line = 0; line < currentObject.getHeight(); line++) {
                            String constructorLine = "";
                            if (currentObject.getWidth() < maxWidth) {
                                constructorLine += new String(new char[maxWidth - currentObject.getWidth()]).replace('\u0000', ' ');
                            }

                            if(line == 0)
                                constructorLine += "@" + new String(new char[currentObject.getWidth()-1]).replace('\u0000', '#');
                            else
                                constructorLine += new String(new char[currentObject.getWidth()]).replace('\u0000', '#');

                            constructorArray.add(constructorLine);
                        }
                    }
                    break;
            }
            super.setObjectSize(constructorArray.size());
            for (int i = 0; i < constructorArray.size(); i++) {
                super.setObjectLine(i, constructorArray.get(i));
            }
        } else {
            super.setObjectSize(0);
        }
        super.updateAlignment();
    }

    @Override
    public void print(int line, int column) {
        if(super.getVisibility()) {
            int index = 0;
            while (super.getObjects().get(index).getHeight() == 0  ||  super.getObjects().get(index).getWidth() == 0  ||  !super.getObjects().get(index).getVisibility()){
                index++;
            }
            external:
            for (int currLine = 0; currLine < super.getHeight(); currLine++) {
                for (int currColumn = 0; currColumn < super.getWidth(); currColumn++) {
                    if (super.getObject()[currLine].charAt(currColumn) == '@') {
                        super.getObjects().get(index).print(line + currLine, column + currColumn);
                        index++;
                        if (index >= super.getObjects().size())
                            break external;
                        while (super.getObjects().get(index).getHeight() == 0  ||  super.getObjects().get(index).getWidth() == 0  ||  !super.getObjects().get(index).getVisibility()){
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