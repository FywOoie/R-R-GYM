package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Entity.Account.CurrentAccount;
import app.Entity.Video.Videoinfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class CustomerVideoControl {
    private ArrayList<Videoinfo> videoData= new ArrayList<>();
    public ArrayList<Videoinfo> getInfo(){
        videoData.clear();
        String student = CurrentAccount.getCurAccount().getId();
        File f = new File("Account\\StudentVideo.txt");
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if(Objects.equals(student, appMain.jsonReader(str).get("StuName").toString().replace("\"", ""))){
                    Videoinfo info = new Gson().fromJson(appMain.jsonReader(str), Videoinfo.class);
                    videoData.add(info);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoData;
    }
}
