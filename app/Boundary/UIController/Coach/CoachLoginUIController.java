package app.Boundary.UIController.Coach;

import app.Control.Coach.CoachLoginControl;
import app.Control.Launch.appMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class CoachLoginUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TextField coachID;
    @FXML
    private PasswordField password;
    @FXML
    private Label wrongPwdLabel;

    /**
     * This method is a key listener
     * If the coach inputs correct administrator ID and corresponding password,
     * it will jump to the coach UI.
     *
     * @param event
     */
    public void loginEnter(KeyEvent event){
        CoachLoginControl coachLoginCtrl = new CoachLoginControl();
        String id = coachID.getText(); // Get the input coach ID.
        String pwd = password.getText(); // Get the input password.
        if (event.getCode() == KeyCode.ENTER) {
            if(coachLoginCtrl.login(id, pwd)){
                appmain.gotoCoachUI();
            }
            else{
                wrongPwdLabel.setText("Wrong User ID or Password!");
            }
        }
    }

    /**
     * This method is a mouse listener
     * If the coach inputs correct administrator ID and corresponding password,
     * it will jump to the coach UI.
     *
     * @param actionEvent
     */
    public void loginButtonClick(ActionEvent actionEvent) {
        CoachLoginControl coachLoginCtrl = new CoachLoginControl();
        String id = coachID.getText(); // Get the input coach ID.
        String pwd = password.getText(); // Get the input password.
        if(coachLoginCtrl.login(id, pwd)){
            appmain.gotoCoachUI();
        }
        else{
            wrongPwdLabel.setText("Wrong User ID or Password!");
        }
    }

    /**
     * If the label is clicked, it will jump to the user login UI.
     *
     * @param mouseEvent
     */
    public void userLabelClick(MouseEvent mouseEvent) { appmain.gotoUserLoginUI(); }

    /**
     * If the label is clicked, it will jump to the administrator login UI.
     *
     * @param mouseEvent
     */
    public void adminLoginClick(MouseEvent mouseEvent) { appmain.gotoAdminLoginUI(); }

    public void labelExit(MouseEvent mouseEvent) {
        Label selectLabel = (Label) mouseEvent.getTarget();
        selectLabel.setTextFill(Paint.valueOf("#000000"));
    }

    public void labelEnter(MouseEvent mouseEvent) {
        Label selectLabel = (Label) mouseEvent.getTarget();
        selectLabel.setTextFill(Paint.valueOf("#7093DB"));
    }

    @FXML
    void liveButtonEnter(MouseEvent event) {
        DropShadow shadow = new DropShadow();
        Button button = (Button) event.getSource();
        button.setEffect(shadow);
    }

    @FXML
    void liveButtonExit(MouseEvent event) {
        DropShadow shadow = new DropShadow();
        Button button = (Button) event.getSource();
        button.setEffect(null);
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
