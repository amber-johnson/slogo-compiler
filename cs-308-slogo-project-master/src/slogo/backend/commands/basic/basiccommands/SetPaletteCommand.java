package slogo.backend.commands.basic.basiccommands;

import slogo.backend.commands.basic.BasicCommandInterface;
import slogo.backend.utils.TurtleHistory;

import java.util.List;


public class SetPaletteCommand implements BasicCommandInterface {
    private static final double ACCURACY = 0.001;

    @Override
    public double getReturnValue(TurtleHistory turtleHistory, List<Double> parameters, int turtleID) {
        return (int) (ACCURACY + parameters.get(0));
    }
}
