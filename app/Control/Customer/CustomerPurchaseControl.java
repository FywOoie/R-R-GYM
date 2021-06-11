package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ReadFile;
import app.Entity.Resource.ResourceData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPurchaseControl implements Initializable {
    private appMain appmain;
    @FXML
    private Label memBuyLabel;

    public void select(int functionFlag){
        if(ConfirmControl.getConfirmFlag()){
            switch (functionFlag){
                case 1:
                    buyBasicExe();
                    break;
                case 2:
                    buyPremierMemExe();
                    break;
            }
        }
    }
    @FXML
    private TextArea basicDes;
    @FXML
    private TextArea preDis;
    @FXML
    private Label basicPrice;
    @FXML
    private Label prePrice;
    public void intializePurchase(){
    basicDes.setText("Basic membership contains a basic workout package videos. " +
            "If you purchase now, you will receive a "+ ResourceData.controlNumber(ResourceData.basicDiscount)*100+"% "+"discount." +
            " In the meanwhile, you can always upgrade your membership!");
    preDis.setText("Premier membership contains a all workout videos. If you purchase now, you will receive a "+
            ResourceData.controlNumber((ResourceData.premierDiscount)*100)+"% discount. In the meanwhile, " +
            "you can have a 1 on 1 training coach teach you a few moves online!");
    basicPrice.setText(String.valueOf(50* ResourceData.controlNumber(1- ResourceData.basicDiscount)));
    prePrice.setText(String.valueOf(100* ResourceData.controlNumber(1- ResourceData.premierDiscount)));
    }



    /**
     * It a control function for user to purchase basic membership
     * @return 1 stands for operation success 0 stands for failed
     **/
    public int buyBasicExe(){
        int i = 0;

        ReadFile readFile = new ReadFile();
        int uid = ResourceData.uid;
        int membershipLevel = Integer.parseInt(readFile.getmembership(uid));
        if(membershipLevel == 0)
        {
            i = new CustomerAccount().buymembership(1, ResourceData.basicDiscount);
            if(i == 0){
                memBuyLabel.setTextFill(Color.RED);
                memBuyLabel.setText("You don't have enough money!");
            }else{
                memBuyLabel.setTextFill(Color.GREEN);
                memBuyLabel.setText("Congratulations, you are now officially a basic member!");
                CurrentAccount.getCurAccount().setMembership(1);
            }
        }else{
            memBuyLabel.setTextFill(Color.RED);
            memBuyLabel.setText("You already have basic membership!");
        }
        return i;
    }

    /**
     * It is a control function for user to purchase premier membership
     * @return 1 stands for operation success 0 stands for failed
     **/
    public void buyPremierMemExe(){
        int i = 0;
        ReadFile readFile = new ReadFile();
        int uid = ResourceData.uid;
        int membershipLevel = Integer.parseInt(readFile.getmembership(uid));
        if(membershipLevel < 2)
        {
            i = new CustomerAccount().buymembership(2, ResourceData.premierDiscount);
            if(i == 0){
                memBuyLabel.setTextFill(Color.RED);
                memBuyLabel.setText("You don't have enough money!");
            }else{
                memBuyLabel.setTextFill(Color.GREEN);
                memBuyLabel.setText("Congratulations, you are now officially a premier member!");
                CurrentAccount.getCurAccount().setMembership(2);
            }
        }else{
            memBuyLabel.setTextFill(Color.RED);
            memBuyLabel.setText("You already have premier membership!");
        }
    }

    @FXML
    void buyBasic(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to buy basic membership?",1, this.getClass(),this);
    }

    //buy premie rmembership
    @FXML
    void buyPremierMem(MouseEvent event) {
        appmain.gotoConfirmUI("Are you sure to buy premier membership?",2, this.getClass(),this);
    }

    public void returnClick(MouseEvent mouseEvent) {
        appmain.gotoUserUI();
    }

    public void chargeClick(MouseEvent mouseEvent) { appmain.gotoChargeUI(); }


    public void setApp(appMain appmain){
        this.appmain = appmain;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }
}
