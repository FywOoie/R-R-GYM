package app.Control.Customer;

import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomerChPwdControl {

    /**
     * This function is to check whether the old password you enter is correct
     * @return if the old password is wrong it will return false, otherwise it return true.
     */
    public int checkOldPwd(String opwd){
        int flag = 0;
        if(!opwd.equals(CurrentAccount.getCurAccount().getPwd())){
            flag = 1;
        }
        return flag;
    }

    /**
     * This function is to check whether the password is valid and the confirm password equals to the new password.
     * @return if password you enter above is wrong it will return false, otherwise it return true.
     */
    public int checkNewPwd(String npwd, String cpwd){
        int flag;
        if(CustomerRegisterControl.checkPwd(npwd) == 0){
            flag = 0;
            // false
        }else{
            if (npwd.equals(CurrentAccount.getCurAccount().getPwd())){
                flag = 1;
                // false
            }else {
                if (!npwd.equals(cpwd)){
                    flag = 3;
                    // false
                }else{
                    flag = 2;
                    // true
                }
            }
        }
        return flag;
    }

    /**
     * This function is to save the correct new password in the file
     */
    public void saveNewPwd(String nPwd){
        String oldAccount = "";//old pwd information
        String newAccount = "";//new pwd information
        JsonObject jsonObject;
        JsonParser jsonParser = new JsonParser();

        //discriminate what is needed to be changed and what is not
        File f = new File(ResourceData.customerFile);
        try {
            String str =null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                if(CurrentAccount.getCurAccount().getUid() == Integer.parseInt(jsonObject.get("uid").toString())){
                    jsonObject.addProperty("pwd", nPwd);
                    newAccount = jsonObject.toString() + "\r\n";
                }
                else{
                    oldAccount = oldAccount + jsonObject.toString() + "\r\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //save changed content to file
        PrintStream stream = null;
        try {
            stream=new PrintStream(ResourceData.customerFile);
            stream.print(oldAccount);
            stream.print(newAccount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
