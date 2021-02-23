package slogo.backend.utils;

import slogo.backend.commands.basic.CommandFactory;
import slogo.backend.exceptions.BackendException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Manages the tree structure that is used to correctly execute the commands in the
 * right order.
 *
 * Ex:  CommandTree commandTree = new CommandTree(myTurtleHistory);
 * commandTree.addToCommandTree("Forward");
 * command.addToCommandTree("50");
 *
 * Additional Usage: none
 * @author Eric Han
 */
public class CommandTree {

    private static final String DECIMAL_PATTERN = "-?[0-9]+\\.?[0-9]*";

    CommandTreeNode commandTreeNode = new CommandTreeNode("", null);
    CommandTreeNode rightMostNode = commandTreeNode;

    private CommandFactory myCommandFactory;
    private int turtleID = 1;

    public CommandTree(TurtleHistory turtleHistory) {
        myCommandFactory = new CommandFactory(turtleHistory);
    }

    /**
     * Puts the command to the tree and execute some commands if possible
     * Assumption: command is one word, and is not user-defined function or user-defined variable
     * @param command : the word to be put
     * @throws BackendException if command is neither a command nor a number
     */
    public void addToCommandTree(String command) throws BackendException {
        if(isThisStringDouble(commandTreeNode.getCommandWord()) || commandTreeNode.getCommandWord().equals("")) {
            commandTreeNode.setCommandWord(command);
        }
        else {
            if (isThisStringDouble(command)) {
                addNumberToTree(command);
            } else {
                addCommandToTree(command);
            }
        }
        interpretTreeFromRight();
    }

    /**
     * returns the only number remaining in the tree
     * Assumption: this is called if onlyNumberLeft() is called prior and it returned true
     */
    public double getLastDouble() {
        if(commandTreeNode.getCommandWord().equals("")) {
            return 0d;
        }
        return Double.parseDouble(commandTreeNode.getCommandWord());
    }

    /**
     * returns true if there is only one node left in the tree and its value is a number
     */
    public boolean onlyNumberLeft() {
        return commandTreeNode.getChildrenNumber() == 0 && isThisStringDouble(commandTreeNode.getCommandWord());
    }

    private void interpretTreeFromRight() throws BackendException {
        String command = rightMostNode.getCommandWord();
        System.out.println(command);
        try {
            int parameter = myCommandFactory.getNumParameter(command);
            while(!(parameter > rightMostNode.getChildrenNumber()) && command != "") {
                printFullCommand();
                double value = myCommandFactory.execute(command, turtleID, getParameters());
                replaceRightMostCommandWithNumber(value);
                command = rightMostNode.getCommandWord();
                if(isThisStringDouble(command)) {
                    System.out.println(command);
                    break;
                }
                parameter = myCommandFactory.getNumParameter(command);
            }
        }
        catch (Exception e) {
            if(!isThisStringDouble(commandTreeNode.getCommandWord())) {
                throw new BackendException(e, command + " is not a valid command, nor is it a variable or function");
            }
        }
    }

    public void setTurtleID(int turtleID) {
        this.turtleID = turtleID;
    }

    private void addNumberToTree(String command) {
        if(rightMostNode.getLeftNode() == null) {
            rightMostNode.setLeftNode(new CommandTreeNode(command, rightMostNode));
        } else {
            rightMostNode.setRightNode(new CommandTreeNode(command, rightMostNode));
        }
    }

    private void addCommandToTree(String command) {
        rightMostNode.setRightNode(new CommandTreeNode(command, rightMostNode));
        rightMostNode = rightMostNode.getRightNode();
    }


    private boolean isThisStringDouble(String command) {
        return Pattern.matches(DECIMAL_PATTERN, command);
    }

    private void replaceRightMostCommandWithNumber(double num) {
        rightMostNode.setCommandWord("" + num);
        rightMostNode.deleteChildren();
        if(rightMostNode.getParentNode() != null) {
            moveRightNodeUp();
        }
    }

    private void moveRightNodeUp() {
        rightMostNode = rightMostNode.getParentNode();
        if(rightMostNode.getLeftNode() == null) {
            CommandTreeNode treeNode = rightMostNode.getRightNode();
            rightMostNode.deleteChildren();
            rightMostNode.setLeftNode(treeNode);
        }
    }

    private List<Double> getParameters() {
        List<Double> list = new ArrayList<>();
        if(rightMostNode.getLeftNode() != null) {
            list.add(Double.parseDouble(rightMostNode.getLeftNode().getCommandWord()));
        }

        if(rightMostNode.getRightNode() != null) {
            list.add(Double.parseDouble(rightMostNode.getRightNode().getCommandWord()));
        }
        return list;
    }

    private void printFullCommand() {
        System.out.print(rightMostNode.getCommandWord() + " ");
        for(double a : getParameters()) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
}