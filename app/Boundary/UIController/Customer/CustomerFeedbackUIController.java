package app.Boundary.UIController.Customer;

import app.Control.Customer.CustomerFeedbackControl;
import app.Control.Launch.appMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFeedbackUIController implements Initializable {
    private appMain appmain;
    @FXML
    private TextArea adviseText;

    /**
     * This method is a button listener
     * When the button is clicked, the page will save all the text you enter.
     *
     * @param mouseEvent
     */
    public void saveAdvise(MouseEvent mouseEvent){
        CustomerFeedbackControl cusFeedCtrl = new CustomerFeedbackControl();
        String text = adviseText.getText();
        cusFeedCtrl.saveAdvise(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void setApp(appMain appMain) {
        this.appmain = appmain;
    }
}
