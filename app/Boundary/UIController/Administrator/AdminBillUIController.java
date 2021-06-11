package app.Boundary.UIController.Administrator;

import app.Control.Administrator.AdminBillControl;
import app.Control.Customer.CustomerRegisterControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import app.Entity.Transaction.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminBillUIController implements Initializable {
    private appMain appmain;
    @FXML
    private Label adminName;
    @FXML
    private TextField billSearchInput;
    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, Double> money;
    @FXML
    private TableColumn<Bill, Integer> billID;
    @FXML
    private TableColumn<Bill, String> time;
    @FXML
    private Label wrongSearchLabel;
    @FXML
    private Label promoLabel;
    @FXML
    private TextField basisDiscount;
    @FXML
    private TextField premierDiscount;

    private ObservableList<Bill> data =
            FXCollections.observableArrayList();//Store Bill entity to show them in the table

    /**
     * This method is called when user clicks the show bill button.
     * The content of the bill will be listed in the table based on the
     * appointed bill ID.
     */
    public void clickBillShow() {
        AdminBillControl adminBillCtrl = new AdminBillControl();
        String searchID = billSearchInput.getText();//Search ID user puts in
        wrongSearchLabel.setText("");//Clear previous prompt
        data.clear();//Clear previous Bill entities
        ArrayList<Bill> billData = adminBillCtrl.billShow(searchID);
        if(billData == null){
            wrongSearchLabel.setText("Please enter numerical uid!");//Check for non-numberical input
            return;
        }
        if(billData.size() == 0){
            wrongSearchLabel.setTextFill(Color.BLACK);
            wrongSearchLabel.setText("No data found");
            return;
        }
        else{
            for(int i = 0;i < billData.size();i++){
                data.add(billData.get(i));
            }
            billTable.setItems(data);//Push data into table
        }

    }

    /**
     * Log out to the login UI of the administrator.
     */
    public void adminLogOutExe(){
        appmain.gotoAdminLoginUI();
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
                    adminLogOutExe();
                    break;
            }
        }
    }
    /**
     * This method is a button listener
     * It will show the coach information if the button is clicked
     * @param mouseEvent : Mouse click event
     */
    public void changeToCoachList(MouseEvent mouseEvent) {
        appmain.gotoCoachListUI();
    }

    /**
     * This method is a button listener
     * It will show the video information if the button is clicked
     * @param mouseEvent : Mouse click event
     */
    public void changeToVideoList(MouseEvent mouseEvent) {
        appmain.gotoVideoListUI();
    }

    /**
     * This method is a button listener
     * It will show the customer management if the button is clicked
     * @param mouseEvent
     */
    public void changeToCustomer(MouseEvent mouseEvent) {
        appmain.gotoAdminCustomerUI();
    }

    /**
     * This method is a button listener
     * It will log out current account if the button is clicked
     * @param mouseEvent : Mouse Event
     */
    public void adminLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    /**
     * This function is the initialize procedure,
     * which is called at appmain
     */
    public void initializeBill(){
        AdminBillControl adminBillCtrl = new AdminBillControl();
        adminBillCtrl.billShow("");//Show bill information when entering this interface
        adminName.setText(CurrentAccount.getCurAccount().getId());///Show admin name
        clickBillShow();
    }

    /**
     * This function is to change the discount rate of different memberships
     * @param event:Mouse event
     *
     */
    @FXML
    void changeDiscount(MouseEvent event) {
        String basicDis = basisDiscount.getText();
        String preDis = premierDiscount.getText();
        if(!basicDis.equals("") && !preDis.equals("")){
            if(CustomerRegisterControl.isNumeric(basicDis))
            {
                double discount = Double.parseDouble(basicDis);
                if((discount > 0) && (discount <= 100)){
                    ResourceData.basicDiscount = discount/100;
                    promoLabel.setTextFill(Color.GREEN);
                    promoLabel.setText("Successful");
                }else{
                    promoLabel.setTextFill(Color.RED);
                    promoLabel.setText("Enter 1-100!");
                }

            }
            if(CustomerRegisterControl.isNumeric(preDis))
            {
                double discount = Double.parseDouble(preDis);
                if((discount > 0) && (discount <= 100)){
                    ResourceData.premierDiscount = discount/100;
                    promoLabel.setTextFill(Color.GREEN);
                    promoLabel.setText("Successful");
                }else{
                    promoLabel.setTextFill(Color.RED);
                    promoLabel.setText("Enter 1-100!");
                }
            }
        }
    }

    public void setName(String name) {
        adminName.setText(name);
    }

    public void setApp(appMain appmain) { this.appmain = appmain;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billID.setCellValueFactory(
                new PropertyValueFactory<>("uid"));//first column
        money.setCellValueFactory(
                new PropertyValueFactory<>("amount"));//second column
        time.setCellValueFactory(
                new PropertyValueFactory<>("buyTime"));//third column
    }
}
