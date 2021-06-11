package app.Control.Launch;

import app.Boundary.UIController.Administrator.*;
import app.Boundary.UIController.Coach.CoachLiveUIController;
import app.Boundary.UIController.Coach.CoachLoginUIController;
import app.Boundary.UIController.Coach.CoachMainUIController;
import app.Boundary.UIController.Coach.CoachVideoUIController;
import app.Boundary.UIController.Customer.*;
import app.Control.Customer.*;
import app.Control.Share.ConfirmControl;
import app.Entity.Account.CurrentAccount;
import app.Entity.Resource.ReadFile;
import app.Entity.Resource.ResourceData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class appMain extends Application {

    private Stage stage;
    private Stage subStage = new Stage();

    /**
     * The constructor for the {@code appMain} class
     */
    public appMain() {
        new ReadFile().getStaticList("membershipLevel", ResourceData.membershipLevelList);
        new ReadFile().getStaticList("videoCategory", ResourceData.categoryList);
    }

    /**
     * This method is used to replace the current content in the panel with the new content
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = appMain.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(appMain.class.getResource(fxml));

        try {
            AnchorPane page = (AnchorPane)loader.load(in);
            Scene scene = new Scene(page, 1200.0D, 675.0D);
            stage.setMinHeight(725.0D);
            stage.setMinWidth(1218.0D);
            stage.setTitle("R & R GYM");
            this.stage.setScene(scene);
            this.stage.sizeToScene();
        } catch (Exception var6) {
        }

        return (Initializable)loader.getController();
    }

    /**
     * Load the specific fxml file to the main stage.
     * This function is mainly used to implement the jump between different UI.
     *
     * @param fxml
     * @param title
     * @return
     * @throws Exception
     */
    private Initializable generateScene(String fxml,String title) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = appMain.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(appMain.class.getResource(fxml));
        this.subStage.show();
        try {
            AnchorPane page = (AnchorPane)loader.load(in);
            Scene scene = new Scene(page, 700.0D, 400.0D);
            this.subStage.setMinWidth(718.0D);
            this.subStage.setMinHeight(450.0D);
            this.subStage.setTitle(title);
            this.subStage.getIcons().add(new Image("file:resource/image/feedback.png"));
            this.subStage.setScene(scene);
            this.subStage.sizeToScene();
        } catch (Exception var6) {
        }

        return (Initializable)loader.getController();
    }

    /**
     * This method will load the content of confirm UI
     */
    public void gotoConfirmUI(String confirmTitle, int type, Class cls, Object obj) {
        if(this.subStage.isShowing() == true){
            return;
        }
        else{
            try {
                ConfirmControl control = (ConfirmControl)this.generateScene("/app/Boundary/UI/Share/ConfirmUI.fxml",confirmTitle);
                control.setStage(subStage);
                control.setTitle(confirmTitle);
                control.setApp(this);
                control.setCls(cls);
                control.setObj(obj);
                control.setType(type);
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }
    }

    /**
     * This method will load the content of feedback UI
     */
    public void gotoFeedBackUI() {
        if(this.subStage.isShowing() == true){
            return;
        }
        else{
            try {
                CustomerFeedbackUIController control = (CustomerFeedbackUIController)this.generateScene("/app/Boundary/UI/Customer/Feedback.fxml", "Feedback");
                control.setApp(this);
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }
    }

    /**
     * This method will load the content of change password UI
     */
    public void gotoChangePasswordUI() {
        try {
            CustomerChPwdUIController control = (CustomerChPwdUIController) this.replaceSceneContent("/app/Boundary/UI/Customer/ChangePassword.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of book live UI
     */
    public void gotoLiveBookUI() {
        try {
            CustomerBookLiveUIController control = (CustomerBookLiveUIController)this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerBookLiveUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of user login UI
     */
    public void gotoUserLoginUI() {
        try {
            CustomerLoginUIController control = (CustomerLoginUIController)this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerLoginUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of user UI
     *
     */
    public void gotoUserUI() {
        try {
            CustomerMainControl control = (CustomerMainControl)this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerUI.fxml");
            control.setApp(this);
            control.hideScrollBar();
            control.initializeCustomer();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of coach UI
     */
    public void gotoCoachUI() {
        try {
            CoachMainUIController control = (CoachMainUIController)this.replaceSceneContent("/app/Boundary/UI/Coach/CoachUI.fxml");
            control.setApp(this);
            control.setName(CurrentAccount.getCurAccount().getId());
            control.getStu();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of coach login UI
     */
    public void gotoCoachLoginUI() {
        try {
            CoachLoginUIController control = (CoachLoginUIController)this.replaceSceneContent("/app/Boundary/UI/Coach/CoachLoginUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of video of coach UI
     */
    public void gotoCoachVideoUI(){
        try {
            CoachVideoUIController control = (CoachVideoUIController)this.replaceSceneContent("/app/Boundary/UI/Coach/CoachVideoUI.fxml");
            control.setApp(this);
            control.getStuAndVideo();
            control.getInfo();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of coach live UI
     */
    public void gotoCoachLiveUI() {
        try {
            CoachLiveUIController control = (CoachLiveUIController)this.replaceSceneContent("/app/Boundary/UI/Coach/CoachLiveUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of membership shop UI
     */
    public void gotoMemberShopUI() {
        try {
            CustomerPurchaseControl control = (CustomerPurchaseControl)this.replaceSceneContent("/app/Boundary/UI/Customer/MemberShop.fxml");
            control.setApp(this);
            control.intializePurchase();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of coach list UI
     */
    public void gotoCoachListUI() {
        try {
            AdminCoachUIController control = (AdminCoachUIController)this.replaceSceneContent("/app/Boundary/UI/Administrator/AdminCoachUI.fxml");
            control.setApp(this);
            control.initializeCoach();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of bill list UI
     */
    public void gotoBillListUI() {
        try {
            AdminBillUIController control = (AdminBillUIController)this.replaceSceneContent("/app/Boundary/UI/Administrator/AdminBillUI.fxml");
            control.setApp(this);
            control.setName(CurrentAccount.getCurAccount().getId());
            control.initializeBill();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of video list UI
     */
    public void gotoVideoListUI() {
        try {
            String name = CurrentAccount.getCurAccount().getId();
            AdminVideoUIController control = (AdminVideoUIController)this.replaceSceneContent("/app/Boundary/UI/Administrator/AdminVideoUI.fxml");
            control.setApp(this);
            control.setName(CurrentAccount.getCurAccount().getId());
            control.initializeVideo(name);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of administrator login UI
     */
    public void gotoAdminLoginUI() {
        try {
            AdminLoginUIController control = (AdminLoginUIController)this.replaceSceneContent("/app/Boundary/UI/Administrator/AdminLoginUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of register UI
     */
    public void gotoRegisterUI() {
        try {
            CustomerRegisterControl control = (CustomerRegisterControl)this.replaceSceneContent("/app/Boundary/UI/Customer/RegisterUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of VideoPlay UI
     */
    public void gotoVideoPlayUI(String url,String name) {
        try {
            CustomerVideoPlayControl control = (CustomerVideoPlayControl) this.replaceSceneContent("/app/Boundary/UI/Customer/VideoPlayUI.fxml");
            control.setApp(this);
            control.initializeVideo(url,name);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of Charge UI
     */
    public void gotoChargeUI() {
        try {
            CustomerChargeUIController control = (CustomerChargeUIController) this.replaceSceneContent("/app/Boundary/UI/Customer/ChargeUI.fxml");
            control.setApp(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of CustomerInfo UI
     */
    public void gotoCustomerInfoUI() {
        try {
            CustomerInfoControl control = (CustomerInfoControl) this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerInfoUI.fxml");
            control.setApp(this);
            control.initializeInfo();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of CustomerLive UI
     */
    public void gotoCustomerLiveUI() {
        try {
            CustomerLiveUIController control = (CustomerLiveUIController) this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerLiveUI.fxml");
            control.setApp(this);
            control.liveInitial();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of AdminCustomer UI.
     */
    public void gotoAdminCustomerUI() {
        try {
            AdminCustomerUIController control = (AdminCustomerUIController) this.replaceSceneContent("/app/Boundary/UI/Administrator/AdminCustomerUI.fxml");
            control.setApp(this);
            control.setName(CurrentAccount.getCurAccount().getId());
            control.initializeVideo();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method will load the content of CustomerVideo UI
     */
    public void gotoCustomerVideoUI() {
        try {
            CustomerVideoUIController control = (CustomerVideoUIController) this.replaceSceneContent("/app/Boundary/UI/Customer/CustomerVideoUI.fxml");
            control.setApp(this);
            control.getInfo();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * This method is used to read from json record and return the json object.
     *
     * @param line The json record.
     * @return The corresponding json object.
     */
    public static JsonObject jsonReader(String line) {
        JsonObject jsonObject;
        JsonParser jsonParser = new JsonParser();
        jsonObject = (JsonObject) jsonParser.parse(line);
        return jsonObject;
    }

    /**
     * This method is used to launch our software.
     * The function will be called automatically once we run our main function.
     *
     * @param primaryStage Basic stage of our software.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.gotoUserLoginUI();
        this.stage.getIcons().add(new Image("file:resource/image/Logo.png"));
        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}