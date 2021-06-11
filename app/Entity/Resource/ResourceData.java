package app.Entity.Resource;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResourceData {
    public static String videoName;
    public static int uid;
    public static String customerFile = "Account\\CustomerAccount.txt";
    public static String coachFile = "Account\\Coach.txt";
    public static String adminFile = "Account\\Admin.txt";
    public static String billFile = "Account\\Bill.txt";
    public static String videoFile = "Account\\Video.txt";
    public static String customerCoachFile = "Account\\CustomerCoach.txt";
    public static double basicDiscount = 0.1;
    public static double premierDiscount = 0.1;

    public static ArrayList<String> membershipLevelList = new ArrayList<>();
    public static ArrayList<String> categoryList = new ArrayList<>();//video type

    public static double controlNumber(double discount){
        DecimalFormat df = new DecimalFormat( "0.00 ");
        return Double.parseDouble(df.format(discount));
    }



}
