package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.AdminAccount;
import app.Entity.Account.CurrentAccount;
import app.Entity.Account.CustomerAccount;
import app.Entity.Resource.ResourceData;
import app.Entity.Video.Video;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CustomerVideoPlayControl implements Initializable{
    private appMain appmain;
    @FXML
    private MediaView videoShow;
    @FXML
    private Label videoName;
    @FXML
    private Slider timeSlide;
    @FXML
    private Label currentTime;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button like;
    @FXML
    private Button cancel;
    @FXML
    private Label catLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label likeLabel;
    @FXML
    private Label nameLabel;

    private MediaPlayer mPlayer;
    private Media media;
    private String currentVideoName;

    /**
     * This function is to set the current video name
     * @param name : current video name
     */
    public void setCurrentVideoName(String name){
        this.currentVideoName = name;
    }

    public void select(int functionFlag){
        if(ConfirmControl.getConfirmFlag()){
            switch (functionFlag){
                case 1:
                    userLogOutExe();
                    break;
            }
        }
    }

    /**
     * This function is to add current video to fav list
     * @param event : Mouse event
     */
    @FXML
    void addFavoList(MouseEvent event) {
        //Add current video from fav list
        new CustomerAccount().addtoFavoList(CurrentAccount.getCurAccount().getUid(),videoName.getText());
        this.changeAddNum(1);//Increase by 1
        like.setVisible(false);//Set dislike icon visible, while set like icon invisible
        cancel.setVisible(true);
    }

    /**
     * This function is to remove the video from fav list
     * @param event : mosue envent
     */
    @FXML
    void removeFavoList(MouseEvent event) {
        //Remove current video from fav list
        new CustomerAccount().removeFavoList(CurrentAccount.getCurAccount().getUid(),videoName.getText());
        this.changeAddNum(-1);//Decrease by 1
        like.setVisible(true);//Set like icon visible, while set dislike icon invisible
        cancel.setVisible(false);
    }

    /**
     * This function is to add or substract one from
     * like number of the video's attribute.
     * @param i:1~add one;-1~decrease one
     */
    private void changeAddNum(int i) {
        Video video = searchVideoByName(currentVideoName);//Get current video entity
        AdminAccount adminAccount = new AdminAccount();
        //Change the like number of the video
        if((i == 1) || (i == -1)){
            adminAccount.changeVideo(video.getVideoID(),video.getVideoName(),video.getCategory(),video.getLikeNum()+i);
        }
        likeLabel.setText(String.valueOf(video.getLikeNum()+i));//Set like number added by 1
    }

    public void backLabelClick(MouseEvent event) {
        appmain.gotoUserUI();
    }

    public void userLogOutExe(){
        appmain.gotoUserLoginUI();
    }
    public void userLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    void playClick(MouseEvent event) {
        mPlayer.play();
        playButton.setVisible(false);
        pauseButton.setVisible(true);
    }

    @FXML
    void pauseClick(MouseEvent event) {
        mPlayer.pause();
        pauseButton.setVisible(false);
        playButton.setVisible(true);
    }

    public String DurationToString(Duration duration){
        int time = (int)duration.toSeconds();
        int hour = time /3600; // Get the the number of hours
        int minute = (time-hour*3600)/60; // Get the the number of minutes
        int second = time %60; // Get the the number of seconds
        return hour + ":" + minute + ":" + second; // Return the final time
    }

    /**
     * This function is to initialize VideoPlay interface
     * based on the parameters passed by the caller
     * @param url:the path of the video to be played
     * @param name:the name of the video entity
     */
    public void initializeVideo(String url,String name){
        this.setCurrentVideoName(name);//Set video name
        this.setVideoInfor(currentVideoName);//Set video info

        File video = new File(url);
        media = new Media(video.getAbsoluteFile().toURI().toString());//Open media
        mPlayer = new MediaPlayer(media);
        videoShow.setMediaPlayer(mPlayer);
        // Add action listener to the media player.
        // If the time of the video has changed, the time slider and time indicator will
        // change correspondingly.
        mPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                currentTime.setText(DurationToString(newValue) + " / " + DurationToString(mPlayer.getTotalDuration()));
                timeSlide.setValue(newValue.toSeconds() / mPlayer.getTotalDuration().toSeconds() * 100);
            }
        });
        // Add action listener to the media player.
        // If the time slider is dragged by the user, the time of the video will be set
        // according to the new position of the time slider.
        timeSlide.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double totalTime = media.getDuration().toMillis();
                // The new time for the video.
                double newTime = timeSlide.valueProperty().getValue()/100*totalTime;
                // Set new time for the video.
                mPlayer.seek(Duration.millis(newTime));
            }
        });

        //Get favorite list and show if the user like this video
        LinkedList<String> linkedList = searchCustomerByName(CurrentAccount.getCurAccount().getUid()).getFavList();
        if(linkedList.contains(name)){
            like.setVisible(false);
            cancel.setVisible(true);
        }else{
            like.setVisible(true);
            cancel.setVisible(false);
        }
    }

    /**
     * Set video information on GUI based on the Name
     * provided by the caller
     * @param name:Video name
     */
    public void setVideoInfor(String name) {
        videoName.setText(name); // Set video name to the video title.
        if(searchVideoByName(name) != null){
            Video video = searchVideoByName(name);//Get Video entity
            //Set all labels to according info
            nameLabel.setText(video.getVideoName());
            catLabel.setText(video.getCategory());
            likeLabel.setText(String.valueOf(video.getLikeNum()));
            dateLabel.setText(video.getCreateDate());
        }

    }

    /**
     * This function is to search the video based on the name
     * provided by the caller.
     * @param name : video name to search
     * @return Video : Video entity which is searched out by the
     *                  function.When didn't search, return null
     */
    public static Video searchVideoByName(String name){
        String line = "";//Temp string to store lines read
        Video video = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.videoFile));//open file
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read each line until null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Video videoTemp = new Gson().fromJson(appMain.jsonReader(line), Video.class);//Parse line to Video
                if(videoTemp.getVideoName().equals(name)){
                    video = videoTemp;//Found Video
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return video;
    }

    /**
     * This function is to search the customer based on the uid
     * provided by the caller.
     * @param uid : customer uid to search
     * @return CustomerAccount : CustomerAccount entity which is
     * searched out by the function.When didn't search, return null
     */
    public static CustomerAccount searchCustomerByName(int uid){
        String line = "";//Temp string to store lines read
        CustomerAccount customerAccountNow = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.customerFile));//open file
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read each line until null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CustomerAccount customerAccount = new Gson().fromJson(appMain.jsonReader(line), CustomerAccount.class);//Parse line to Video
                if(customerAccount.getUid() == uid){
                    customerAccountNow = customerAccount;//Found Customer
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerAccountNow;
    }

}
