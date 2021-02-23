package slogo.frontend.turtlescreen;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import slogo.backend.utils.ColorManager;
import slogo.backend.utils.TurtleHistory;
import slogo.backend.utils.TurtleMovement;
import slogo.util.DrawStatus;
import slogo.util.PenStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a collection of TurtleViews, iterates through the list of turtlemovements
 * given by the displayScreen, assigns them to the individual turtleview, make the turtleviews
 * move and draw lines according to the penStatus, drawStatus value of the turtleView
 *
 * @author Eric Han
 */
public class SLogoViewManager {

    private static final double INITIAL_RATIO = 0.5;
    private static final double FULL_SPEED = 15;
    private static final double MAX_SPEED = 300;
    private static final double PROTECTION_RATIO=0.00001;
    private static final double SETMAXSPEED = 10;
    private static final PenStatus INITIAL_PEN_STATUS = new PenStatus(true, 1, 1);
    private static final DrawStatus INITIAL_DRAW_STATUS = new DrawStatus(true, 1, 1, false);

    private List<TurtleView> turtleViewList = new ArrayList<>();
    private List<List<TurtleMovement>> turtleMovements = new ArrayList<>();
    private ImageManager imageManager;
    private ColorManager colorManager = new ColorManager();
    private ColorAndPenStatus myColorAndPenStatus;
    private DisplayScreen turtlePane;
    private Image image;
    private boolean step = false;
    private double speed = FULL_SPEED * INITIAL_RATIO;
    private PenStatus penStatus;
    private DrawStatus drawStatus;
    private int index = 0;
    private boolean maxSpeed = false;
    private List<Line> lines = new ArrayList<>();


    public SLogoViewManager(DisplayScreen displayScreen) {
        penStatus = INITIAL_PEN_STATUS;
        drawStatus = INITIAL_DRAW_STATUS;
        myColorAndPenStatus = new ColorAndPenStatus(colorManager);
        turtlePane = displayScreen;
        imageManager = new ImageManager();
        setImage(2);
        addTurtleView(1);
    }

    /**
     * purpose : make this class store the collection of all the turtleMovement, and start allocating
     * the turtleMovement to individual TurtleViews
     * @param turtleHistory : TurtleHistory object that holds all the turtleMovement
     */
    protected void setHistory(TurtleHistory turtleHistory) {
        turtleMovements.clear();
        index = 0;
        turtleMovements.addAll(turtleHistory.getMyTurtleHistory());
        System.out.println(turtleMovements.size() + " is the size");
        allocateTurtleMovements();
    }

    /**
     * purpose : make the turtleViews move, and according to the movements, will draw
     * the lines on the screen. Will allocate turtleMovements to the individual turtleviews
     * if no turtleView is moving
     */
    protected void update() {
        int movingNum = 0;
        if(step) {
            setSpeed(SETMAXSPEED);
        }
        for(TurtleView turtleView : turtleViewList) {
            if (turtleView.isMoving()) {
                movingNum++;
                drawLine(turtleView);
                checkInitialize(turtleView);
            }
        }
        if(movingNum == 0 && index < turtleMovements.size()-1) {
            index++;
            allocateTurtleMovements();
        }
        if(step) {
            setSpeed(SETMAXSPEED);
            step = false;
        }
    }

    /**
     * Sets the image according to the image number (when graphically decided)
     * @param imageNum: image number
     */
    protected void setImage(int imageNum) {
        if(imageManager.getImage(imageNum)!=null) {
            image = imageManager.getImage(imageNum);
        }
        for(TurtleView turtleView : turtleViewList) {
            turtleView.setImage(image);
        }
        drawStatus.setImageNum(imageNum);
    }

    /**
     * Sets line color of the lines that will be drawn (when graphically decided)
     */
    protected void setLineColor(Color color) {
        myColorAndPenStatus.setDefaultLineColor(color);
    }

    /**
     * Sets size of the lines that will be drawn (when graphically decided)
     */
    public void setPenSize(double penSize) {
        penStatus.setPenSize(penSize);
        for(TurtleView turtleView : turtleViewList) {
            myColorAndPenStatus.setPenSize(turtleView, penSize, true);
        }
    }

    /**
     * Sets the speed of the turtleViews' movement
     * @param ratio : relative speed of the turtle
     */
    protected void setSpeed(double ratio) {
        double inputSpeed = 0;
        if(!maxSpeed) {
            inputSpeed = ratio <= 1 ? FULL_SPEED * ratio + PROTECTION_RATIO : MAX_SPEED;
            maxSpeed = ratio > 1;
            this.speed = ratio <= 1 ? inputSpeed : speed;
        } else {
            inputSpeed = ratio <= 1 ? MAX_SPEED : speed;
            maxSpeed = ratio <= 1;
        }
        for(TurtleView turtleView : turtleViewList) {
            turtleView.setSpeed(inputSpeed);
        }
    }

    /**
     * Make the turtleViews jump to the ending point of their current path
     */
    public void step() {
        step = true;
        System.out.println(speed);
    }

    protected boolean finishedMoving() {
        return index >= turtleMovements.size()-1;
    }

    /**
     * Returns the number that the Visualization will use to dynamically change the texts on the Variable scrollPane
     */
    public int getCurrentInstructionIndex() {
        return index;
    }

    private void updateDrawing(TurtleView turtleView) {
        DrawStatus drawStatus = turtleView.getDrawStatus();
        if(drawStatus.isVisibleChanged()) {
            turtleView.setVisible(drawStatus.isTurtleVisible());
        }
        if(drawStatus.isImageChanged()) {
            turtleView.setImage(imageManager.getImage(drawStatus.getImageNum()));
        }
        if(drawStatus.isBackGroundChanged()) {
            myColorAndPenStatus.setBackgroundColor(drawStatus.getImageNum());
        }
    }

    private TurtleView getTurtleView(int myID) {
        for(TurtleView turtleView : turtleViewList) {
            if(turtleView.getMyID() == myID) {
                return turtleView;
            }
        }
        addTurtleView(myID);
        return getTurtleView(myID);
    }

    private void addTurtleView(int myID) {
        if(!hasTurtleView(myID)) {
            TurtleView turtleView = new TurtleView(image, myID, turtlePane.getWidth(), turtlePane.getHeight());
            updateTurtleStatus(turtleView);
            turtleViewList.add(turtleView);
            turtlePane.getChildren().add(turtleView);
            myColorAndPenStatus.addLineColor(turtleView);
            myColorAndPenStatus.addPenSize(turtleView);
            turtleView.setSpeed(speed);
            turtleView.setX(turtlePane.getWidth()/2 - turtleView.getFitWidth()/2);
            turtleView.setY(turtlePane.getHeight()/2 - turtleView.getFitHeight()/2);
        }
    }

    private boolean hasTurtleView(int myID) {
        for(TurtleView turtleView : turtleViewList) {
            if(turtleView.getMyID() == myID) {
                return true;
            }
        }
        return false;
    }

    private void updateTurtleStatus(TurtleView turtleView) {
        turtleView.setPenStatus(penStatus);
        turtleView.setDrawStatus(drawStatus);
    }

    private void allocateTurtleMovements() {
        if(index >= turtleMovements.size()-1) {
            return;
        }
        for(TurtleMovement turtleMovement : turtleMovements.get(index)) {
            getTurtleView(turtleMovement.getTurtleID()).addMovement(turtleMovement);
        }
    }

    private void drawLine(TurtleView turtleView) {
        Line line = turtleView.updateAndGetLine();
        updateDrawing(turtleView);
        if (turtleView.getPenStatus().isPenSizeChanged()) {
            myColorAndPenStatus.setPenSize(turtleView, turtleView.getPenStatus().getPenSize(), false);
        }
        if (line != null) {
            line.setStroke(myColorAndPenStatus.getLineColor(turtleView));
            line.setStrokeWidth(myColorAndPenStatus.getPenSize(turtleView));
            turtlePane.getChildren().add(line);
            turtlePane.getChildren().remove(turtleView);
            turtlePane.getChildren().add(turtleView);
            lines.add(line);
        }
    }

    private void checkInitialize(TurtleView turtleView) {
        if(turtleView.getDrawStatus().screenToBeErased()) {
            for(Line myLine: lines) {
                turtlePane.getChildren().remove(myLine);
            }
            lines.clear();
        }
    }
}
