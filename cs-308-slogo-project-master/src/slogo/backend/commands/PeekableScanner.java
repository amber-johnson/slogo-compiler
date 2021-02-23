package slogo.backend.commands;

import java.util.List;
import java.util.ArrayList;

/**
 * Purpose: The CommandBlockManager executes a String of commands and manages the control flow and all the variables, functions,
 * and active turtles defined for this String block of commands.
 * Assumptions: This class assumes the existence of everything in its constructor parameters. As such, when an accessibleVariables list or
 * definedFunction map has not yet been created, the user should pass in a new instance of these classes which the
 * CommandBlockManager can build off of. This class also depends on the TurtleHistory class, as its commands
 * directly execute commands that make changes to this class.
 * Example of usage: CommandBlockManager commandBlockManager = new CommandBlockManager(arguments, turtleHistory, accessibleVariables, definedFunctions);
 *                   double returnValue = commandBlockManager.executeInstructionBlock();
 * Additional details: none
 * @author Ha Nguyen, Eric Han
 */

public class PeekableScanner {
    private int index;
    private List<String> myWords = new ArrayList<>();

    public PeekableScanner(String source) {
        String[] arr = source.split(" ");
        for(int i=0; i<arr.length; i++) {
            myWords.add(arr[i]);
        }
        index = 0;
    }

    /**
     * Checks if the source String has a word/String following it
     * @return
     */
    public boolean hasNext() {
        return index < myWords.size();
    }

    /**
     * Gets the next individual word/String in the source String
     * @return
     */
    public String next() {
        String str = myWords.get(index);
        index++;
        return str;
    }

    /**
     * Gets the index of a certain String in the source
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Goes back to the index of a certain string in the source
     * @param index
     */
    public void goToIndex(int index) {
        this.index = index;
    }

    /**
     * peeks the next element in the source
     * @return
     */
    public String peek() {
        return myWords.get(index);
    }
}