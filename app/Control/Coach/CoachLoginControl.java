package app.Control.Coach;

import app.Control.Launch.appMain;
import app.Control.Share.LoginControl;
import app.Entity.Account.CoachAccount;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is the control class for the CoachLoginUI.
 * This class will control the login process for the coach.
 * It also controls the action of the elements in the CoachLoginUI panel.
 */
public class CoachLoginControl extends LoginControl {

    /**
     * This method is used by the coach to log in.
     * The method will match the input coach ID and
     * password with the record in back end. If the coach
     * inputs the correct ID and password, he will successfully
     * login the system, or he has to input the ID and password again.
     */
    public boolean login(String id, String pwd){
        boolean flag = false;
        CoachAccount coachAccount = new CoachAccount(); // Create an account for the coach.
        // Check whether the input ID and password are correct.
        if(signin(ResourceData.coachFile, id, pwd)==1)
        {
            String line = "";
            try {
                // Read the record from the file.
                BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.coachFile));
                while(true) {
                    if ((line = bufferedReader.readLine()) == null) {
                        break;
                    }
                    coachAccount = new Gson().fromJson(appMain.jsonReader(line), CoachAccount.class);
                    // Get the record corresponding to the input administrator ID.
                    if(coachAccount.getId().equals(id)){
                        // Set the current active account.
                        CurrentAccount.setCurAccount(coachAccount);
                        break;
                    }
                }
                bufferedReader.close();
                flag = true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            // An notification when the administrator input the wrong user ID or password.
            flag = false;
        }
        return flag;
    }



}