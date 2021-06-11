package app.Entity.Transaction;

import app.Entity.Resource.ReadFile;
import app.Entity.Resource.ResourceData;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * This class is an entity class for user account's charge record.
 * This class is mainly used to store and check the bill list of users.
 */
public class Bill {
    //Attributes
    private int uid;//user id
    private String buyTime;//system time of user bill change
    private double amount;//amount of user bill change
    private JsonObject jsonObject;//an object stores the information above, and save it in a file

    /**
     * Constructor
     */
    public Bill(int uid, double amount){
        //another constructor of object
        this.setUid(uid);
        this.setAmount(amount);
        this.setBuyTime(ReadFile.getTime());
        this.saveFile();
    }

    public Bill() {}

    //Getters and setters
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return this.amount;
    }
    public void setBuyTime(String buyTime){
        this.buyTime = buyTime;
    }
    public String getBuyTime(){
        return this.buyTime;
    }

    /**
     * the function is to make a JsonObject store all the information
     * @param jsonObject is the jsonobject we want to modify.
     */
    public void setJsonObject(JsonObject jsonObject, int uid, double amount, String buyTime) {
        //store the user bill information in the jsonObject.
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("amount",amount);
        jsonObject.addProperty("buyTime",buyTime);
        this.jsonObject = jsonObject;
    }

    /**
     * the function is to save the jsonobject in the file
     */
    //save the jsonObject in the txt file
    public void saveFile(){
        JsonObject jsonObject = new JsonObject();
        this.setJsonObject(jsonObject, uid, amount, buyTime);
        File f= new java.io.File(ResourceData.billFile);
        try{
            FileOutputStream fos = new FileOutputStream(f,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            osw.write(jsonObject.toString());
            osw.write("\r\n");
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
