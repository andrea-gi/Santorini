package it.polimi.ingsw.PSP034.view.CLI.printables.arrangements;

import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;

import java.util.ArrayList;

/**
 * {@inheritDoc}
 * <p>
 * A vertical arrangement aligns the object it contains vertically from top to bottom.
 * <p>
 * The width of the arrangement is the same as the largest object it contains. The height of the arrangement is the sum of the height of every object it contains and of the spacing (border) between the objects.
 */
public class VerticalArrangement extends Arrangement {

    /**
     * Initializes an empty vertical arrangement. Border is set to 1 and alignment is set to centre.
     */
    public VerticalArrangement() {
        super();
    }

    /**
     * Sets the alignment of the arrangement to centre, meaning that the centre of each object will be on the same column.
     */
    public void setCentreAlignment() {
        super.setAlignment(0);
        updateAlignment();
    }

    /**
     * Sets the alignment of the arrangement to left, meaning that the left side of each object will be on the same column.
     */
    public void setLeftAlignment() {
        super.setAlignment(1);
        updateAlignment();
    }

    /**
     * Sets the alignment of the arrangement to right, meaning that the right side of each object will be on the same column.
     */
    public void setRightAlignment() {
        super.setAlignment(2);
        updateAlignment();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Prints all th object that the arrangement contains based on the set alignment.
     * @param line line to start printing from. The value is 1-based.
     * @param column line to start printing from.  The value is 1-based.
     */
    @Override
    public void print(int line, int column) {
        if(super.getVisibility()) {
            int index = 0;
            while (super.getObjects().get(index).getHeight() == 0  ||  super.getObjects().get(index).getWidth() == 0  ||  !super.getObjects().get(index).getVisibility()){
                index++;
                if(index >= super.getObjects().size())
                    break;
            }
            if(index < super.getObjects().size()) {
                external:
                for (int currLine = 0; currLine < super.getHeight(); currLine++) {
                    for (int currColumn = 0; currColumn < super.getWidth(); currColumn++) {
                        if (super.getObject()[currLine].charAt(currColumn) == '@') {
                            super.getObjects().get(index).print(line + currLine, column + currColumn);
                            index++;
                            if (index >= super.getObjects().size())
                                break external;
                            while (super.getObjects().get(index).getHeight() == 0 || super.getObjects().get(index).getWidth() == 0 || !super.getObjects().get(index).getVisibility()) {
                                index++;
                                if (index >= super.getObjects().size())
                                    break external;
                            }
                        }
                    }
                }
            }
        }
        super.setStartLine(line);
        super.setStartColumn(column);
    }
}