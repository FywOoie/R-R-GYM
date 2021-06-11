package app.Boundary.UIController.Administrator;

import app.Control.Administrator.AdminCustomerControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminCustomerUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TextField customerSearchInput;
    @FXML
    private TableView<CustomerAccount> customerTable;
    @FXML
    private TableColumn<CustomerAccount, String> nameCol;
    @FXML
    private TableColumn<CustomerAccount, Integer> uidCol;
    @FXML
    private TableColumn<CustomerAccount, String> pwdCol;
    @FXML
    private TableColumn<CustomerAccount, Double> balanceCol;
    @FXML
    private TableColumn<CustomerAccount, Integer> memCol;
    @FXML
    private Label wrongSearchLabel;
    @FXML
    private Label adminName;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private TextField deleteCustomerInput;
    @FXML
    private Label customerAddLabel;
    @FXML
    private Label customerDeleteLabel;
    @FXML
    private TextField changeCustomerInput;
    @FXML
    private TextField customerNameInput;
    @FXML
    private TextField changeCustomerInforInput;
    @FXML
    private Label customerChangeLabel;
    @FXML
    private TextField customerPwdInput;
    @FXML
    private ChoiceBox<String> chooseChange;

    String selectedProperty = "Name";// Selected changing properties, default the first choice
    String[] candidateProperties = {"Name","Password","Balance","Membership"};//Candidate changing properties
    ObservableList<CustomerAccount> data =
            FXCollections.observableArrayList();//Store CoachAccount entity to show them in the table


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
     * add coach by admin's input
     * @param event: Mouse click event
     */
    public void addCustomer(MouseEvent event){
        AdminCustomerControl adminCusCtrl = new AdminCustomerControl();
        String id = customerNameInput.getText();//Get coach's name
        String pwd = customerPwdInput.getText();//Get coach's password
        if(id.equals("") || pwd.equals("")){
            customerAddLabel.setTextFill(Color.RED);
            customerAddLabel.setText("Enter all info    !");
        }else{
            int flag = adminCusCtrl.addCustomer(id, pwd);
            if(flag == 1){
                //Check if the password is legal
                //If illegal, print out information
                customerAddLabel.setTextFill(Color.RED);
                customerAddLabel.setText("Password illegal!");
            }
            else if(flag == 2){
                //Adding successfully
                customerAddLabel.setTextFill(Color.GREEN);
                customerAddLabel.setText("Add successfully!");
            }
            else if(flag == 3){
                //Adding wrong
                customerAddLabel.setTextFill(Color.RED);
                customerAddLabel.setText("Wrong! This customer name already exits!");
            }
            this.customerShow();
        }

    }

    /**
     *delete a customer based on uid provided by admin
     * @param event: Mouse click event
     */
    public void deleteCustomer(MouseEvent event){
        AdminCustomerControl adminCusCtrl = new AdminCustomerControl();
        String customerID = deleteCustomerInput.getText();//get admin's chosen ID
        int flag = adminCusCtrl.deleteCustomer(customerID);
        if(flag == 0){
            return;
        }
        else if(flag == 1){
            customerDeleteLabel.setTextFill(Color.RED);
            customerDeleteLabel.setText("Please enter a numerical value!");
        }
        else if(flag == 2){
            customerDeleteLabel.setTextFill(Color.GREEN);
            customerDeleteLabel.setText("Delete successfully!");
        }
        else if(flag == 3){
            customerDeleteLabel.setTextFill(Color.RED);
            customerDeleteLabel.setText("No customer found!");
        }
        this.customerShow();//Refresh table
    }

    /**
     * change customer information based on searched customer ID
     * @param event
     */
    public void changeCustomer(MouseEvent event){
        AdminCustomerControl adminCusCtrl = new AdminCustomerControl();
        String customerID = changeCustomerInput.getText();//get admin's chosen ID
        String info = changeCustomerInforInput.getText();
        int flag = adminCusCtrl.changeCustomer(customerID,selectedProperty,info);
        if(flag == 0){
            return;
        }
        else if(flag == 1){
            //Check invalid input
            customerChangeLabel.setTextFill(Color.RED);
            customerChangeLabel.setText("Please enter a numerical value!");
        }
        else if(flag == 2){
            customerChangeLabel.setTextFill(Color.RED);
            customerChangeLabel.setText("Please enter a numerical balance!");
        }
        else if(flag == 3){
            customerChangeLabel.setTextFill(Color.RED);
            customerChangeLabel.setText("Please enter a numerical membership type!");
        }
        else if(flag == 4){
            //Changing successfully
            customerChangeLabel.setTextFill(Color.GREEN);
            customerChangeLabel.setText("Change successfully");
        }
        else if(flag == 5){
            //Failed
            customerChangeLabel.setTextFill(Color.RED);
            customerChangeLabel.setText("Change unsuccessfully");
        }
        this.customerShow();//Refresh table
    }

    /**
     * This method is a button listener
     * It will show the bill information if the button is clicked
     * @param mouseEvent
     */
    public void changeToBillList(MouseEvent mouseEvent) { appmain.gotoBillListUI(); }

    /**
     * This method is a button listener
     * It will show the coach information if the button is clicked
     * @param mouseEvent
     */
    public void changeToCoachList(MouseEvent mouseEvent) {
        appmain.gotoCoachListUI();
    }

    /**
     * This method is a button listener
     * It will show the video information if the button is clicked
     * @param mouseEvent
     */
    public void changeToVideoList(MouseEvent mouseEvent) {
        appmain.gotoVideoListUI();
    }

    /**
     * This method is a button listener
     * It will show the customer management if the button is clicked
     * @param mouseEvent
     */
    public void changeToCustomer(MouseEvent mouseEvent) { appmain.gotoAdminCustomerUI(); }

    public void adminLogOutExe(){
        appmain.gotoAdminLoginUI();
    }

    /**
     * This method is a button listener
     * It will log out current account if the button is clicked
     * @param mouseEvent
     */
    public void adminLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    /**
     * This function is for the intitialization of the whole interface
     */
    public void initializeVideo(){
        this.customerShow();
        adminName.setText(CurrentAccount.getCurAccount().getId());//Show admin name

        //Choice box of change properties
        chooseChange.getItems().addAll(candidateProperties);
        chooseChange.getSelectionModel().selectFirst();//Set default choice as name
        chooseChange.setTooltip(new Tooltip("Select Change Property"));//Set a tip of hovering
        chooseChange.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                selectedProperty = candidateProperties[newValue.intValue()];//Set selected property as the chosen one
            }
        });
    }

    /**
     * This function is to show the customer information
     * when the search button is clicked
     */
    public void customerShow(){
        String line = "";//Temp string which stores
        AdminCustomerControl adminCusCtrl = new AdminCustomerControl();
        String searchID = customerSearchInput.getText();//Search ID user puts in
        wrongSearchLabel.setText("");//Clear previous prompt
        data.clear();//Clear previous Bill entities
        ArrayList<CustomerAccount> customerData = new ArrayList<CustomerAccount>();
        customerData = adminCusCtrl.customerShow(searchID);
        for(int i = 0;i < customerData.size();i++){
            data.add(customerData.get(i));
        }
        customerTable.setItems(data);//Push data into table
    }

    public void setName(String name){
        adminName.setText(name);
    }

    public void setApp(appMain appmain) { this.appmain = appmain;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set table column format
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));//Second column
        uidCol.setCellValueFactory(
                new PropertyValueFactory<>("uid"));//3rd column
        pwdCol.setCellValueFactory(
                new PropertyValueFactory<>("pwd"));//4th column
        balanceCol.setCellValueFactory(
                new PropertyValueFactory<>("balance"));//5th column
        memCol.setCellValueFactory(
                new PropertyValueFactory<>("membership"));//6th column
    }
}
