package app.Boundary.UIController.Administrator;

import app.Control.Administrator.AdminVideoControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Resource.ResourceData;
import app.Entity.Video.Video;
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

public class AdminVideoUIController implements Initializable {
    @FXML
    private TextField deleteID;
    @FXML
    private TextField categoryInput;
    @FXML
    private TextField urlInput;
    @FXML
    private TextField coverInput;
    @FXML
    private TextField videoIDInput;
    @FXML
    private TextField videoSearchInput;
    @FXML
    private Label videoAddLabel;
    @FXML
    private Label videoDeleteLabel;
    @FXML
    private TableView<Video> videoTable;
    @FXML
    private TableColumn<Video, String> nameCol;
    @FXML
    private TableColumn<Video, Integer> uidCol;
    @FXML
    private TableColumn<Video, String> dateCol;
    @FXML
    private TableColumn<Video, String> categoryCol;
    @FXML
    private TableColumn<Video, String> urlCol;
    @FXML
    private TableColumn<Video, String> coverCol;
    @FXML
    private TableColumn<Video, Integer> likeCol;
    @FXML
    private Label wrongSearchLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label adminName;
    @FXML
    private ChoiceBox<String> chooseSearch;
    String selectedProperty = "Name";
    String[] candidateProperties = {"Name","UID","Category"};

    ObservableList<Video> data =
            FXCollections.observableArrayList();//store Video entity to show them in the table
    private appMain appmain;

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
     * @param mouseEvent : mouseEvent
     */
    public void changeToCoachList(MouseEvent mouseEvent) {
        appmain.gotoCoachListUI();
    }

    /**
     * This method is a button listener
     * It will show the bill information if the button is clicked
     * @param mouseEvent: mouseEvent
     */
    public void changeToBillList(MouseEvent mouseEvent) {
        appmain.gotoBillListUI(); }

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
     * @param mouseEvent
     */
    public void adminLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    /**
     * This function is to show video info by Table view
     * when the admin click the search button
     */
    public void videoShow(){
        AdminVideoControl adminVideoCtrl = new AdminVideoControl();
        ArrayList<Video> videoData= new ArrayList<Video>();
        data.clear();//Clear previous Bill entities
        String searchID = videoSearchInput.getText();//Search ID user puts in
        wrongSearchLabel.setText("");//Clear previous prompt
        videoData = adminVideoCtrl.videoShow(searchID,selectedProperty);
        //If no data is found, prompt admin
        if(videoData.size() == 0){
            wrongSearchLabel.setTextFill(Color.BLACK);
            wrongSearchLabel.setText("No data found.");
        }
        for(int i = 0;i < videoData.size();i++){
            data.add(videoData.get(i));
        }
        videoTable.setItems(data);//Push data into table
    }

    /**
     * This function is to add video based on information provided by admin
     * when the admin put in strings in input fields
     */
    public void addVideo(){
        AdminVideoControl adminVideoCtrl = new AdminVideoControl();
        if(videoIDInput.getText().equals("") || categoryInput.getText().equals("")
                || urlInput.getText().equals("") || coverInput.getText().equals("")){
            videoAddLabel.setTextFill(Color.RED);
            videoAddLabel.setText("Please enter all info");
        }else{
            String videoName = videoIDInput.getText();//Get admin's video name input
            String category = categoryInput.getText();//Get admin's video category input
            String videoUrl = "resource\\video\\url\\" + urlInput.getText();//Get admin's video url input
            String videoCover = "resource\\video\\cover\\" + coverInput.getText();//Get admin's video cover input
            int flag = adminVideoCtrl.addVideo(videoName, category, videoUrl, videoCover);

            if(flag == 1){
                videoAddLabel.setTextFill(Color.RED);
                videoAddLabel.setText("Invalid video category");
            }
            else if(flag == 2){
                videoAddLabel.setTextFill(Color.RED);
                videoAddLabel.setText("Video doesn't exist!");
            }
            else if(flag == 3){
                videoAddLabel.setTextFill(Color.RED);
                videoAddLabel.setText("Cover doesn't exist!");
            }
            else if(flag == 4){
                videoAddLabel.setTextFill(Color.GREEN);
                videoAddLabel.setText("Add successfully!");//Prompt user adding successfully
            }
            else if(flag == 5){
                videoAddLabel.setTextFill(Color.RED);
                videoAddLabel.setText("Name already existed!");
            }
            this.videoShow();//Show new info
        }
    }

    /**
     * This function is to delete a video based on videoID provided by admin
     * @param event : Mouse event
     */
    public void deleteVideo(MouseEvent event){
        AdminVideoControl adminVideoCtrl = new AdminVideoControl();
        String videoId = deleteID.getText();//Get admin's chosen ID
        if(!videoId.equals("")){
            int flag = adminVideoCtrl.deleteVideo(videoId);
            if(flag == 0){
                return;
            }
            else if(flag == 1){
                videoDeleteLabel.setTextFill(Color.GREEN);
                videoDeleteLabel.setText("Delete successfully!");
            }
            else if(flag == 2){
                videoAddLabel.setTextFill(Color.RED);
                videoDeleteLabel.setText("Fail deleting index!");
            }
            else if(flag == 3){
                videoAddLabel.setTextFill(Color.RED);
                videoDeleteLabel.setText("Fail deleting video file!");
            }
            else if(flag == 4){
                videoAddLabel.setTextFill(Color.RED);
                videoDeleteLabel.setText("Fail deleting video cover!");
            }
            this.videoShow();//Show new info
        }else{
            videoAddLabel.setTextFill(Color.RED);
            videoDeleteLabel.setText("Enter uid!");
        }

    }

    /**
     * Initialize AdminVideoUI interface
     * @param name:name of the video
     */
    public void initializeVideo(String name){
        this.videoShow();
        adminName.setText(name); //Show admin name

        //Provide categories for admin to add
        StringBuilder categoryContent = new StringBuilder();//String which consist of categories
        for(int i = 0; i < ResourceData.categoryList.size(); i++) {
            categoryContent.append(ResourceData.categoryList.get(i)).append("  ");//Add to the String to be shown
        }
        categoryLabel.setText("Category : " + categoryContent);//Set label content

        //Choice box of search properties
        chooseSearch.getItems().addAll(candidateProperties);
        chooseSearch.getSelectionModel().selectFirst();//Set default choice as name
        chooseSearch.setTooltip(new Tooltip("Select Search Property"));//Set a tip of hovering
        chooseSearch.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                selectedProperty = candidateProperties[newValue.intValue()];//Set selected property as the chosen one
            }
        });
    }

    public void setApp(appMain appmain) { this.appmain = appmain;}
    //Interface initialization
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set table column format
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("videoName"));//first column
        uidCol.setCellValueFactory(
                new PropertyValueFactory<>("videoID"));//second column
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("createDate"));//3th column
        categoryCol.setCellValueFactory(
                new PropertyValueFactory<>("category"));//4th column
        urlCol.setCellValueFactory(
                new PropertyValueFactory<>("videoUrl"));//5th column
        coverCol.setCellValueFactory(
                new PropertyValueFactory<>("videoCover"));//6th column
        likeCol.setCellValueFactory(
                new PropertyValueFactory<>("likeNum"));//7th column

    }

    public void setName(String name) {
        adminName.setText(name);
    }
}
