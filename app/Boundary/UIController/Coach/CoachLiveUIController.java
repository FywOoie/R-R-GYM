package app.Boundary.UIController.Coach;

import app.Control.Coach.CoachLiveControl;
import app.Control.Launch.appMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class CoachLiveUIController implements Initializable {
    private appMain appmain;
    @FXML
    private Label backLabel;
    @FXML
    private TextField adviceID;//Used to extract suggestions
    @FXML
    private Label stu;//Used to display the name of the student being broadcast
    public static String stuName;//declare a static variable

    /**
     * This method is a label listener
     * It will goto the login UI if the label is clicked
     *
     * @param mouseEvent
     */
    public void labelExit(MouseEvent mouseEvent) {
        this.backLabel.setTextFill(Paint.valueOf("#000000"));
    }

    /**
     * This method is a label listener
     * It will save the advice if the button is clicked
     *
     * @param mouseEvent
     */
    public void labelEnter(MouseEvent mouseEvent) {
        this.backLabel.setTextFill(Paint.valueOf("#a9a9a9"));
    }

    /**
     * This method is a label listener
     * It will goto the previous UI if the label is clicked
     *
     * @param event
     */
    public void backLabelClick(MouseEvent event) {
        appmain.gotoCoachUI();
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stu.setText(stuName);
    }

    /**
     * This method save the advice from the coach.
     *
     * @param mouseEvent
     */
    public void saveAdvice(MouseEvent mouseEvent) {
        CoachLiveControl coachLiveCtrl = new CoachLiveControl();
        String ad = adviceID.getText();//Extract coach comments from text box
        coachLiveCtrl.saveAdvice(ad);
    }
}
