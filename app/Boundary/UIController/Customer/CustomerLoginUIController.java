package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerLoginControl;
import app.Control.Launch.appMain;
import javafx.event.ActionEvent;
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

public class CustomerLoginUIController implements Initializable {
    private appMain appmain;
    @FXML
    private PasswordField password;
    @FXML
    private TextField customerID;
    @FXML
    private Label wrongPwdLabel;

    /**
     * This method is a key listener.
     * It will goto the customer UI if the key 'Enter' is pressed.
     *
     * @param event
     */
    public void loginEnter(KeyEvent event){
        CustomerLoginControl cusLoginCtrl = new CustomerLoginControl();
        String id = customerID.getText(); // Get the input coach ID.
        String pwd = password.getText(); // Get the input password.
        if (event.getCode() == KeyCode.ENTER) {
            if(cusLoginCtrl.login(id, pwd)){
                appmain.gotoUserUI();
            }
            else{
                wrongPwdLabel.setText("Wrong User ID or Password!");
            }
        }
    }

    /**
     * This method is a button listener.
     * It will goto the customer UI if the button is clicked.
     *
     * @param actionEvent
     */
    public void loginButtonClick(ActionEvent actionEvent) {
        CustomerLoginControl cusLoginCtrl = new CustomerLoginControl();
        String id = customerID.getText(); // Get the input coach ID.
        String pwd = password.getText(); // Get the input password.
        if(cusLoginCtrl.login(id, pwd)){
            appmain.gotoUserUI();
        }
        else{
            wrongPwdLabel.setText("Wrong User ID or Password!");
        }
    }

    /**
     * This method is a button listener.
     * It will goto the register UI if the button is clicked.
     *
     * @param mouseEventEvent
     */
    public void registerButtonClick(MouseEvent mouseEventEvent) {
        appmain.gotoRegisterUI();}

    /**
     * This method is a label listener.
     * It will goto the coach login UI if the label is clicked.
     *
     * @param mouseEvent
     */
    public void coachLoginClick(MouseEvent mouseEvent) { appmain.gotoCoachLoginUI(); }

    /**
     * This method is a label listener.
     * It will goto the admin login UI if the label is clicked.
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

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
