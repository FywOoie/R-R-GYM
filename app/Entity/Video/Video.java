package app.Entity.Video;

import app.Entity.Resource.ReadFile;
import app.Entity.Resource.ResourceData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class is Video entity class that
 * have attributes of videos and methods related
 */
public class Video {
    //Attributes
    private String videoName;//video name
    private int videoID;//video ID
    private String createDate;//create date of video
    private String category;//coach of the video
    private String videoUrl;//String to represent content of video
    private String videoCover;//Cover of the video
    private int likeNum;//like number of this video
    private double videoRat;//the rating of the video
    private JsonObject jsonVideo;//json object

    //Constructors
    public Video() {}
    public Video(JsonObject jsonVideo,String videoName,int videoID, String category,String videoUrl,String videoCover){
        this.setVideoName(videoName);
        this.setVideoID(videoID);
        this.setCreateDate(ReadFile.getTime());
        this.setCategory(category);
        this.setVideoUrl(videoUrl);
        this.setVideoCover(videoCover);
        this.likeNum = 0;
        this.videoRat = 0;
        this.setJsonObject(jsonVideo,videoName,videoID,this.getCreateDate(),category,videoUrl,videoCover,likeNum,videoRat);
    }

    //Setters
    public void setJsonObject(JsonObject jsonVideo, String videoName,int videoID,String createDate, String category, String videoUrl, String videoCover,int likeNum,double videoRat) {
        this.jsonVideo = jsonVideo;
        jsonVideo.addProperty("videoName",videoName);
        jsonVideo.addProperty("videoID",videoID);
        jsonVideo.addProperty("createDate",createDate);
        jsonVideo.addProperty("category",category);
        jsonVideo.addProperty("videoUrl",videoUrl);
        jsonVideo.addProperty("videoCover",videoCover);
        jsonVideo.addProperty("likeNum",likeNum);
        jsonVideo.addProperty("videoRat",videoRat);
    }
    public void setVideoName(String videoName){
        this.videoName = videoName;
    }
    public String getVideoName(){return this.videoName;}
    public void setVideoID(int videoID) { this.videoID = videoID; }
    public int getVideoID(){return this.videoID;}
    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory(){return this.category;}
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getVideoUrl(){return this.videoUrl;}
    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }
    public String getVideoCover(){return this.videoCover;}
    public void setLikeNum(int likeNum){this.likeNum = likeNum;}
    public int getLikeNum(){return this.likeNum;}
    public void setVideoRat(double videoRat){this.videoRat = videoRat;}
    public double getVideoRat(){return this.videoRat;}

    /**
     * save the video record to file
     * @param jsonVideo :json video to be saved
     * @return isSaved:1 if saved successfully, 0 if failed
     */
    public int saveVideo(JsonObject jsonVideo) {
        int isSaved = 0;//Return value, default set as 0
        //Save video in format of json
        File f = new File(ResourceData.videoFile);//open file
        try {
            FileOutputStream fos = new FileOutputStream(f,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            //Write video information as Json format in one line
            osw.write(jsonVideo.toString());
            osw.write("\r\n");
            osw.close();
            isSaved = 1;//Save successfully
        } catch (Exception e) {
            e.printStackTrace();//Catch error
        }
        return isSaved;
    }

    public int addtoVideoCategory(String category){
        int i = 0;
        boolean has = true;
        File f = new File("Account\\PropertyList.txt");
        String newInfo="";
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        String str =null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                JsonArray jsonArray = (JsonArray) jsonObject.get("videoCategory");
                for (int j = 0; j < ResourceData.categoryList.size(); j++) {
                    if(!(category.equals(ResourceData.categoryList.get(j))))
                    {
                        has = false;
                    }
                    else {
                        has = true;
                        break;
                    }
                }
                if(!has)
                {
                    jsonArray.add(category);
                    ResourceData.categoryList.add(category);
                    jsonObject.add("videoCategory",jsonArray);
                    newInfo = jsonObject.toString();
                    i = 1;
                }
                else {
                    newInfo=jsonObject.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintStream stream = null;
        try {
            stream = new PrintStream("Account\\PropertyList.txt");//
            stream.print(newInfo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int removetoVideoCategory(String category){
        int i = 0;
        boolean has = true;
        File f = new File("Account\\PropertyList.txt");
        String newInfo = "";//
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        String str = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                JsonArray jsonArray = (JsonArray) jsonObject.get("videoCategory");
                for (int j = 0; j < ResourceData.categoryList.size(); j++) {
                    if(category.equals(ResourceData.categoryList.get(j)))
                    {
                        jsonArray.remove(j);
                        ResourceData.categoryList.remove(category);
                        jsonObject.add("videoCategory",jsonArray);
                        newInfo = jsonObject.toString();
                        i=1;
                    }
                    else {
                        newInfo = jsonObject.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintStream stream = null;
        try {
            stream = new PrintStream("Account\\PropertyList.txt");//
            stream.print(newInfo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }

}
