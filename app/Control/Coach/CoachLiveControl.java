package app.Control.Coach;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CoachLiveControl{

    public static String stuName;

    /**
     * This function is used to save the suggestions
     * put forward by the coach in the live broadcast,
     * and the trainees can view the training suggestions
     * put forward by the coach to them in the live broadcast
     *
     * @param ad
     */
    public void saveAdvice(String ad){
        File f= new java.io.File("Account\\Advice.txt");
        //Open the txt file where the comments are stored
        try{
            FileOutputStream fos = new FileOutputStream(f,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            //Write comments
            osw.write(ad.toString());
            osw.write("\r\n");
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
