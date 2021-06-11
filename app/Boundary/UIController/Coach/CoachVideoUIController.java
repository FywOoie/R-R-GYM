package app.Boundary.UIController.Coach;

import app.Control.Coach.CoachVideoControl;
import app.Control.Launch.appMain;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import app.Entity.Video.Videoinfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CoachVideoUIController implements Initializable {
    private appMain appmain;
    @FXML
    private ChoiceBox<String> choice1;
    @FXML
    private ChoiceBox<String> choice2;
    @FXML
    private Label Tip;

    @FXML
    private TableView<Videoinfo> VideoTable;
    @FXML
    private TableColumn<Videoinfo,String> StuCol;
    @FXML
    private TableColumn<Videoinfo,String> VidCol;

    ObservableList<Videoinfo> data =
            FXCollections.observableArrayList();//store CoachAccount entity to show them in the table

    /**
     * This is a button listener method
     * when the button is clicked, the page will go to the coachUI
     * @param mouseEvent
     */
    public void backLabelClick(MouseEvent mouseEvent){
        CoachVideoControl.setStudent(null);
        CoachVideoControl.setVideo(null);
        appmain.gotoCoachUI();
    }

    /**
     * This method is button listener
     * when button is clicked, it will write the student, coach and video information to the txt
     * @param actionEvent :when mouse click it will action
     */
    public void SaveVideoClick(ActionEvent actionEvent){
        CoachVideoControl coachVideoCtrl = new CoachVideoControl();
        int flag = coachVideoCtrl.SaveVideoClick();
        if(flag == 1){
            Tip.setText("The video has already enter in the student video list!");
            Tip.setTextFill(Color.RED);
        }
        else if(flag == 2){
            Tip.setText("Invalid enter!");
            Tip.setTextFill(Color.RED);
        }
        else if(flag == 3){
            Tip.setText("Successfully entered!");
            Tip.setTextFill(Color.GREEN);
        }
        this.getInfo();
    }

    public void getInfo(){
        data.clear();
        CoachVideoControl coachVideoCtrl = new CoachVideoControl();
        ArrayList<Videoinfo> videoData = coachVideoCtrl.getInfo();
        if(videoData.size()!=0){
            for (Videoinfo videoDatum : videoData) {
                data.add(videoDatum);
            }
        VideoTable.setItems(data);//push data into table
        StuCol.setCellValueFactory(new PropertyValueFactory<>("StuName"));
        VidCol.setCellValueFactory(new PropertyValueFactory<>("VideoName"));
        }
    }

    public void getStuAndVideo(){
        CoachVideoControl.setCoach(CurrentAccount.getCurAccount().getId());
        ArrayList<String> Stuname = new ArrayList<>();
        ArrayList<String> Stuinfo = new ArrayList<>();
        File f1 = new File("Account\\CustomerCoach.txt");
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f1);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if (Objects.equals(CoachVideoControl.getCoach(), appMain.jsonReader(str).get("coachID").toString().replace("\"", ""))){
                    Stuname.add(appMain.jsonReader(str).get("stuID").toString().replace("\"", ""));
                    Stuinfo.add(appMain.jsonReader(str).get("stuID").toString().replace("\"", "")
                            +": "+appMain.jsonReader(str).get("position").toString().replace("\"", ""));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        ArrayList<String> Videoname = new ArrayList<>();
        File f2 = new File(ResourceData.videoFile);
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f2);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                Videoname.add(appMain.jsonReader(str).get("videoName").toString().replace("\"",""));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        choice1.getItems().addAll(Stuinfo);
        choice2.getItems().addAll(Videoname);
        choice1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value){
                String student = Stuname.get(new_value.intValue()).toString();
                CoachVideoControl.setStudent(student);
            }
        });
        choice2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value){
                String video = Videoname.get(new_value.intValue()).toString();
                CoachVideoControl.setVideo(video);
            }
        });
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
