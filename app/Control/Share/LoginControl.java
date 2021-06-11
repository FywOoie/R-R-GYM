package app.Control.Share;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginControl {
    /**
     *
     * @param filepath its database
     * @param id user id
     * @param pwd user password
     *
     *
     *
     * @return 1 stands for operation success 0 stands for failed
     **/
    public int signin(String filepath, String id, String pwd){
        int isCorrect = 0;
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        File f = new File(filepath);
        try {
            String str =null;
            String idString = '"'+id+'"';
            String pwdString= '"'+pwd+'"';
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                if(idString.equals(jsonObject.get("id").toString())&&pwdString.equals(jsonObject.get("pwd").toString())){
                    isCorrect = 1;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCorrect;
    }
}
