package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerBookLiveControl;
import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Live.LiveSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerBookLiveUIController implements Initializable {
    private appMain appmain;
    ObservableList<LiveSession> data =
            FXCollections.observableArrayList();

    @FXML
    private DatePicker calendar;
    @FXML
    private TableView<LiveSession> liveTable;
    @FXML
    private TableColumn<LiveSession, Integer> liveIDColumn;
    @FXML
    private TableColumn<LiveSession, String> liveContentColumn;
    @FXML
    private TableColumn<LiveSession, String> coachColumn;
    @FXML
    private TableColumn<LiveSession, String> timeColumn;
    @FXML
    private TableColumn<LiveSession, String> selectedColumn;
    @FXML
    private Label invalidLabel;
    private TableRow<LiveSession> rowClick;
    private LiveSession rowDataClick;
    private String userSelectedDate;

    /**
     * This is a button listener.
     * User should choose a day before searching. If the user clicked the button,
     * the table will show the information of the live in the specific day.
     *
     * @param event
     * @throws ParseException
     */
    @FXML
    void searchLive(MouseEvent event) throws ParseException {
        CustomerBookLiveControl cusBookCtrl = new CustomerBookLiveControl();
        if(calendar.getValue() == null){
            invalidLabel.setText("Invalid Query");
            invalidLabel.setAlignment(Pos.CENTER);
            return;
        }
        String userDate = calendar.getValue().toString(); // Obtain the selected day from user.
        boolean flag = cusBookCtrl.judgeDate(userDate);
        if(flag){
            invalidLabel.setText("Invalid Date");
            invalidLabel.setAlignment(Pos.CENTER);
            return;
        }
        else{
            invalidLabel.setText("");
        }
        this.showContent();
    }

    public void showContent() throws ParseException {
        data.clear();
        CustomerBookLiveControl cusBookCtrl = new CustomerBookLiveControl();
        String userDate = calendar.getValue().toString(); // Obtain the selected day from user.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(userDate); // Convert the selected day to the standard form.
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        this.userSelectedDate = userDate;
        ArrayList<LiveSession> liveData = new ArrayList<LiveSession>();
        liveData = cusBookCtrl.showLive(userDate);
        if(liveData.size()!=0){
            for(int i = 0;i<liveData.size();i++){
                data.add(liveData.get(i));
            }
        }
        liveTable.setItems(data);
    }

    /**
     * This method is used to return back to the live book UI.
     *
     * @param event
     */
    @FXML
    void goBack(MouseEvent event) {
        appmain.gotoLiveBookUI();
    }

    /**
     * This is a button listener.
     * If the user clicked the button, it'll go back to the customer UI.
     *
     * @param event
     */
    @FXML
    void back(MouseEvent event) {
        appmain.gotoUserUI();
    }

    /**
     * This method is used to set the main control of the software.
     * @param appmain
     */
    public void setApp(appMain appmain) { this.appmain = appmain;}

    /**
     * This method is called after the user has selected his choice in the
     * confirm window.
     * The triggered action of user's selection is defined in the control class.
     *
     * @param functionFlag
     */
    public void select(int functionFlag) throws ParseException {
        CustomerBookLiveControl cusBookCtrl = new CustomerBookLiveControl();
        if(ConfirmControl.getConfirmFlag()){
            switch (functionFlag){
                case 1:
                    cusBookCtrl.setRowStyle(this.rowDataClick,true,this.userSelectedDate);
                    this.showContent();
                    break;
                case 2:
                    cusBookCtrl.setRowStyle(this.rowDataClick,false,this.userSelectedDate);
                    this.showContent();
                    break;
            }
        }
    }

    /**
     * This method is used to initialize all the components in the
     * user interface.
     * This method is called once the software jump into the UI.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the column in the table.
        liveIDColumn.setCellValueFactory(new PropertyValueFactory<>("liveID"));
        liveContentColumn.setCellValueFactory(new PropertyValueFactory<>("liveTitle"));
        coachColumn.setCellValueFactory(new PropertyValueFactory<>("coachName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("scheduledStartTime"));
        selectedColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        // If the row is double clicked by the user, then the confirm window will be shown to the user.
        liveTable.setRowFactory( tv -> {
            TableRow<LiveSession> row = new TableRow<>();
            // Add action listener to the row.
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    // Get the content of the clicked row.
                    LiveSession rowData = row.getItem();
                    // Go to the confirm window.
                    this.rowClick = row;
                    this.rowDataClick = rowData;
                    CustomerBookLiveControl cusBookCtrl = new CustomerBookLiveControl();
                    boolean flag = cusBookCtrl.judge(rowData);
                    if(!flag){
                        appmain.gotoConfirmUI("Are you sure to book this live?",1,this.getClass(),this);
                    }
                    else{
                        appmain.gotoConfirmUI("Are you sure to cancel this live?",2,this.getClass(),this);
                    }
                }
            });
            return row ;
        });
    }
}
