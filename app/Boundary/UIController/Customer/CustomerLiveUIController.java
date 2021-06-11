package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerLiveControl;
import app.Control.Launch.appMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerLiveUIController implements Initializable {

    private appMain appmain;
    @FXML
    private Text textarea;

    /**
     * This method is designed for the simulation of the live for customers.
     * The live panel will show the advice from the specific coach.
     *
     */
    public void adviceShow(){
        CustomerLiveControl cusLiveCtrl = new CustomerLiveControl();
        String adviceInfo = cusLiveCtrl.showContent("Account\\Advice.txt");
        textarea.setText(adviceInfo);//set the text according to the content
    }

    /**
     * This method is a label listener.
     * It will goto the previous UI if the label is clicked.
     * @param event
     */
    public void backLabelClick(MouseEvent event) {
        appmain.gotoUserUI();
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    public void liveInitial(){
        adviceShow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
