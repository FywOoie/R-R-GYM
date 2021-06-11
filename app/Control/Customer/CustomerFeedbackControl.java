package app.Control.Customer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * This class is the control class for the feedback.
 * This class will control and save the text of feedback.
 */
public class CustomerFeedbackControl {
    /**
     * This method is used to save the feedback from the user.
     *
     * @param text The feedback information from user.
     */
    public void saveAdvise(String text){
        if(text != null){
            File f= new java.io.File("Account\\feedback.txt");
            try{
                FileOutputStream fos = new FileOutputStream(f,true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(text);
                osw.write("\r\n");
                osw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
