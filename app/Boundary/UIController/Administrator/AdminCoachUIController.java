package app.Boundary.UIController.Administrator;

import app.Control.Administrator.AdminCoachControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CoachAccount;
import app.Entity.Account.CurrentAccount;
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

public class AdminCoachUIController implements Initializable {
    @FXML
    private TextField coachSearchInput;
    @FXML
    private TextField coachNameInput;
    @FXML
    private TextField coachPwdInput;
    @FXML
    private TextField deleteCoachID;
    @FXML
    private Label coachAddLabel;
    @FXML
    private Label coachDeleteLabel;
    @FXML
    private TableView<CoachAccount> coachTable;
    @FXML
    private TableColumn<CoachAccount, Integer> uidCol;
    @FXML
    private TableColumn<CoachAccount, String> nameCol;
    @FXML
    private TableColumn<CoachAccount, String> pwdCol;
    @FXML
    private Label wrongSearchLabel;
    @FXML
    private Label adminName;
    private appMain appmain;

    ObservableList<CoachAccount> data =
            FXCollections.observableArrayList();//Store CoachAccount entity to show them in the table

    /**
     * This method is a button listener
     * It will show the bill information if the button is clicked
     * @param mouseEvent: Mouse click event
     */
    public void changeToBillList(MouseEvent mouseEvent) {
        appmain.gotoBillListUI();
    }

    /**
     * This method is a button listener
     * It will show the video information if the button is clicked
     * @param mouseEvent: Mouse click event
     */
    public void changeToVideoList(MouseEvent mouseEvent) {
        appmain.gotoVideoListUI();
    }

    /**
     * This function is the initialize procedure,
     * which is called at appmain
     */
    public void initializeCoach(){

        adminName.setText(CurrentAccount.getCurAccount().getId());//Show admin name
        this.coachShow();
    }

    /**
     * This method is a button listener
     * It will show the customer management if the button is clicked
     * @param mouseEvent : Mouse click event
     */
    public void changeToCustomer(MouseEvent mouseEvent) {appmain.gotoAdminCustomerUI();}

    /**
     * This method is a button listener
     * It will log out current account if the button is clicked
     * @param mouseEvent : Mouse click event
     */
    public void adminLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    /**
     * add coach by admin's input
     * @param event: Mouse click event
     */
    public void addCoach(MouseEvent event){
        String id = coachNameInput.getText();//Get coach's name
        String pwd = coachPwdInput.getText();//Get coach's password
        AdminCoachControl adminCoachCtrl = new AdminCoachControl();
        int flag = adminCoachCtrl.addCoach(id, pwd);
        if(flag == 0){
            return;
        }
        else if(flag == 1){
            coachAddLabel.setTextFill(Color.RED);
            coachAddLabel.setText("Password illegal!");
        }
        else if(flag == 2){
            coachAddLabel.setTextFill(Color.GREEN);
            coachAddLabel.setText("Add successfully!");
        }
        else if(flag == 3){
            coachAddLabel.setTextFill(Color.RED);
            coachAddLabel.setText("Wrong! This coach name exists!");
        }
        this.coachShow();
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
     * delete a coach based on uid provided by admin
     * @param event: Mouse click event
     */
    public void deleteCoach(MouseEvent event){
        AdminCoachControl adminCoachCtrl = new AdminCoachControl();
        String coachID = deleteCoachID.getText();//Get admin's chosen ID
        int flag = adminCoachCtrl.deleteCoach(coachID);
        if(flag == 0){
            return;
        }
        else if(flag == 1){
            coachDeleteLabel.setTextFill(Color.RED);
            coachDeleteLabel.setText("Please enter a numerical value!");
        }
        else if(flag == 2){
            coachDeleteLabel.setTextFill(Color.GREEN);
            coachDeleteLabel.setText("Delete successfully!");
        }
        else if(flag == 3){
            coachDeleteLabel.setTextFill(Color.RED);
            coachDeleteLabel.setText("No coach found!");
        }
       this.coachShow();
    }

    public void coachShow(){
        AdminCoachControl adminCoachCtrl = new AdminCoachControl();
        String searchID = coachSearchInput.getText();//Search ID user puts in
        ArrayList<CoachAccount> coachData = new ArrayList<CoachAccount>();
        wrongSearchLabel.setText("");//Clear previous prompt
        data.clear();//Clear previous Bill entities
        coachData = adminCoachCtrl.coachShow(searchID);
        for(int i = 0;i < coachData.size();i++){
            data.add(coachData.get(i));
        }
        coachTable.setItems(data);//Push data into table
    }

    public void setName(String name) {
        adminName.setText(name);
    }

    public void setApp(appMain appmain) { this.appmain = appmain;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uidCol.setCellValueFactory(
                new PropertyValueFactory<>("uid"));//Second column
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));//First column
        pwdCol.setCellValueFactory(
                new PropertyValueFactory<>("pwd"));//Third column
    }
}
