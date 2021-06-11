package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerChPwdControl;
import app.Control.Launch.appMain;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerChPwdUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private Label wrongPwdLabel;
    @FXML
    private Label invalidPwd;
    @FXML
    private Label invalidPwd2;
    @FXML
    private Label successLabel;


    public void setApp(appMain appmain) {
        this.appmain = appmain;
    }

    public void backLabelClick(MouseEvent event) {
        CustomerAccount customerAccount = (CustomerAccount) CurrentAccount.getCurAccount();
        appmain.gotoCustomerInfoUI();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This function is to check whether the old password you enter is correct
     * @return if the old password is wrong it will return false, otherwise it return true.
     */
    public int checkOldPwd(){
        CustomerChPwdControl cusChPwdCtrl = new CustomerChPwdControl();
        String opwd = oldPassword.getText();//get user input of old password
        int flag = cusChPwdCtrl.checkOldPwd(opwd);
        if(flag == 1){
            wrongPwdLabel.setTextFill(Color.RED);
            wrongPwdLabel.setText("the password entered is wrong!");
        }
        return flag;
    }

    public int checkNewPwd() {
        CustomerChPwdControl cusChPwdCtrl = new CustomerChPwdControl();
        String npwd = newPassword.getText();//get user input of new password
        String cpwd = passwordConfirm.getText();//get user input of confirm password
        int flag = cusChPwdCtrl.checkNewPwd(npwd, cpwd);
        if(flag == 0){
            invalidPwd.setTextFill(Color.RED);
            invalidPwd.setText("6-to-10-digit-and-letter password!");
        }
        else if(flag == 1){
            invalidPwd.setTextFill(Color.RED);
            invalidPwd.setText("Same password as before!");
        }
        else if(flag == 3){
            wrongPwdLabel.setTextFill(Color.RED);
            invalidPwd2.setText("Please enter password same as above!");
        }
        return flag;
    }

    public void changePwd(MouseEvent mouseEvent) {
        CustomerChPwdControl cusChPwdCtrl = new CustomerChPwdControl();
        int flagOld = checkOldPwd();
        int flagNew = checkNewPwd();
        if(flagOld == 0 && flagNew == 2){
            String nPwd = newPassword.getText();
            cusChPwdCtrl.saveNewPwd(nPwd);
            successLabel.setText("Password changed successfully!");
            wrongPwdLabel.setText("");
            invalidPwd.setText("");
            invalidPwd2.setText("");
        }
    }
}
