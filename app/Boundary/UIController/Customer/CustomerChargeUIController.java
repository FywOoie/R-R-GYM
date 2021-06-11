package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerChargeControl;
import app.Control.Customer.CustomerRegisterControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerChargeUIController implements Initializable {
    private appMain appmain;
    @FXML
    private Label chargePro;

    @FXML
    private TextField amount;

    @FXML
    void getFifty(MouseEvent event) {
        amount.setText("50");
    }

    @FXML
    void getOneHundred(MouseEvent event) {
        amount.setText("100");
    }

    public void backLabelClick(MouseEvent event) { appmain.gotoMemberShopUI(); }

    @FXML
    void getThirty(MouseEvent event) {
        amount.setText("30");
    }

    /**
     * This method is called after the user has selected his choice in the
     * confirm window.
     * The triggered action of user's selection is defined in the control class.
     *
     * @param functionFlag
     */
    public void select(int functionFlag){
        if(ConfirmControl.getConfirmFlag()){
            switch (functionFlag){
                case 1:
                    chargeExe();
                    break;
            }
        }
    }

    /**
     * It's a control function for user to charge
     * @param event MouseEvent
     *
     * @return 1 stands for operation success 0 stands for failed
     **/
    @FXML
    void charge(MouseEvent event) {
        appmain.gotoConfirmUI("Are you sure to charge?",1, this.getClass(),this);
    }

    /**
     * This method is used to execute the charge process.
     *
     */
    public void chargeExe(){
        CustomerChargeControl chargeCtrl = new CustomerChargeControl();
        if(amount.getText().equals("")){
            chargePro.setTextFill(Color.RED);
            chargePro.setText("Enter to charge!");
        }else if(!CustomerRegisterControl.isNumeric(amount.getText())){
            chargePro.setTextFill(Color.RED);
            chargePro.setText("Enter numerical value!");
        }else{
            boolean flag = chargeCtrl.charge(amount.getText());
            if(flag){
                chargePro.setTextFill(Color.GREEN);
                chargePro.setText("Thank you for buying!");
            }else{
                chargePro.setTextFill(Color.RED);
                chargePro.setText("Error occurred when buying!");
            }
        }
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

}