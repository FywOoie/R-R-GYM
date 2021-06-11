package app.Entity.Account;

import app.Entity.Resource.ResourceData;
import app.Entity.Video.Video;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class implements adminAccount class which
 * defines attributes and things an admin can do
 */

public class AdminAccount extends Account{

	/**
	 * This method takes in the admin's name and password and
	 * create a new one.
	 *
	 * @param id:name of admin
	 * @param pwd:password of the admin
	 * @return i : 1 if successfully added, 0 if not.
	 */
	public int addAdmin(String id,String pwd)
	{
		int i = 0;//i will be considered as return value, default value is 0
		boolean hassameid = false;//Used to verify if the newly distributed uid is used before
		int uid=1;//Default distribued uid of admin is 1
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonObject1 = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		String idString = '"'+id+'"';
		this.setJsonObject(jsonObject, id, uid, pwd);//Add json object

		File f = new File(ResourceData.adminFile);//Open admin file
    	try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String str =null;//Temporary string used to store information of one line of txt file

			if ((str = br.readLine()) == null) {
				FileOutputStream fos = new FileOutputStream(f,true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				setJsonObject(jsonObject, id, uid, pwd);
				osw.write(jsonObject.toString());
				osw.write("\r\n");
				osw.close();
				br.close();
				i = 1;//Write successfully
				hassameid = true;//There are same id in the file
			}
			else {
				do{
					//Make sure uid is not duplicated
					jsonObject1 = (JsonObject) jsonParser.parse(str);
					if(uid+1>Integer.parseInt(jsonObject1.get("uid").toString()))
					{
						uid = uid + 1;//Add uid by one to see if now uid is unique
					}
					else {
						uid=Integer.parseInt(jsonObject1.get("uid").toString())+1;
					}
					if (idString.equals(jsonObject1.get("id").toString())) {
						hassameid = true;//There is same id
						break;
					}
				}while ((str = br.readLine()) != null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	jsonObject.addProperty("uid", uid);//Add uid to json object

    	if(!hassameid)//No same id
    	{
			 try {
			    	FileOutputStream fos = new FileOutputStream(f,true);
			    	OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			    	osw.write(jsonObject.toString());
			    	osw.write("\r\n");
			    	osw.close();
			    	i = 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
    	}
    	return i;
	}

	/**
	 * This method is to for admin to add a coach or
	 * customer on the User Interface
	 *
	 * @param coachOrCustomer:1 if adding coach, otherwise adding cusotmer
	 * @param id:name of the customer or coach
	 * @param pwd:password of the customer or coach
	 * @return i : 1 if successfully added, 0 if not.
	 */
	public int addElement(int coachOrCustomer, String id, String pwd)
	{
		int i = 0;
		boolean hassameid = false;
		int uid = 1;
		JsonObject jsonObject = new JsonObject();
		JsonObject jsonObject1 = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		String idString = '"' + id + '"';//Add "" to String so that it fits json
		File f;

		//If adding coach, open coach file
		if (coachOrCustomer == 1) {
			this.setJsonObject(jsonObject, id, uid, pwd);
			f = new File(ResourceData.coachFile);
		}else{
			//If adding customer, open customer file
			double balance = 0;
			int membership = 0;
			JsonArray favList = new JsonArray();
			this.setJsonObject(jsonObject, id, uid, pwd,balance,membership,favList,"");
			f = new File(ResourceData.customerFile);
		}

    	try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(reader);
			String str =null;
			if ((str = br.readLine()) == null) {
				FileOutputStream fos = new FileOutputStream(f,true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

				if (coachOrCustomer == 1) {
					this.setJsonObject(jsonObject, id, uid, pwd);
				}else{
					double balance = 0;
					int membership = 0;
					JsonArray favList = new JsonArray();
					this.setJsonObject(jsonObject, id, uid, pwd,balance,membership,favList,"");
				}
				osw.write(jsonObject.toString());
				osw.write("\r\n");
				osw.close();
				br.close();
				i =1;
				hassameid=true;
			}
			else {
				do{
					jsonObject1 = (JsonObject) jsonParser.parse(str);
					if(uid+1>Integer.parseInt(jsonObject1.get("uid").toString()))
					{
						uid=uid+1;
					}
					else {
						uid=Integer.parseInt(jsonObject1.get("uid").toString())+1;
					}
					if (idString.equals(jsonObject1.get("id").toString())) {
						hassameid = true;
						break;
					}
				}while ((str = br.readLine()) != null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	jsonObject.addProperty("uid", uid);

    	if(!hassameid)
    	{
			 try {
			    	FileOutputStream fos = new FileOutputStream(f,true);
			    	OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			    	osw.write(jsonObject.toString());
			    	osw.write("\r\n");
			    	osw.close();
			    	i=1;
				} catch (Exception e) {
					e.printStackTrace();
				}
    	}
    	return i;//Return 0 if not successful, 1 if successful
	}

	/**
	 * This method is to add video by admin.
	 *
	 * @param id:name of the video
	 * @param category:kind of the video
	 * @param videoUrl:path of the video
	 * @param videoCover:path of the video cover
	 * @return i:1 if successfully added, 0 if not.
	 */
    public int createVideo(String id, String category, String videoUrl, String videoCover) {
		int i = 0;
    	boolean hassameid = Boolean.FALSE;
    	int uid = 1;
    	JsonObject jsonObject = new JsonObject();
    	JsonObject jsonObject1 = new JsonObject();
    	JsonParser jsonParser = new JsonParser();
    	String idString = '"' + id + '"';
    	Video videoTemp = new Video(jsonObject, id, uid, category, videoUrl, videoCover);

    	File f= new java.io.File(ResourceData.videoFile);//Open video file
    	try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String str =null;
			if ((str = br.readLine()) == null) {
				Video video= new Video(jsonObject, id, uid , category, videoUrl, videoCover);
				i = video.saveVideo(jsonObject);//Save video information to txt file
				hassameid=true;
			}
			else {
				do{
					jsonObject1 = (JsonObject) jsonParser.parse(str);
					if((uid + 1) > Integer.parseInt(jsonObject1.get("videoID").toString()))
					{
						uid = uid+1;
					}
					else {
						uid = Integer.parseInt(jsonObject1.get("videoID").toString())+1;
					}
					if (idString.equals(jsonObject1.get("videoName").toString())) {
						hassameid = true;
						break;
					}
				}while ((str = br.readLine()) != null);
		}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
    	jsonObject.addProperty("videoID", uid);
    	if(!hassameid)
    	{
			 try {
				 Video video= new Video(jsonObject, id, uid , category, videoUrl, videoCover);
			    	i=video.saveVideo(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
    	}
    	return i;//Return 0 if not successful, 1 if successful
	}

	/**
	 * change the video information according to search id
	 *
	 * @param SearchId:search uid of the video file
	 * @param videoName:change the video name to this string
	 * @param category:change the video category to this string
	 * @param likeNum:change the video like number to this string
	 * @return isChanged:1 if successfully changed, 0 if not.
	 */
    public int changeVideo(int SearchId, String videoName, String category, int likeNum){
        int isChanged = 0;//Return value, default setting to 0
    	String oVideoList = "";//New video information
        StringBuilder nVideoList = new StringBuilder();//Old video information
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();

        File f = new File(ResourceData.videoFile);//open video list
        try {
            String str = null;//temp string used to store the line of the file
            String idString = String.valueOf(SearchId);//parse search id
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(reader);

            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);//parse line to json
                if(idString.equals(jsonObject.get("videoID").toString())){
                	//search successfully
                	jsonObject.addProperty("videoName",videoName);
                	jsonObject.addProperty("category",category);
					jsonObject.addProperty("likeNum",likeNum);
                    oVideoList = jsonObject.toString() + "\r\n";
                    isChanged = 1;//Change succufully
                }
                else{
                	nVideoList.append(jsonObject.toString()).append("\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Save to file
        PrintStream stream = null;//Output stream
        try {
            stream=new PrintStream(ResourceData.videoFile);
            stream.print(oVideoList);
            stream.print(nVideoList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isChanged;//return 1 if successful, 0 not successful
    }
	
    //change customer information

	/**
	 * change customer information based on search uid
	 * @param searchID:search uid
	 * @param id:change name, won't change if it's old
	 * @param pwd:change password, won't change if it's old
	 * @param balance:change balance, won't change if it's -1
	 * @param membership:change membership, won't change if it's -1
	 * @param position:change position wants to be trained, won't change if it's old
	 * @return 1 passed, 0 failed
	 */
	public int changeCustomer(int searchID,String id,String pwd,double balance,int membership,String position){
		int isChanged = 0;
		String oCustomerList = "";//Changed customer info
		StringBuilder nCustomerList= new StringBuilder();
		String newID = id;//New name
		String newPwd = pwd;//New password
		String newPos = position;//New position
		double newBalance = balance;//New balance
		int newMembership = membership;//New membership
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		File f = new File(ResourceData.customerFile);//open video list

		try {
			String str = null;//temp string to be read of one line
			String idString = String.valueOf(searchID);
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(reader);

			while ((str = br.readLine()) != null) {
				jsonObject = (JsonObject) jsonParser.parse(str);//read one line
				if(id.equals("old")){
					//Change if not old
					newID = jsonObject.get("id").toString().replace("\"","");
				}
				if(pwd.equals("old")){
					//Change if not old
					newPwd = jsonObject.get("pwd").toString().replace("\"","");
				}
				if(position.equals("old")){
					//Change if not old
					newPos = jsonObject.get("position").toString().replace("\"","");
				}
				if(balance == -1){
					//Change if not -1
					String balanceTemp = jsonObject.get("balance").toString().replace("\"","");
					newBalance = Double.parseDouble(balanceTemp);
				}
				if(membership == -1){
					//Change if not -1
					String memTemp = jsonObject.get("membership").toString().replace("\"","");
					newMembership = Integer.parseInt(memTemp);
				}

				if(idString.equals(jsonObject.get("uid").toString())){
					//Write to list if it mataches id
					jsonObject.addProperty("id",newID);
					jsonObject.addProperty("pwd",newPwd);
					jsonObject.addProperty("balance",newBalance);
					jsonObject.addProperty("membership",newMembership);
					jsonObject.addProperty("position",newPos);
					oCustomerList = jsonObject.toString() + "\r\n";
					isChanged = 1;//SUCCESSFULLY
				}
				else{
					nCustomerList.append(jsonObject.toString()).append("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Write to file
		PrintStream stream = null;
		try {
			stream = new PrintStream(ResourceData.customerFile);
			stream.print(oCustomerList);
			stream.print(nCustomerList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return isChanged;
	}

	/**
	 * delete element: video, coach or customer
	 * @param uid : uid of the source to be deleted
	 * @param src : source file of the element to be deleted
	 * @return : 1 if successully, 0 failed, -1 error when reading
	 */
	public int deleteElement(int uid,File src){
		int deleteStatus = 0;//Return value

		try{
			File f1 = new File(String.valueOf(src));
			File f2 = new File("Account\\temp.txt");
			this.streamCopyFile(f1,f2);//Copy a new file as temp file

			BufferedReader bufferedReader = new BufferedReader(new FileReader(f2));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f1));
			String line = "";//Line is a buffered text variable that will store infor of one line
			if((String.valueOf(src).equals(ResourceData.coachFile)) ||
					(String.valueOf(src).equals(ResourceData.customerFile))){
				while((line=bufferedReader.readLine())!=null){
					if(!line.contains(("\"uid\":" + uid))){
						bufferedWriter.write(line);
						bufferedWriter.newLine();//Read next line
					}else {
						deleteStatus = 1;//Delete successfully for there is a match
					}
				}
			}else if(String.valueOf(src).equals(ResourceData.videoFile)) {
				while((line=bufferedReader.readLine())!=null){
					if(!line.contains(("\"videoID\":" + uid))){
						bufferedWriter.write(line);
						bufferedWriter.newLine();//Read next line
					}else {
						deleteStatus = 1;//Delete successfully for there is a match
					}
				}
			}
			bufferedReader.close();
			bufferedWriter.close();//close file
			deleteFile("Account\\temp.txt");
		}
		catch (Exception e){
			e.printStackTrace();
			deleteStatus = -1;//error occured
		}
		return deleteStatus;//return -1 if error, 0 didn't search, 1 if successful
	}

	/**
	 * Delete file according to the file name
	 *
	 * @param fileName: file name to be deleted
	 * @return:1 if succssfully deleted, 0 if file not exists but
	 * didn't delete succssfully, -1 if not exists.
	 */
	public static int deleteFile(String fileName) {
		File file = new File(fileName);
		// If the file to which the file path corresponds exists and is a file, delete it directly
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}

	/**
	 * copy a duplicate of Video.txt
	 * @param srcFile:source file to be copied
	 * @param desFile:save to this destnation file
	 */
    private void streamCopyFile(File srcFile, File desFile) {
    	try {
    		FileInputStream fi = new FileInputStream(srcFile);//Open file
			FileOutputStream fo = new FileOutputStream(desFile);
			Integer by = 0;
			while((by = fi.read()) != -1) {
				fo.write(by);//Write to new file
			}
			fi.close();
			fo.close();//Close file
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
