package app.Entity.Resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadFile {
	/**
	 * It is a function for programer to get a user's current membership
	 * @param uid It is used for the program to locate this user's information in database
	 *
	 * @return user's current membership level
	 **/
	public String getmembership(int uid) {
		String membership = null;
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);
		try {
			String str = null;

			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(uid == Integer.parseInt(jsonObject.get("uid").toString())){
					membership = jsonObject.get("membership").toString();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membership;
	}

	/**
	 * It is a function for programer to get a user's current balance
	 * @param uid It is used for the program to locate this user's information in database
	 *
	 * @return user's current balance
	 **/
	public Double getbalance(int uid) {
		double balance = 0.0;
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);
		try {
			String str = null;

			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(uid==Integer.parseInt(jsonObject.get("uid").toString())){
					balance= Double.parseDouble(jsonObject.get("balance").toString());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}

	public static String getTime(){// get the system time
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return sdf.format(date);
	}

	public void getStaticList(String className, ArrayList<String> arrayList){
		File f = new File("Account\\PropertyList.txt");
		FileInputStream fis = null;
		try {
			String str = null;
			fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			JsonParser jsonParser = new JsonParser();
			while ((str = br.readLine()) != null){
				JsonObject jsonObject = (JsonObject) jsonParser.parse(str);
				JsonArray levelList =(JsonArray) jsonObject.get(className);
				for (int i = 0; i < levelList.size(); i++) {
					arrayList.add(levelList.get(i).toString().replace("\"",""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
