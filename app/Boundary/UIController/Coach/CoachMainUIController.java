package app.Boundary.UIController.Coach;

import app.Control.Coach.CoachLiveControl;
import app.Control.Coach.CoachMainControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Live.CoachLive;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CoachMainUIController implements Initializable {
    private appMain appmain;
    @FXML
    private ChoiceBox choice1;
    @FXML
    private TableView<CoachLive> Table1;
    @FXML
    private TableColumn<CoachLive,String> StuCol;
    @FXML
    private TableColumn<CoachLive,String> TitleCol;
    @FXML
    private TableColumn<CoachLive, Integer> WeekCol;
    @FXML
    private TableColumn<CoachLive,String> TimeCol;
    @FXML
    private Label Tip;
    @FXML
    private Label coachName;

    ObservableList<CoachLive> data =
            FXCollections.observableArrayList();//store CoachAccount entity to show them in the table
    private String coach;
    private String passStu;
    private ArrayList Stuname = new ArrayList();
    private ArrayList<CoachLive> coachLiveData = new ArrayList<CoachLive>();

    public void setName(String name){
        coachName.setText(name);
    }

    /**
     * This method is for getting student information and put them in a table and a choice box
     */
    public void getStu(){
        CoachMainControl coachMainCtrl = new CoachMainControl();
        Stuname.clear();
        coachLiveData.clear();
        data.clear();
        Stuname = coachMainCtrl.getStuName();
        coachLiveData = coachMainCtrl.getStu();
        if(coachLiveData.size()!=0) {
            for(int i = 0;i < coachLiveData.size();i++){
                data.add(coachLiveData.get(i));
            }
            Table1.setItems(data);//push data into table
            StuCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            TitleCol.setCellValueFactory(new PropertyValueFactory<>("liveTitle"));
            WeekCol.setCellValueFactory(new PropertyValueFactory<>("week"));
            TimeCol.setCellValueFactory(new PropertyValueFactory<>("scheduledStartTime"));
        }

        choice1.getItems().addAll(Stuname);
        choice1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value){
                passStu=Stuname.get(new_value.intValue()).toString();
            }
        });
    }

    /**
     * This method is a button listener
     * When the button is clicked, the page will go to the coachVideoUI
     * @param mouseEvent
     */
    public void VideoButtonClick(MouseEvent mouseEvent){ appmain.gotoCoachVideoUI(); }

    /**
     * This is method is a button listener
     * When the button is clicked, first it will check whether passStu string is empty
     * if passStu is not empty it will go to the coachLiveUI
     * else it will alert
     * @param mouseEvent
     */
    public void LiveButtonClick(MouseEvent mouseEvent){
        if(passStu!=null){
            CoachLiveControl.stuName = passStu;
            appmain.gotoCoachLiveUI();
        }
        else{
            Tip.setText("You must choose a student to enter the liver page!");
            Tip.setTextFill(Color.RED);
        }
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
                    clickLogOutExe();
                    break;
            }
        }
    }

    public void clickLogOutExe() {
        appmain.gotoCoachLoginUI();
        CurrentAccount.setCurAccount(null);
    }

    /**
     * This method is a button listener
     * It will log out the coach account if the button is clicked
     * @param mouseEvent
     */

    public void clickLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
