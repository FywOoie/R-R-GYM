package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerRegisterControl implements Initializable {

    private appMain appmain;
    @FXML
    private TextField registerID;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordConfirm;

    @FXML
    private Label wrongPwdLabel;

    // This method is a button listener
    // It will goto the customer login UI if the button is clicked
    public void returnToLogin(MouseEvent mouseEvent) { appmain.gotoUserLoginUI(); }

    // This method is a button listener
    // The register information is confirmed if the button is clicked
    @FXML
    private Button ok;

    /**
     * This is the method for customer to register.
     */
    public void register(){
        String id = registerID.getText();//get user input of user name
        String pwd = password.getText();//get user input of password
        String pwdCon = passwordConfirm.getText();//get user input of password again
        CustomerAccount customerAccount = new CustomerAccount();
        if(passwordConfirm(pwd, pwdCon)==1)//determine if two passwords are identical
        {
            if(checkPwd(pwd)==1){//check if password is of valid format
                if(registerCheck(id, pwd, customerAccount)==1)//check if register process is successful
                {
                    appmain.gotoUserLoginUI();
                }
            }else{
                wrongPwdLabel.setText("6-to-10-digit-and-letter password!");
                wrongPwdLabel.setStyle("-fx-text-base-color:red;");
            }
        }else{
            wrongPwdLabel.setText("The two password inputs should be the same!");
            wrongPwdLabel.setStyle("-fx-text-base-color:red;");
        }

    }

    /**
     * This method is ued to check whether the two passwords are
     * the same.
     *
     * @param firstPwd The first password.
     * @param secondPwd The second password.
     * @return
     */
    public int passwordConfirm(String firstPwd,String secondPwd) {
        int same =0;
        if(firstPwd.equals(secondPwd))
        {
            same = 1;
        }
        return same;
    }

    /**
     * It is a function to check whether the register
     * process is successful.
     *
     * @param id user id
     * @param pwd user password
     *
     * @return 1 stands for operation success 0 stands for failed
     **/
    public int registerCheck(String id, String pwd, CustomerAccount account) {
        int isSucc = 0;
        boolean hasSameId = Boolean.FALSE;
        int uid = 1;
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        String idString = '"'+id+'"';
        account.setJsonObject(jsonObject, id, uid , pwd, 0.0, 0 ,new JsonArray(),"");
        File f= new java.io.File(ResourceData.customerFile);
        try {
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String str =null;
            if ((str = br.readLine()) == null) {
                FileOutputStream fos = new FileOutputStream(f,true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                osw.write(jsonObject.toString());
                osw.write("\r\n");
                osw.close();
                br.close();
                isSucc = 1;
                hasSameId = true;
            }
            else {
                do{
                    jsonObject1 = (JsonObject) jsonParser.parse(str);
                    if(uid+1>Integer.parseInt(jsonObject1.get("uid").toString()))
                    {
                        uid=uid+1;
                    }
                    else {
                        uid=Integer.parseInt(jsonObject1.get("uid").toString())+1;
                    }
                    if (idString.equals(jsonObject1.get("id").toString())) {
                        hasSameId = true;
                        break;
                    }
                }while ((str = br.readLine()) != null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.addProperty("uid", uid);
        if(!hasSameId)
        {
            try {
                FileOutputStream fos = new FileOutputStream(f,true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                osw.write(jsonObject.toString());
                osw.write("\r\n");
                osw.close();
                isSucc = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSucc;
    }

    //register account by pressing Enter
    public void registerEnter(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) register();
    }

    //register account by clicking button
    @FXML
    void registerAccount(MouseEvent event) {
        register();
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //check password. Return 1 if it is valid, 0 if it is not valid.
    public static int checkPwd(String pwd){
        int isValid = 1;//default return value is 1, any condition that is not satisfied, it will be changed to 0
        if((pwd.length()<6) || (pwd.length()>10)){
            //check for condition 1:be between six and ten characters long
            isValid=0;
        }else if(!isLetterDigit(pwd)){
            //check for condition 2:contain only letters and digits
            isValid=0;
        }else if(!hasDigit(pwd)||!hasLetter(pwd)){
            //check for condition 3:contain at least one letter and one digit
            isValid=0;
        }else if((pwd.contains(" ")) || !isLetterDigit(pwd)){
            //check for condition 4:cannot contain any other characters and cannot have any blank spaces
            isValid=0;
        }
        return isValid;
    }

    //using regular expression to check if it contains only letters and numbers
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    //using regular expression to check if it contains numbers
    public static boolean hasDigit(String str) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    //using regular expression to check if it contains letters
    public static boolean hasLetter(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    //using regular expression to check if it only contains numbers
    public static boolean isNumeric(String str){
        //using regular expression
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
}
