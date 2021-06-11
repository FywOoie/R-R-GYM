package app.Entity.Video;

import com.google.gson.JsonObject;

public class Videoinfo {
    private String StuName;
    private String VideoName;
    private String CoachName;
    private JsonObject jsonObject;

    public void setStuName(String StuName){ this.StuName = StuName; }
    public String getStuName(){ return this.StuName; };
    public void setVideoName(String VideoName){ this.VideoName = VideoName; }
    public String getVideoName(){ return this.VideoName; };
    public void setCoachName(String CoachName){ this.CoachName = CoachName; }
    public String getCoachName(){ return this.CoachName; }

    public void setJsonObject(JsonObject jsonObject, String StuName, String VideoName, String CoachName) {
        jsonObject.addProperty("StuName",StuName);
        jsonObject.addProperty("VideoName",VideoName);
        jsonObject.addProperty("CoachName",CoachName);
        this.jsonObject = jsonObject;
    }
    
}
