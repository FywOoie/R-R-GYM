package app.Entity.Account;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class  Account {
    private Integer uid; //user uid
    private String id;  //user id
    private String pwd;      //user password
    private Double balance;  //user money
    private Integer membership;  // user's membership nomembership" 0 default "basicmembership" 1 50 "premiermembership" 2 100
    private JsonObject jsonObject;
    private String position;

    //getters and setters
    public void setUid(int uid){
        this.uid = uid;
    }
    public int getUid(){
        return this.uid;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
    public String getPwd(){
        return this.pwd;
    }
    public void setBalance(double balance){this.balance = balance;}
    public double getBalance(){return this.balance;}
    public void setMembership(int membership){this.membership = membership;}
    public int getMembership(){return this.membership;}
    public void setPosition(String pos){
        this.position = pos;
    }
    public String getPosition(){ return this.position; }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject, String id,Integer uid,String pwd) {
        jsonObject.addProperty("id",id);
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("pwd",pwd);
        this.jsonObject = jsonObject;
    }

    //creat a json for customer
    public void setJsonObject(JsonObject jsonObject, String id, Integer uid, String pwd, Double balance, Integer membership, JsonArray jsonArray, String position) {
        jsonObject.addProperty("id",id);
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("pwd",pwd);
        jsonObject.addProperty("balance",balance);
        jsonObject.addProperty("membership",membership);
        jsonObject.add("favoList",jsonArray);
        jsonObject.addProperty("position",position);
        this.jsonObject = jsonObject;
    }
}
