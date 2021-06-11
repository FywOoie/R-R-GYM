package app.Entity.Account;

import app.Entity.Resource.ReadFile;
import app.Entity.Resource.ResourceData;
import app.Entity.Transaction.Bill;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;


public class CustomerAccount extends Account {

	LinkedList<String> favoList = new LinkedList<>();
	public LinkedList<String> getFavList(){
		return this.favoList;
	}
	public void setFavoList(LinkedList<String> linkedList){
		this.favoList = linkedList;
	}

	/**
	 * It is a function for user to top up on their account
	 * @param uid It is used for the program to locate this user's information in database
	 * @param amount recharge amount
	 *
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int topup(int uid,Double amount) {
		int isTopup = 0;
		new Bill(uid, amount);
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		StringBuilder oldInfo= new StringBuilder();
		String newInfo="";
		File f = new File(ResourceData.customerFile);
		try {
			String str =null;
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(uid==Integer.parseInt(jsonObject.get("uid").toString())){
					jsonObject.addProperty("balance",amount+Double.parseDouble(jsonObject.get("balance").toString()));
					newInfo = jsonObject.toString() + "\r\n";
					isTopup = 1;
				}
				else{
					oldInfo.append(jsonObject.toString()).append("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream stream=null;
		try {
			stream=new PrintStream(ResourceData.customerFile);
			stream.print(oldInfo);
			stream.print(newInfo);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return isTopup;
	}

	/**
	 * It is a function for user to purchase a membership
	 * @param membershipLevel which kind of membership our user want to buy
	 * @param discount the discount of this kind of membership
	 *
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int buymembership(int membershipLevel,double discount) {
		int i=0;
		boolean has = false;
		for (int j = 0; j < ResourceData.membershipLevelList.size(); j++) {
			if(membershipLevel==Integer.parseInt(ResourceData.membershipLevelList.get(j)))
			{
				has=true;
				break;
			}
		}
		if (!has)
		{
			return i;
		}
		StringBuilder oldInfo = new StringBuilder();//
		String newInfo="";//
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);
		double amount = membershipLevel*50*(1-discount);
		try {
			String str =null;
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(ResourceData.uid==Integer.parseInt(jsonObject.get("uid").toString())){
					ReadFile readFile = new ReadFile();
					if(readFile.getbalance(ResourceData.uid)>=amount)
					{
						jsonObject.addProperty("membership",membershipLevel);
						new Bill(ResourceData.uid,amount*(-1));
						jsonObject.addProperty("balance",Double.parseDouble(jsonObject.get("balance").toString())-amount);
						newInfo = jsonObject.toString() + "\r\n";
						i=1;
					}
					else{
						oldInfo.append(jsonObject.toString()).append("\r\n");
					}

				}
				else{
					oldInfo.append(jsonObject.toString()).append("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream stream = null;
		try {
			stream = new PrintStream(ResourceData.customerFile);//
			stream.print(oldInfo);
			stream.print(newInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * It is a function for user to add a video to its favorite list
	 * @param uid It is used for the program to locate this user's information in database
	 * @param favoVideo video name
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int addtoFavoList(int uid, String favoVideo)
	{
		int i=0;
		JsonArray jsonArray = new JsonArray();
		StringBuilder oldInfo= new StringBuilder();//
		String newInfo="";//
		JsonObject jsonObject;
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);
		try {
			String str =null;
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(uid==Integer.parseInt(jsonObject.get("uid").toString())){
					jsonArray=jsonObject.getAsJsonArray("favoList");
					for (int j = 0; j < jsonArray.size() ; j++) {
						if (jsonArray.get(j).toString().equals('"'+favoVideo+'"'))
						{
							return i;
						}
					}
					jsonArray.add(favoVideo);
					jsonObject.add("favoList",jsonArray);
					i=1;
					newInfo = jsonObject.toString() + "\r\n";
					this.favoList.add(favoVideo);//add to the object
				}
				else{
					oldInfo.append(jsonObject.toString()).append("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		PrintStream stream=null;
		try {
			stream=new PrintStream(ResourceData.customerFile);
			stream.print(oldInfo);
			stream.print(newInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return i;
	}
	/**
	 * It is a function for user to remove a video from its favorite list
	 * @param uid It is used for the program to locate this user's information in database
	 * @param removeVideo video name
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int removeFavoList(int uid, String removeVideo)
	{
		int i=0;
		JsonArray jsonArray = new JsonArray();
		StringBuilder oldInfo= new StringBuilder();//
		String newInfo="";//
		JsonObject jsonObject;
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);
		try {
			String str =null;
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);
				if(uid==Integer.parseInt(jsonObject.get("uid").toString())){
					jsonArray=jsonObject.getAsJsonArray("favoList");
					for (int j = 0; j < jsonArray.size(); j++) {
						String videoname = '"'+removeVideo+'"';
						if(jsonArray.get(j).toString().equals(videoname))
						{
							jsonArray.remove(j);
							i=1;
							this.favoList.remove(removeVideo);//remove to the object
							break;
						}
					}
					jsonObject.add("favoList",jsonArray);
					newInfo = jsonObject.toString() + "\r\n";
				}
				else{
					oldInfo.append(jsonObject.toString()).append("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream stream = null;
		try {
			stream=new PrintStream(ResourceData.customerFile);
			stream.print(oldInfo);
			stream.print(newInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return i;
	}
	/**
	 * It is a function for administrator to add a membership level
	 * @param level new membership level
	 *
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int addtoMembershipLevel(String level){
		int i=0;
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
				JsonArray jsonArray = (JsonArray) jsonObject.get("membershipLevel");
				for (int j = 0; j < ResourceData.membershipLevelList.size(); j++) {
					if(!(level.equals(ResourceData.membershipLevelList.get(j))))
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
					jsonArray.add(level);
					ResourceData.membershipLevelList.add(level);
					jsonObject.add("membershipLevel",jsonArray);
					newInfo = jsonObject.toString();
					i = 1;
				}
				else {
					newInfo = jsonObject.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream stream=null;
		try {
			stream=new PrintStream("Account\\PropertyList.txt");//
			stream.print(newInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * It is a function for administrator to remove a membership level
	 * @param level membership level admin want to remove
	 *
	 * @return 1 stands for operation success 0 stands for failed
	 **/
	public int removeMembershipLevel(String level){
		int i = 0;
		boolean has = true;
		File f = new File("Account\\PropertyList.txt");
		String newInfo="";//
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
				JsonArray jsonArray = (JsonArray) jsonObject.get("membershipLevel");
				for (int j = 0; j < ResourceData.membershipLevelList.size(); j++) {
					if(level.equals(ResourceData.membershipLevelList.get(j)))
					{
						jsonArray.remove(j);
						ResourceData.membershipLevelList.remove(level);
						jsonObject.add("membershipLevel",jsonArray);
						newInfo=jsonObject.toString();
						i=1;
					}
					else {
						newInfo=jsonObject.toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintStream stream=null;
		try {
			stream = new PrintStream("Account\\PropertyList.txt");//
			stream.print(newInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return i;
	}

}
