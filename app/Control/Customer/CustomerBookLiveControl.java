package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Entity.Account.*;
import app.Entity.Live.*;
import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is the control class for the LiveBookUI.
 * This class will control the live-book procedure for the customer.
 * Customer can book or cancel a live session by double clicking the live record.
 * A confirm window will be shown to the customer before he confirms his choice.
 * It also controls the action of the elements in the LiveBookUI panel.
 */
public class CustomerBookLiveControl {
    private ArrayList<LiveSession> liveData = new ArrayList<>();

    /**
     * This method is called when the user has selected his choice.
     * This method is used to update the record in the table based
     * on the choice of the user.
     */
    public void setRowStyle(LiveSession rowDataClick, boolean select, String userSelectedDate){
        File f1 = new File("Account\\userLive.txt");
        File f2 = new File("Account\\LiveInfoForCoach.txt");
        // Get the liveID of the selected live session.
        int liveIDTemp = rowDataClick.getLiveID();
        if(select){
            // The live is selected.
            rowDataClick.liveSelect();
            rowDataClick.selectedLiveRecord(CurrentAccount.getCurAccount().getId(),userSelectedDate);
        }
        else{
            // The live is cancelled.
            rowDataClick.liveCancel();
            deleteElement(liveIDTemp,f2);
        }
        // Delete the live record.
        deleteElement(liveIDTemp,f1);
        // Update the live record.
        rowDataClick.liveRecordUser(CurrentAccount.getCurAccount().getId());
        // Show the updated information in the table.
        this.showLive(userSelectedDate);
    }

    /**
     * This method is used to copy from one file to another file.
     *
     * @param srcFile The source file.
     * @param desFile The destination file.
     */
    private void streamCopyFile(File srcFile, File desFile) {
        try {
            // Open two file streams.
            FileInputStream fi = new FileInputStream(srcFile);
            FileOutputStream fo = new FileOutputStream(desFile);
            int by = 0;
            while((by = fi.read()) != -1) {
                fo.write(by);
            }
            // Close the file stream.
            fi.close();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to delete specific live session record.
     * This method deletes the live session record in file {@code src}
     * based on the ID {@code liveID}. This method is used to update
     * the information of the live session record.
     *
     * @param liveID Unique ID of the live.
     * @param src The file containing the live session record.
     * @return
     */
    public int deleteElement(int liveID,File src) {
        int deleteStatus = 0;
        try{

            // Open two file streams.
            File f1 = new File(String.valueOf(src));
            File f2 = new File("Account\\liveTemp.txt");
            // Copy a new file as temp file.
            this.streamCopyFile(f1,f2);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(f2));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f1));
            // Line is a buffered text variable that will store information of one line.
            String line = "";
            while((line=bufferedReader.readLine())!=null){
                if(!line.contains(("\"liveID\":" + liveID))){
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }else {
                    deleteStatus = 1; // Delete the specific record.
                }
            }
            // Close file.
            bufferedReader.close();
            bufferedWriter.close();
            deleteFile("Account\\liveTemp.txt");
        }
        catch (Exception e){
            e.printStackTrace();
            deleteStatus = -1;
        }
        // Return -1 if error, 0 didn't search, 1 if successful.
        return deleteStatus;
    }

    /**
     * This method is used to delete a specified file.
     *
     * @param fileName The file to be deleted.
     */
    public void deleteFile(String fileName) {
        File file = new File(fileName);
        // If the file to which the file path corresponds exists and is a file, delete it directly.
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * This method is used to show the live information of a day.
     * This method is called if the user clicked the search button,
     * and the live information of the selected day will be shown to the user.
     *
     * @param selectedDate The selected day
     */
    public ArrayList<LiveSession> showLive(String selectedDate){
        liveData.clear();
        String line = ""; // Store the temporary read string.
        File f = new File("Account\\CustomerCoach.txt");
        String coachName = "";
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if (('"' + CurrentAccount.getCurAccount().getId() + '"').equals(appMain.jsonReader(str).get("stuID").toString())) {
                    coachName = appMain.jsonReader(str).get("coachID").toString();
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Account\\userLive.txt"));
            while(true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                        e.printStackTrace();
                }
                // Read from the file and obtain the LiveSession object.
                LiveSession live = new Gson().fromJson(appMain.jsonReader(line), LiveSession.class);
                // If the selected week is equal to the week in file, then add this row of data to the table.
                if(live.getLiveDate().equals(selectedDate) && ('"' + live.getCoachName() + '"').equals(coachName)){
                    liveData.add(live);
                }
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return liveData;
    }

    /**
     * This method is used to judge whether the selected date is valid.
     * User can only select the date after today.
     *
     * @param userDate Selected date.
     * @return Indicate the validation of the selected date.
     */
    public boolean judgeDate(String userDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = sdf.format(date);
        return userDate.compareTo(currentDate) < 0;
    }

    /**
     * This method is used to judge whether a live has been booked.
     * This method is implemented by checking the flag set in each
     * live entity.
     *
     * @param rowData The live entity.
     * @return
     */
    public boolean judge(LiveSession rowData){
        if(rowData.getSelected().equals("NO")){
             return false;
        }
        else{
            return true;
        }
    }

}
