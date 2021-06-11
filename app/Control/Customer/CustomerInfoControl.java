package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.AdminAccount;
import app.Entity.Account.CoachAccount;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CustomerInfoControl implements Initializable {

    private appMain appmain;
    @FXML
    private TextArea faList;
    @FXML
    private ChoiceBox<String> chooseMode;
    @FXML
    private ChoiceBox<String> chooseCoach;
    @FXML
    private Label coachLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label memLabel;
    @FXML
    private Label posLabel;
    @FXML
    private Label changeLabel;

    private String selectedPosition = "";
    private final String[] position = {"Chest","Arm","Back","Leg"};
    private String selectedCoach = "";
    private final String[] membershipIndex = {"non-membership","basic membership","premier membership"};

    public void select(int functionFlag){
        if(ConfirmControl.getConfirmFlag()){
            switch (functionFlag){
                case 1:
                    userLogOutExe();
                    break;
            }
        }
    }

    /**
     * Change customer information based on the user choice
     * @param event:mouse event
     */
    @FXML
    void changeInfo(MouseEvent event) {
        if(nameLabel.getText() != null) {//Name label should not be empty
            AdminAccount adminAccount = new AdminAccount();
            CustomerAccount customerAccount = (CustomerAccount) CurrentAccount.getCurAccount();//Get current log in account

            //Change information by admin account
            int isChanged = adminAccount.changeCustomer(customerAccount.getUid(),customerAccount.getId(),
                    customerAccount.getPwd(),customerAccount.getBalance(),customerAccount.getMembership(),selectedPosition);
            if((isChanged == 1) && !selectedPosition.equals(customerAccount.getPosition())){
                //Successful prompt
                changeLabel.setTextFill(Color.GREEN);
                changeLabel.setText("Change Successfully!");
                customerAccount.setPosition(selectedPosition);
                posLabel.setText(customerAccount.getPosition());
                coachLabel.setText(behStudentCoach(1,""));
            }else if((isChanged == 1)) {
                //empty block that will do nothing
            }else{
                    //Error handling
                    changeLabel.setTextFill(Color.RED);
                    changeLabel.setText("Name already exists!");
            }

            chooseMode.getSelectionModel().select(selectedCoach);
            this.behStudentCoach(2,chooseCoach.getSelectionModel().getSelectedItem());
            coachLabel.setText(behStudentCoach(1,""));
        }else{
            changeLabel.setTextFill(Color.RED);
            changeLabel.setText("Empty Name!");
        }
    }

    @FXML
    void changePassword(MouseEvent mouseEvent){
        appmain.gotoChangePasswordUI();
    }

    /**
     * This function is called by appMain
     * to initialize the whole page
     * behaviors included: Set personal information
     */
    public void initializeInfo(){
        selectedPosition = CurrentAccount.getCurAccount().getPosition();
        CustomerAccount customerAccount = CustomerVideoPlayControl.searchCustomerByName(CurrentAccount.getCurAccount().getUid());//Get current account
        //Set labels to personal information
        nameLabel.setText(customerAccount.getId());
        balanceLabel.setText(String.valueOf(ResourceData.controlNumber(customerAccount.getBalance())));
        memLabel.setText(membershipIndex[customerAccount.getMembership()]);
        posLabel.setText(customerAccount.getPosition());

        //Initialize choose boxes
        chooseMode.getItems().addAll(position);
        chooseMode.getSelectionModel().select(CurrentAccount.getCurAccount().getPosition());
        chooseMode.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value){
                selectedPosition = position[new_value.intValue()];//Change choice box selection
            }
        });

        //Initialize coach
        if(this.getCoachList().size() != 0){
            for(int i = 0; i < getCoachList().size(); i++)
                chooseCoach.getItems().add(getCoachList().get(i));//Add to choice box
            String coach = this.behStudentCoach(1,"");

            if(coach != null){//If the customer has a coach
                chooseCoach.getSelectionModel().select(coach);
                coachLabel.setText(coach);
            }else{//If the customer doesn't have a coach
                chooseCoach.getSelectionModel().selectFirst();
            }
        }else{//No coach in the gym
            changeLabel.setTextFill(Color.RED);
            changeLabel.setText("There are no coaches!");
        }

        if((CurrentAccount.getCurAccount().getMembership() == 0) || (CurrentAccount.getCurAccount().getMembership() == 1)){
            chooseCoach.setVisible(false);
            coachLabel.setText("Premier privilege");
        }

        chooseCoach.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value){
                selectedCoach = getCoachList().get(new_value.intValue());
            }
        });
    }

    /**
     * This function is to show the fav list of the customer
     * when the customer click the show button
     */
    public void favShow(){
        String line = "";//Temp string which stores
        LinkedList<String> linkedList = new LinkedList<>();
        StringBuilder printInfo = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.customerFile));//Open file
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read until null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CustomerAccount customerAccount = new Gson().fromJson(appMain.jsonReader(line), CustomerAccount.class);
                if(CurrentAccount.getCurAccount().getUid() == customerAccount.getUid()){//Found match
                    linkedList = customerAccount.getFavList();
                    for (String s : linkedList) {
                        printInfo.append(s).append("\r\n");//Append fav list to end and start a new line
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(String.valueOf(printInfo).equals(""))
            faList.setText("No Liked Videos");//If no like video of the user
        else
            faList.setText(String.valueOf(printInfo));//Show the favlist
    }

    public void userLogOutExe(){
        appmain.gotoUserLoginUI();
        CurrentAccount.setCurAccount(null);
    }

    // Return to the student login interface
    public void userLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }
    public void backLabelClick(MouseEvent event) {
        appmain.gotoUserUI();
    }
    public void changeToPurchase(){appmain.gotoChargeUI();}
    public void changeToMem(){appmain.gotoMemberShopUI();}
    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This function is to get the coach list from Coach.txt
     * @return ArrayList that contains all coaches
     */
    public ArrayList<String> getCoachList() {
        ArrayList<String> coachList = new ArrayList<>();//Coach list
        String line = "";//Temp reading string

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.coachFile));//Open file
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read until null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CoachAccount coachAccount = new Gson().fromJson(appMain.jsonReader(line), app.Entity.Account.CoachAccount.class);
                coachList.add(coachAccount.getId());//Add to coach list
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return coachList;
    }

    /**
     * Get the student's coach from file
     * @param mode:MODE 1~get the student's coach
     *             MODE 2~Change the student's coach
     * @param newCoach:MODE 1~set to ""
     *                 MODE 2~the new coach name
     * @return MODE 1~Coach's name
     *         MODE 2~""
     */
    public String behStudentCoach(int mode, String newCoach){
        String coach = "";//Used in MODE 1 to store the coach name
        String line = "";//Temp reading line
        StringBuilder coachInfo = new StringBuilder();//coach's info
        int isChanged = 0;//Used in MODE 2 to see if successfully changed the customer's coach

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = new JsonObject();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.customerCoachFile));//Open file
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read til null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jsonObject = (JsonObject) jsonParser.parse(line);//Parse line to json object

                if((mode == 1) && (jsonObject.get("stuID").toString().replace("\"","")
                        .equals(CurrentAccount.getCurAccount().getId()))){//MODE 1
                    coach = jsonObject.get("coachID").toString().replace("\"","");
                }else if(mode == 2){//MODE 2
                    if(jsonObject.get("stuID").toString().replace("\"","")
                            .equals(CurrentAccount.getCurAccount().getId())){
                        //Change personal coach
                        jsonObject.addProperty("stuID", CurrentAccount.getCurAccount().getId());
                        jsonObject.addProperty("coachID", newCoach);
                        jsonObject.addProperty("position", selectedPosition);
                        isChanged = 1;//Changed the customer's coach
                    }
                    coachInfo.append(jsonObject.toString()).append("\r\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();//Print error messages to the command line
        }

        //Didn't change the info when no mapping, add to mapping
        if((isChanged == 0) && (mode == 2)){
            JsonObject newJsonObject = new JsonObject();
            newJsonObject.addProperty("stuID", CurrentAccount.getCurAccount().getId());
            newJsonObject.addProperty("coachID", newCoach);
            newJsonObject.addProperty("position", CurrentAccount.getCurAccount().getPosition());
            coachInfo.append(newJsonObject.toString()).append("\r\n");
        }

        //MODE 2 write to mapping file
        if(mode == 2){
            PrintStream stream = null;
            try {
                stream = new PrintStream(ResourceData.customerCoachFile);
                stream.print(coachInfo);//Save to file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return coach;
    }
}

