package slogo.frontend.statusscreen;

/**
 * Object used so that Controller class can extract the changed string when
 * the user types on a different screen.
 *
 * @author Eric Han
 */
public class ChangedString {
    private String changedVariable = "";

    public String getChangedVariable() {
        String str = changedVariable;
        changedVariable = "";
        return str;
    }

    public void setChangedVariable(String changedVariable) {
        this.changedVariable = changedVariable;
    }
}
