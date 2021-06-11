package app.Control.Administrator;

import app.Control.Customer.CustomerRegisterControl;
import app.Control.Launch.appMain;
import app.Entity.Resource.ResourceData;
import app.Entity.Transaction.Bill;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  This class is the control class for the AdminBIllUI.
 *  This class will control the bill and promotion procedure for the admin.
 *  admin can look at the bill records
 */
public class AdminBillControl {
    private ArrayList<Bill> billData = new ArrayList<>();
    private appMain appmain = new appMain();

    /**
     * This function is to show the bill information
     * when the search button is clicked
     */
    public ArrayList<Bill> billShow(String searchID){
        String line = "";//Temp string which stores
        billData.clear();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.billFile));//Open file
            if(CustomerRegisterControl.isNumeric(searchID) || searchID.equals("")){
                while(true) {
                    try {
                        if ((line = bufferedReader.readLine()) == null) break;//Break while when EOF
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bill bill = new Gson().fromJson(appMain.jsonReader(line), Bill.class);//Transform json to Bill entity

                    //No search uid
                    if(searchID.equals("") ||(bill.getUid() == Integer.parseInt(searchID))){
                        billData.add(bill);
                    }
                }
                bufferedReader.close();
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return billData;
    }
}
