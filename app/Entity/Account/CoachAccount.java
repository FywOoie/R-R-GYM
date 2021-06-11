package app.Entity.Account;

import app.Entity.Resource.ResourceData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class CoachAccount extends Account {

    ArrayList<CustomerAccount> myList = new ArrayList<CustomerAccount>();

    //get all students for the coach
    public HashMap<Integer, String> isStu(String coachID) {
        HashMap<Integer, String > myHashMap = new HashMap<>();
        String str1;
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        File f = new java.io.File(ResourceData.customerCoachFile);
        try {
            String str =null;
            int key = 0;
            String coachString= '"'+coachID+'"';
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                if(coachString.equals(jsonObject.get("coachID").toString())){
                    jsonObject1 = (JsonObject) jsonParser.parse(str);
                    str1 = jsonObject1.get("stuID").toString();
                    myHashMap.put(key,str1);
                    key++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myHashMap;
    }

    //list coach information
    public String listCoach(String id){
        String coachList = "";
        JsonObject jsonObject ;
        JsonParser jsonParser = new JsonParser();

        File f = new File(ResourceData.coachFile);
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                if(id.equals(jsonObject.get("id").toString().replace("\"",""))){
                    coachList = coachList + str + '\n';
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return coachList;
    }

}

