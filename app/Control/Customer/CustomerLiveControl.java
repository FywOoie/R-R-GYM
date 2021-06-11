package app.Control.Customer;

import java.io.BufferedReader;
import java.io.FileReader;

public class CustomerLiveControl {

    /**
     * This method is used to find the advice from the coach to the specific user.
     * The coach advice will be shown in the live panel.
     *
     * @param fileName The file containing the coach advice.
     * @return The advice to the specific user.
     */
    public String showContent(String fileName) {
        StringBuilder content= new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line="";
            while((line=bufferedReader.readLine())!=null) {
                content.append(line).append('\n');//increment the String
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
