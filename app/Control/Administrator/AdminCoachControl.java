package app.Control.Administrator;

import app.Control.Customer.CustomerRegisterControl;
import app.Control.Launch.appMain;
import app.Entity.Account.AdminAccount;
import app.Entity.Account.CoachAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is the control class for the AdminCoachUI.
 * This class will control the coach procedure for the admin.
 * admin can add or delete or see coach info at UI
 */
public class AdminCoachControl {
    private ArrayList<CoachAccount> coachData = new ArrayList<CoachAccount>();

    /**
     * Add coach by administrator's input
     */
    public int addCoach(String id, String pwd){
        int flag = 0;
        if(!(id.equals("") || pwd.equals(""))){
            if(CustomerRegisterControl.checkPwd(pwd) == 0){
                flag =  1;
            }
            else{
                AdminAccount adminAccount = new AdminAccount();
                int isAdded = adminAccount.addElement(1,id, pwd);//Add coach by function in entity class
                if(isAdded == 1){
                    flag =  2;
                }else{
                    flag =  3;
                }
            }
        }
        return flag;
    }

    /**
     * delete a coach based on uid provided by admin
     * @param coachID: The value of {@code coachID}
     */
    public int deleteCoach(String coachID){
        int flag = 0;
        if(!coachID.equals("")){
            if(!CustomerRegisterControl.isNumeric(coachID)){
                flag = 1;
            }else {
                AdminAccount adminAccount = new AdminAccount();
                //Delete coach by uid
                int isDeleted = adminAccount.deleteElement(Integer.parseInt(coachID), new File(ResourceData.coachFile));
                if(isDeleted == 1){
                    flag = 2;
                }else{
                    flag = 3;
                }
            }
        }
        return flag;
    }

    /**
     * This function is to show the coach information
     * when the search button is clicked
     */
    public ArrayList<CoachAccount> coachShow(String searchID){
        String line = "";//Temp string which stores
        coachData.clear();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.coachFile));//Open file
            while(true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Break while when EOF
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CoachAccount coach = new Gson().fromJson(appMain.jsonReader(line), CoachAccount.class);
                if(searchID.equals("") ||(coach.getId().equals(searchID))){//Search by uid
                    coachData.add(coach);
                }
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return coachData;
    }
}
