package slogo.util;

/**
 * Class to hold all the information that frontend will need when drawing the turtle or background.
 *
 * @author Eric Han
 */
public class DrawStatus {

    private boolean visible;
    private int backGround;
    private int imageNum;

    private boolean visibleChanged;
    private boolean backGroundChanged;
    private boolean imageChanged;
    private boolean eraseScreen = false;


    public DrawStatus(boolean visible, int backGround, int imageNum, boolean eraseScreen) {
        this.visible = visible;
        this.backGround = backGround;
        this.imageNum = imageNum;
        this.eraseScreen = eraseScreen;
    }

    public DrawStatus(DrawStatus drawStatus) {
        this.visible = drawStatus.isTurtleVisible();
        this.backGround = drawStatus.getBackGround();
        this.imageNum = drawStatus.getImageNum();
        this.eraseScreen = drawStatus.screenToBeErased();
        this.visibleChanged = drawStatus.isVisibleChanged();
        this.imageChanged = drawStatus.isImageChanged();
        this.backGroundChanged = drawStatus.isBackGroundChanged();
    }

    /**
     * true if turtle is to be visible
     */
    public boolean isTurtleVisible() {
        return visible;
    }

    /**
     * returns the color index of the background
     */
    public int getBackGround() {
        return backGround;
    }

    /**
     * returns the image index of the turtle
     */
    public int getImageNum() {
        return imageNum;
    }

    /**
     * true if visibility of turtle was changed by the last command
     */
    public boolean isVisibleChanged() {
        return visibleChanged;
    }

    /**
     * true if background was changed by the last command
     */
    public boolean isBackGroundChanged() {
        return backGroundChanged;
    }

    /**
     * true if image was changed by the last command
     */
    public boolean isImageChanged() {
        return imageChanged;
    }

    /**
     * sets the visibility of turtle
     */
    public void setTurtleVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    /**
     * sets the background color index of turtle
     */
    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }

    /**
     * sets the image number of turtle
     */
    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    /**
     * Sets the values for the field variables according to another DrawStatus object
     */
    public void compareAndSetChanged(DrawStatus other) {
        visibleChanged = !(other.isTurtleVisible() == visible);
        backGroundChanged = !(other.getBackGround() == backGround);
        imageChanged = !(other.getImageNum() == imageNum);
        eraseScreen = !other.screenToBeErased() && eraseScreen;
    }

    /**
     * true if command was clearscreen
     */
    public boolean screenToBeErased() {
        return eraseScreen;
    }
}
