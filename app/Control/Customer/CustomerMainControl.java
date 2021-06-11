package app.Control.Customer;

import app.Control.Launch.appMain;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ResourceData;
import app.Entity.Video.Video;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CustomerMainControl implements Initializable{

    private appMain appmain;
    private double scrollLong = 0;
    private double scrollLong1 = 0;

    @FXML
    private Label freePrompt;
    @FXML
    private Label chargePrompt;
    @FXML
    private Button bookLiveButton;
    @FXML
    private Pane livePane;
    @FXML
    private Button left;
    @FXML
    private Button left1;
    @FXML
    private Button right;
    @FXML
    private Button right1;
    @FXML
    private Label noCoach;
    @FXML
    private Label yogaCatLabel;
    @FXML
    private Label fitCatLabel;
    @FXML
    private Label userIconLabel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private ImageView fitImage1,fitImage2,fitImage3,fitImage4,fitImage5,fitImage6,fitImage7,fitImage8,fitImage9;
    @FXML
    private ImageView yogaImage1,yogaImage2,yogaImage3,yogaImage4,yogaImage5,yogaImage6,yogaImage7,yogaImage8,yogaImage9;
    @FXML
    private Label fitLabel1,fitLabel2,fitLabel3,fitLabel4,fitLabel5,fitLabel6,fitLabel7,fitLabel8,fitLabel9;
    @FXML
    private Label yogaLabel1,yogaLabel2,yogaLabel3,yogaLabel4,yogaLabel5,yogaLabel6,yogaLabel7,yogaLabel8,yogaLabel9;

    ArrayList<ImageView> fitImages = new ArrayList<>();
    ArrayList<Label> fitLabel = new ArrayList<>();

    ArrayList<ImageView> yogaImages = new ArrayList<>();
    ArrayList<Label> yogaLabel = new ArrayList<>();

    @FXML
    void feedBack(MouseEvent mouseEvent){
        appmain.gotoFeedBackUI();
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

    public void userLogOutExe(){
        appmain.gotoUserLoginUI();
        CurrentAccount.setCurAccount(null);
    }

    public void userLogOut(MouseEvent mouseEvent) {
        appmain.gotoConfirmUI("Are you sure to log out?",1, this.getClass(),this);
    }

    public void memberClick(MouseEvent mouseEvent) {
        appmain.gotoMemberShopUI();
    }

    /**
     * This function is to get the list(cover path, name list,
     * or video path) from the video file
     * @param element:get cover path, name list, or video path
     * @param category:specify which type of video(category)
     * @return list of cover path, name list, or video path
     */
    public ArrayList<String> getElement(String element,String category){
        String line = "";//Temp string which stores
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceData.videoFile));//Open file
            while(true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;//Read til null
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Video video = new Gson().fromJson(appMain.jsonReader(line), Video.class);

                //When category matches
                if(video.getCategory().equals(category)){
                    //Get what kind of list
                    switch (element){
                        case "cover":
                            arrayList.add(video.getVideoCover());
                            break;
                        case "name":
                            arrayList.add(video.getVideoName());
                            break;
                        case "url":
                            arrayList.add(video.getVideoUrl());
                            break;
                    }
                }
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public void liveClick(MouseEvent mouseEvent) {
        CustomerInfoControl customerInfoControl = new CustomerInfoControl();
        if(customerInfoControl.behStudentCoach(1,"").equals("")){
            noCoach.setText("No personal coach!");
        }else{
            appmain.gotoCustomerLiveUI();
        }
    }

    public void infoClick(MouseEvent mouseEvent) { appmain.gotoCustomerInfoUI(); }

    public void setApp(appMain appmain){
        this.appmain = appmain;
    }

    public void scrollToLeft(MouseEvent mouseEvent) {

        if(scrollLong>0)
            scrollLong = scrollLong - 0.2;
        scrollPane.setHvalue(scrollLong);
    }

    public void scrollToRight(MouseEvent mouseEvent) {
        if (scrollLong<1)
            scrollLong = scrollLong + 0.2;
        scrollPane.setHvalue(scrollLong);
    }

    public void scrollToLeft1(MouseEvent mouseEvent) {
        if(scrollLong1>0)
            scrollLong1 = scrollLong1 - 0.2;
        scrollPane1.setHvalue(scrollLong1);
    }

    public void scrollToRight1(MouseEvent mouseEvent) {
        if (scrollLong1<1)
            scrollLong1 = scrollLong1 + 0.2;
        scrollPane1.setHvalue(scrollLong1);
    }

    public void hideScrollBar(){
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * This is the function which is called when intilize the
     * whole page and add video components to the GUI.
     */
    public void addToArrayList(){
        addLabel(fitLabel, fitLabel1, fitLabel2, fitLabel3, fitLabel4, fitLabel5, fitLabel6, fitLabel7, fitLabel8,
                fitLabel9, fitImages, fitImage1, fitImage2, fitImage3, fitImage4, fitImage5, fitImage6, fitImage7,
                fitImage8, fitImage9);//add fitness category

        addLabel(yogaLabel, yogaLabel1, yogaLabel2, yogaLabel3, yogaLabel4, yogaLabel5, yogaLabel6, yogaLabel7,
                yogaLabel8, yogaLabel9, yogaImages, yogaImage1, yogaImage2, yogaImage3, yogaImage4, yogaImage5,
                yogaImage6, yogaImage7, yogaImage8, yogaImage9);
    }

    /**
     * This is the function which is called when intilize the
     * whole page and add video components to the arrayList
     * which stores all the components.
     */
    public void addLabel(ArrayList<Label> fitLabel, Label fitLabel1, Label fitLabel2, Label fitLabel3, Label fitLabel4,
                         Label fitLabel5, Label fitLabel6, Label fitLabel7, Label fitLabel8, Label fitLabel9,
                         ArrayList<ImageView> fitImages, ImageView fitImage1, ImageView fitImage2, ImageView fitImage3,
                         ImageView fitImage4, ImageView fitImage5, ImageView fitImage6, ImageView fitImage7,
                         ImageView fitImage8, ImageView fitImage9) {
        fitLabel.add(fitLabel1);
        fitLabel.add(fitLabel2);
        fitLabel.add(fitLabel3);
        fitLabel.add(fitLabel4);
        fitLabel.add(fitLabel5);
        fitLabel.add(fitLabel6);
        fitLabel.add(fitLabel7);
        fitLabel.add(fitLabel8);
        fitLabel.add(fitLabel9);

        fitImages.add(fitImage1);
        fitImages.add(fitImage2);
        fitImages.add(fitImage3);
        fitImages.add(fitImage4);
        fitImages.add(fitImage5);
        fitImages.add(fitImage6);
        fitImages.add(fitImage7);
        fitImages.add(fitImage8);
        fitImages.add(fitImage9);
    }

    /**
     * This is the initialize function of the whole
     * customer page which is called by appMain to
     * initailze video components according to the
     * membership level
     */
    public void initializeCustomer(){
        userIconLabel.setText(CurrentAccount.getCurAccount().getId());

        int memLevel = CurrentAccount.getCurAccount().getMembership();
        if(memLevel == 0){
            authorityElements(false,false,true,false,true,false,true,false,false,true,true,true);
        }else if(memLevel == 1){
            authorityElements(false,false,true,true,true,true,true,true,true,true,true,false);
        }else if(memLevel == 2){
            authorityElements(true,true,true,true,true,true,true,true,true,true,false,false);
        }

        this.addToArrayList();//Add elements to arraylist
        //Fitness category
        ArrayList<String> coverListFit = this.getElement("cover","fit");
        ArrayList<String> nameListFit = this.getElement("name","fit");
        ArrayList<String> urlListFit = this.getElement("url","fit");
        //Yoga category
        ArrayList<String> coverListYoga = this.getElement("cover","yoga");
        ArrayList<String> nameListYoga = this.getElement("name","yoga");
        ArrayList<String> urlListYoga = this.getElement("url","yoga");

        //Add elements to the interface
        addVideoToInterface(coverListFit, nameListFit, urlListFit, fitImages, fitLabel);
        addVideoToInterface(coverListYoga, nameListYoga, urlListYoga, yogaImages, yogaLabel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Add element to the interface
     * @param coverList :all video cover path
     * @param nameList :all video name list
     * @param urlList :all video file list
     * @param images :all imageView which contains video cover list
     * @param label :all labels which contains video name list
     */
    public void addVideoToInterface(ArrayList<String> coverList, ArrayList<String> nameList, ArrayList<String> urlList, ArrayList<ImageView> images, ArrayList<Label> label) {
        for(int i=0;i<coverList.size();i++) {
            Image img = new Image("file:" + String.valueOf(coverList.get(i)));//get image name and create new Image entity
            images.get(i).setImage(img);//set the image entity to imageview
            int finalI = i;
            int finalI1 = i;
            images.get(i).setOnMouseClicked((MouseEvent e) -> {
                ResourceData.videoName = nameList.get(finalI);
                appmain.gotoVideoPlayUI(String.valueOf(urlList.get(finalI)), nameList.get(finalI1));//add click event
            });
            label.get(i).setText(String.valueOf(nameList.get(i)));//set video label
        }
    }

    public void bookLive(MouseEvent mouseEvent) {
        CustomerInfoControl customerInfoControl = new CustomerInfoControl();
        if(customerInfoControl.behStudentCoach(1,"").equals("")){
            noCoach.setText("No personal coach!");
        }else{
            appmain.gotoLiveBookUI();
        }
    }
    public void stuVideo(MouseEvent mouseEvent) {
        CustomerInfoControl customerInfoControl = new CustomerInfoControl();
        if(customerInfoControl.behStudentCoach(1,"").equals("")){
            noCoach.setText("No personal coach!");
        }else{
            appmain.gotoCustomerVideoUI();
        }
    }

    /**
     * This is the set visible or not function which controls
     * all variable components showed for different group of users
     * @param b1 bookLiveButton visibility
     * @param b2 livePane visibility
     * @param b3 scrollPane visibility
     * @param b4 scrollPane1 visibility
     * @param b5 left visibility
     * @param b6 left1 visibility
     * @param b7 right visibility
     * @param b8 right1 visibility
     * @param b9 yogaCatLabel visibility
     * @param b10 fitCatLabel visibility
     * @param b11 freePrompt visibility
     * @param b12 chargePrompt visibility
     */
    public void authorityElements(boolean b1, boolean b2,Boolean b3,boolean b4,boolean b5,boolean b6,boolean b7,boolean b8,
                                  boolean b9,boolean b10,boolean b11,boolean b12){
        bookLiveButton.setVisible(b1);
        livePane.setVisible(b2);
        scrollPane.setVisible(b3);
        scrollPane1.setVisible(b4);
        left.setVisible(b5);
        left1.setVisible(b6);
        right.setVisible(b7);
        right1.setVisible(b8);
        yogaCatLabel.setVisible(b9);
        fitCatLabel.setVisible(b10);
        freePrompt.setVisible(b11);
        chargePrompt.setVisible(b12);
    }
}