package app.Control.Share;

import app.Control.Launch.appMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the control class for the ConfirmUI.
 * When the user needs to confirm the information, the
 * ConfirmUI will be shown to the user. The class also
 * get the user's choice and return information to other UI.
 */
public class ConfirmControl implements Initializable {
    private appMain appmain;
    private static boolean liveConfirm = false; // Indicate whether the reservation of live is confirmed.
    private static boolean confirmFlag;
    private Stage subStage;
    @FXML
    private Label liveTitle;
    private int type;
    private Class cls;
    private Object obj;

    public void execute(int selectType, Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(this.cls == null){
        }
        this.cls.getMethod("select", int.class).invoke(obj, selectType);
    }

    /**
     * This method is called when the user doesn't want to
     * book the live.
     *
     * @param event
     */
    @FXML
    void cancel(MouseEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        confirmFlag = false;
        subStage.close();
        execute(this.type, obj);
    }

    /**
     * This method is called when the user booked the live.
     *
     * @param event
     */
    @FXML
    void confirm(MouseEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        confirmFlag = true;
        subStage.close();
        execute(this.type, obj);
    }

    public static boolean getConfirmFlag(){
        return confirmFlag;
    }

    /**
     * This is the setter for the variable {@code subStage}.
     *
     * @param subStage The instance of the window.
     */
    public void setStage(Stage subStage) {
        this.subStage = subStage;
    }

    /**
     * This is the setter for the variable {@code obj}.
     *
     * @param obj
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * This is the setter for the variable {@code cls}.
     *
     * @param cls
     */
    public void setCls(Class cls) {
        this.cls = cls;
    }

    /**
     * This method sets the title of the confirm window.
     *
     * @param confirmTitle The title of the confirm window
     */
    public void setTitle(String confirmTitle) {
        liveTitle.setAlignment(Pos.CENTER);
        liveTitle.setText(confirmTitle);
    }

    /**
     * This method sets the title of the confirm window.
     *
     * @param type The type of the confirmation
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * This is the getter for the variable {@code liveConfirm}.
     *
     * @return The value of {@code liveConfirm}
     */
    public static boolean getLiveConfirm(){
        return liveConfirm;
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
