package app.Control.Administrator;

import app.Control.Launch.appMain;
import app.Entity.Account.AdminAccount;
import app.Entity.Resource.ResourceData;
import app.Entity.Video.Video;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminVideoControl implements Initializable {
    private ArrayList<Video> videoData= new ArrayList<Video>();
    String[] candidateProperties = {"Name","UID","Category"};

    ObservableList<Video> data =
            FXCollections.observableArrayList();//store Video entity to show them in the table

    private appMain appmain;

    /**
     * This method is a button listener
     * It will show the coach information if the button is clicked
     * @param mouseEvent : mouseEvent
     */
    public void changeToCoachList(MouseEvent mouseEvent) {
        appmain.gotoCoachListUI();
    }

    /**
     * This method is a button listener
     * It will show the bill information if the button is clicked
     * @param mouseEvent: mouseEvent
     */
    public void changeToBillList(MouseEvent mouseEvent) { appmain.gotoBillListUI(); }

    /**
     * This method is a button listener
     * It will show the customer management if the button is clicked
     * @param mouseEvent
     */
    public void changeToCustomer(MouseEvent mouseEvent) {appmain.gotoAdminCustomerUI();}

    /**
     * This method is a button listener
     * It will log out current account if the button is clicked
     * @param mouseEvent
     */
    public void adminLogOut(MouseEvent mouseEvent) {
        appmain.gotoAdminLoginUI();
    }

    /**
     * This function is to show video info by Table view
     * when the admin click the search button
     */
    public ArrayList<Video> videoShow(String searchID, String selectedProperty){
        String line = "";//Temp string which stores
        videoData.clear();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.videoFile));//open file
            while(true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Video video = new Gson().fromJson(appMain.jsonReader(line), Video.class);
                switch(selectedProperty){//Search by different field properties
                    case "UID"://Search by uid
                        if(searchID.equals("") ||(video.getVideoID() == Integer.parseInt(searchID))){
                            videoData.add(video);
                        }
                        break;
                    case "Category"://Search by category
                        if(searchID.equals("") ||(video.getCategory().equals(searchID))){
                            videoData.add(video);
                        }
                        break;
                    default://Default search by video name
                        if(searchID.equals("") ||(video.getVideoName().equals(searchID))){
                            videoData.add(video);
                        }
                }
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //If no data is found, prompt admin
        return videoData;
    }

    /**
     * This function is to add video based on information provided by admin
     * when the admin put in strings in input fields
     */
    public int addVideo(String videoName, String category, String videoUrl, String videoCover){
        //Add a set on action of a event
        int flag = 0;
        int isAdded = 0;

        //Add json video text
        AdminAccount adminAccount = new AdminAccount();
        int isWrongInfor = this.checkAddingInfor(videoName, category, videoUrl, videoCover);//check if valid input
        switch(isWrongInfor){
            case 1://Video category doesn't exist
                flag = 1;
                break;
            case 2://Video file doesn't exist
                flag = 2;
                break;
            case 3://Video cover file doesn't exist
                flag = 3;
                break;
            default:
                isAdded = adminAccount.createVideo(videoName, category, videoUrl, videoCover);//Add to json file
                //Give information about if it is added successfully
                if(isAdded == 1){
                    flag = 4;
                }else{
                    flag = 5;
                }
        }
        return flag;
    }

    /**
     * This function is to delete a video based on videoID provided by admin
     *
     * @param videoId The video ID.
     * @return
     */
    public int deleteVideo(String videoId){
        int flag = 0;
        if(!videoId.equals("")){
            AdminAccount adminAccount = new AdminAccount();
            JsonObject jsonObject = new JsonObject();
            JsonParser jsonParser = new JsonParser();
            boolean isDeletedFile = false;
            boolean isDeletedCover = false;

            File f = new File(ResourceData.videoFile);
            //Delete video source
            try {
                String line = null;//Temp string used to store lines in the file
                String deleteUrl = "";//Deleted video path
                String deleteCover = "";//Deleted video cover path
                FileInputStream fis = new FileInputStream(f);
                InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(reader);

                while ((line = br.readLine()) != null) {
                    jsonObject = (JsonObject) jsonParser.parse(line);//parse json to object
                    //Get url and cover by video id
                    if(videoId.equals(jsonObject.get("videoID").toString())){
                        deleteUrl = jsonObject.get("videoUrl").toString();
                        deleteCover = jsonObject.get("videoCover").toString();
                    }
                }
                br.close();
                fis.close();
                reader.close();

                //Delete file source in the file system
                File fVideo = new File(deleteUrl.replace("\"",""));
                File fCover = new File(deleteCover.replace("\"",""));
                isDeletedFile = fVideo.delete();
                isDeletedCover = fCover.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Delete json video text
            int isDeleted = adminAccount.deleteElement(Integer.parseInt(videoId),new File(ResourceData.videoFile));
            //Judge if all deleting successfully
            if((isDeleted == 1) && isDeletedFile && isDeletedCover){
                flag = 1;

            }else if(isDeleted != 1){
                flag = 2;
            }else if(!isDeletedFile){
                flag = 3;
            }else{
                flag = 4;
            }
        }
        return flag;
    }

    /**
     * This function is to check if information given by admin is correct
     * @param videoName:video name of the video
     * @param category:category of the video
     * @param videoUrl:path of the video
     * @param videoCover:cover path of the video
     * @return isWrongInfor:if the information isnot wrong, return 0;
     *                      if the category is wrong, return 1;
     *                      if the video path is wrong, return 2;
     *                      if the video cover path is wrong, return 3;
     */
    public int checkAddingInfor(String videoName, String category,String videoUrl,String videoCover){
        int isWrongInfor = 0;//Default set there is no wrong information

        if(this.checkCategory(category) == 0)  isWrongInfor = 1;
        else if(!this.checkFile(videoUrl,"video"))  isWrongInfor = 2;
        else if(!this.checkFile(videoCover,"image"))  isWrongInfor = 3;

        return isWrongInfor;
    }

    /**
     * This function is to check if category is in the category list
     * @param category:input string to be checked if it is a string
     * @return : if the category exsits, return 1. Else return 0
     */
    public int checkCategory(String category){
        if(ResourceData.categoryList.contains(category)) return 1;
        else return 0;
    }

    /**
     * This function is to check if the file given by admin
     * exists and of correct format
     * @param fileName:file name of the file
     * @param fileType:video or image.
     * @return: return true if the file is valid, else return false
     */
    public boolean checkFile(String fileName,String fileType){
        //check file existence and video or image format
        boolean isValid = true;
        File f = new File(fileName);
        if(!f.exists()) isValid = false;
        switch (fileType){
            case "image":
                if(!isImage(fileName)) isValid = false;
                break;
            case "video":
                if(!isVideo(fileName)) isValid = false;
                break;
        }
        return isValid;
    }

    /**
     * check if the input is an image file
     * @param name:Path name of the image including the postfix
     * @return :return true if the file is valid, else return false
     */
    public boolean isImage(String name){
        String[] validPostfix = {".png",".jpg",".jpeg",".tiff",".bmp"};//Common image file postfix
        boolean ans = false;
        for (String postfix : validPostfix) {
            if (name.contains(postfix)) {//Contain common postfix
                ans = true;
                break;
            }
        }
        return ans;
    }

    /**
     * This function is to check if the input is an video file
     * @param name:the video name of the video
     * @return: return true if the file is valid, else return false
     */
    public boolean isVideo(String name){
        String[] validPostfix = {".avi",".wmv",".mov",".mp4",".mkv",".flv"};//Common video file postfix
        boolean ans = false;
        for (String postfix : validPostfix) {
            if (name.contains(postfix)) {//Contain common postfix
                ans = true;
                break;
            }
        }
        return ans;
    }

    public void setApp(appMain appmain) { this.appmain = appmain;}

    //Interface initialization
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
