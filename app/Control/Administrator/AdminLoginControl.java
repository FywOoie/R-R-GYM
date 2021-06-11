package app.Control.Administrator;

import app.Control.Launch.appMain;
import app.Control.Share.LoginControl;
import app.Entity.Account.AdminAccount;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is the control class for the AdminLoginUI.
 * This class will control the login process for the administrator.
 * It also controls the action of the elements in the AdminLoginUI panel.
 */
public class AdminLoginControl extends LoginControl {

    /**
     * This method is used by the administrator to log in.
     * The method will match the input administrator ID and
     * password with the record in back end. If the administrator
     * inputs the correct ID and password, he will successfully
     * login the system, or he has to input the ID and password again.
     */
    public boolean login(String id, String pwd){
        AdminAccount adminAccount = new AdminAccount(); // Create an account for the administrator.
        // Check whether the input ID and password are correct.
        if(signin(ResourceData.adminFile, id, pwd)==1)
        {
            String line = "";
            try {
                // Read the record from the file.
                BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.adminFile));
                while(true) {
                    if ((line = bufferedReader.readLine()) == null) {
                        break;
                    }
                    adminAccount = new Gson().fromJson(appMain.jsonReader(line), AdminAccount.class);
                    // Get the record corresponding to the input administrator ID.
                    if(adminAccount.getId().equals(id)){
                        // Set the current active account.
                        CurrentAccount.setCurAccount(adminAccount);
                        break;
                    }
                }
                bufferedReader.close();
                return true;

            }
            catch (Exception e){
            }
        }
        else{
            return false;
        }
        return false;
    }

}
