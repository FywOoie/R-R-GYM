package app.Control.Administrator;

import app.Control.Customer.CustomerRegisterControl;
import app.Control.Launch.appMain;
import app.Entity.Account.AdminAccount;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is the control class for the AdminCusetomerUI.
 * This class will control the customer procedure for the admin.
 * admin can add or change or delete or see coach info at UI
 */
public class AdminCustomerControl {
    private ArrayList<CustomerAccount> customerData = new ArrayList<>();

    /**
     * This function is to show the customer information
     * when the search button is clicked
     */
    public ArrayList<CustomerAccount> customerShow(String searchID){
        String line = "";//Temp string which stores
        customerData.clear();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.customerFile));//Open file
            while(true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Break while when EOF
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CustomerAccount customerAccount = new Gson().fromJson(appMain.jsonReader(line), CustomerAccount.class);
                if(searchID.equals("") ||(customerAccount.getId().equals(searchID))){//search by uid
                    customerData.add(customerAccount);
                }
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return customerData;
    }

    /**
     * Add coach by administrator's input.
     *
     * @param id Coach ID.
     * @param pwd Coach password.
     * @return
     */
    public int addCustomer(String id, String pwd){
        int flag = 0;
        if(CustomerRegisterControl.checkPwd(pwd) == 0){
            flag = 1;
        }else{
            AdminAccount adminAccount = new AdminAccount();
            int isAdded = adminAccount.addElement(2,id, pwd);//Add coach by function in entity class
            if(isAdded == 1){
                flag = 2;
            }else{
                flag = 3;
            }
        }
        return flag;
    }

    /**
     * Delete a customer based on uid provided by administrator.
     *
     * @param customerID Customer ID.
     * @return
     */
    public int deleteCustomer(String customerID){
        int flag = 0;
        if(!customerID.equals("")){
            if(!CustomerRegisterControl.isNumeric(customerID)){
                flag = 1;
            }else{
                AdminAccount adminAccount = new AdminAccount();
                //delete coach by uid
                int isDeleted = adminAccount.deleteElement(Integer.parseInt(customerID), new File(ResourceData.customerFile));
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
     * Change customer information based on searched customer ID.
     *
     * @param customerID Customer ID.
     */
    public int changeCustomer(String customerID, String selectedProperty, String info){
        int flag = 0;
        if(!customerID.equals("")){
            int isChanged = 0;
            AdminAccount adminAccount = new AdminAccount();
            if(!CustomerRegisterControl.isNumeric(customerID)){
                flag = 1;
            }
            else{
                int searchID = Integer.parseInt(customerID);

                //Change information based on selceted property
                switch(selectedProperty){
                    case "Name" :
                        isChanged = adminAccount.changeCustomer(searchID, info,"old",-1,-1,"old");
                        break;
                    case "Password" :
                        if(CustomerRegisterControl.checkPwd(info) == 1){
                            isChanged = adminAccount.changeCustomer(searchID,"old", info,-1,-1,"old");
                        }
                        break;
                    case "Balance" :
                        if(CustomerRegisterControl.isNumeric(info)){
                            double balanceTemp = Double.parseDouble(info);
                            isChanged = adminAccount.changeCustomer(searchID,"old","old",balanceTemp,-1,"old");
                        }else{
                            flag = 2;
                        }
                        break;
                    case "Membership":
                        if(CustomerRegisterControl.isNumeric(info)){
                            int memTemp = Integer.parseInt(info);
                            if(memTemp >= 0 && memTemp <= 2)
                                isChanged = adminAccount.changeCustomer(searchID,"old","old",-1,memTemp,"old");
                            else
                                flag = 3;
                        }else{
                            flag = 3;
                        }
                        break;
                }
                if(isChanged == 1){
                    flag = 4;
                }else{
                    flag = 5;
                }
            }
        }
        return flag;
    }
}
