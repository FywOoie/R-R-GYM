package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Control.Share.LoginControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is the control class for the CustomerLoginUI.
 * This class will control the login process for the customer.
 * It also controls the action of the elements in the CustomerLoginUI panel.
 */
public class CustomerLoginControl extends LoginControl {

    /**
     * This method is used by the customer to log in.
     * The method will match the input customer ID and
     * password with the record in back end. If the customer
     * inputs the correct ID and password, he will successfully
     * login the system, or he has to input the ID and password again.
     */
    public boolean login(String id, String pwd){
        boolean flag = false;
        CustomerAccount customerAccount = new CustomerAccount(); // Create an account for the customer.
        // Check whether the input ID and password are correct.
        if(signin(ResourceData.customerFile, id, pwd) == 1){
            String line;
            try {
                // Read the record from the file.
                BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.customerFile));
                while(true) {
                    if ((line = bufferedReader.readLine()) == null) {
                        break;
                    }
                    customerAccount = new Gson().fromJson(appMain.jsonReader(line), CustomerAccount.class);
                    // Get the record corresponding to the input customer ID.
                    if(customerAccount.getId().equals(id)){
                        // Set the current active account.
                        CurrentAccount.setCurAccount(customerAccount);
                        ResourceData.uid = customerAccount.getUid();
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
        return flag;
    }
}
