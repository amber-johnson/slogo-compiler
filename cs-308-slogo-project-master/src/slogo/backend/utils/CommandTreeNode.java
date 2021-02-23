package slogo.backend.utils;

/**
 * Node used in CommandTree to store data
 *
 * Ex:  CommandTreeNode cT = new CommandTreeNode("Forward", null);
 *
 * Additional Usage: none
 * @author Eric Han
 */
public class CommandTreeNode {
    private CommandTreeNode parentNode;
    private CommandTreeNode leftNode;
    private CommandTreeNode rightNode;
    private String commandWord;

    public CommandTreeNode(String commandWord, CommandTreeNode parentNode) {
        this.parentNode = parentNode;
        this.commandWord = commandWord;
    }

    /**
     * Returns the left node
     */
    public CommandTreeNode getLeftNode() {
        return leftNode;
    }

    /**
     * Sets the left node
     */
    public void setLeftNode(CommandTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * Returns the right node
     */
    public CommandTreeNode getRightNode() {
        return rightNode;
    }

    /**
     * Sets the right node
     */
    public void setRightNode(CommandTreeNode rightNode) {
        this.rightNode = rightNode;
    }

    /**
     * Gets the parent node
     */
    public CommandTreeNode getParentNode() {
        return parentNode;
    }

    /**
     * Returns the word stored in the node
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * Sets the word stored in the node
     */
    public void setCommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Delets all the children
     */
    public void deleteChildren() {
        leftNode = null;
        rightNode = null;
    }

    /**
     * Returns the number of its children that are not null
     */
    public int getChildrenNumber() {
        int leftNodeNum = leftNode == null ? 0 : 1;
        int rightNodeNum = rightNode == null ? 0 : 1;
        return leftNodeNum + rightNodeNum;
    }
}
