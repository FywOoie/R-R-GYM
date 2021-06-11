package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerVideoControl;
import app.Control.Launch.appMain;
import app.Entity.Video.Videoinfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerVideoUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TableView<Videoinfo> StuTable;
    @FXML
    private TableColumn<Videoinfo,String> CoaCol;
    @FXML
    private TableColumn<Videoinfo,String> VidCol;
    ObservableList<Videoinfo> data =
            FXCollections.observableArrayList();//store CoachAccount entity to show them in the table

    public void backLabelClick(MouseEvent event) {
        appmain.gotoUserUI();
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This method is used to get the information of the video.
     * The searched information will be listed in the table.
     */
    public void getInfo() {
        ArrayList<Videoinfo> videoData= new ArrayList<Videoinfo>();
        CustomerVideoControl cusVideoCtrl = new CustomerVideoControl();
        videoData = cusVideoCtrl.getInfo();
        data.clear();
        if(videoData.size() != 0){
            for(int i = 0;i < videoData.size();i++){
                data.add(videoData.get(i));
            }
            StuTable.setItems(data);//push data into table
            CoaCol.setCellValueFactory(new PropertyValueFactory<>("CoachName"));
            VidCol.setCellValueFactory(new PropertyValueFactory<>("VideoName"));
        }
    }
}
