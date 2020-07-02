package it.polimi.ingsw.PSP034.view.CLI.printables.arrangements;


import it.polimi.ingsw.PSP034.view.CLI.printables.PrintableObject;

import java.util.ArrayList;

/**
 * {@inheritDoc}
 * <p>
 * A horizontal arrangement aligns the object it contains horizontally from left to right.
 * <p>
 * The height of the arrangement is the same as the highest object it contains. The width of the arrangement is the sum of the width of every object it contains and of the spacing (border) between the objects.
 *
 */
public class HorizontalArrangement extends Arrangement{

    public HorizontalArrangement(){
        super();
    }

    /**
     * Sets the alignment of the arrangement to centre, meaning that the centre of each object will be at the same height.
     */
    public void setCentreAlignment(){
        super.setAlignment(0);
        updateAlignment();
    }

    /**
     * Sets the alignment of the arrangement to top, meaning that the top of each object will be at the same height.
     */
    public void setTopAlignment(){
        super.setAlignment(1);
        updateAlignment();
    }

    /**
     * Sets the alignment of the arrangement to bottom, meaning that the bottom of each object will be at the same height.
     */
    public void setBottomAlignment(){
        super.setAlignment(2);
        updateAlignment();
    }

    /**
     * {@inheritDoc}
     */
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
                for (int currColumn = 0; currColumn < super.getWidth(); currColumn++) {
                    for (int currLine = 0; currLine < super.getHeight(); currLine++) {
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