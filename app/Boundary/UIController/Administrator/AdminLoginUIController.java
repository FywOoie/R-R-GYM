package app.Boundary.UIController.Administrator;

import app.Control.Administrator.AdminLoginControl;
import app.Control.Launch.appMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TextField adminID;
    @FXML
    private PasswordField password;
    @FXML
    private Label wrongPwdLabel;

    /**
     * This method is a key listener
     * If the administrator inputs correct administrator ID and corresponding password,
     * it will jump to the administrator UI.
     *
     * @param event
     */
    public void loginEnter(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            AdminLoginControl adminLogin = new AdminLoginControl();
            String id = adminID.getText(); // Get the input administrator ID.
            String pwd = password.getText(); // Get the input password.
            if(adminLogin.login(id, pwd)){
                appmain.gotoVideoListUI();
            }
            else{
                // An notification when the administrator input the wrong user ID or password.
                wrongPwdLabel.setText("Wrong User ID or Password!");
            }
        }
    }

    /**
     * This method is a button listener
     * If administrator input correct administrator ID and corresponding password,
     * it will jump to the administrator UI.
     *
     * @param mouseEvent
     */
    public void loginClick(MouseEvent mouseEvent) {
        AdminLoginControl adminLogin = new AdminLoginControl();
        String id = adminID.getText(); // Get the input administrator ID.
        String pwd = password.getText(); // Get the input password.
        if(adminLogin.login(id, pwd)){
            appmain.gotoVideoListUI();
        }
        else{
        // An notification when the administrator input the wrong user ID or password.
        wrongPwdLabel.setText("Wrong User ID or Password!");
        }
    }

    /**
     * This method is a label listener
     * It will goto the coach login UI if the label is clicked.
     *
     * @param mouseEvent
     */
    public void coachLoginClick(MouseEvent mouseEvent) { appmain.gotoCoachLoginUI();}

    /**
     * This method is a label listener
     * It will goto the customer login UI if the label is clicked
     *
     * @param mouseEvent
     */
    public void customerLoginClick(MouseEvent mouseEvent) { appmain.gotoUserLoginUI(); }

    public void setApp(appMain appmain) { this.appmain = appmain; }

    public void labelExit(MouseEvent mouseEvent) {
        Label selectLabel = (Label) mouseEvent.getTarget();
        selectLabel.setTextFill(Paint.valueOf("#000000"));
    }

    public void labelEnter(MouseEvent mouseEvent) {
        Label selectLabel = (Label) mouseEvent.getTarget();
        selectLabel.setTextFill(Paint.valueOf("#7093DB"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
