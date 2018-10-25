import java.io.*;
import java.net.ProtocolException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetC {

    public void sendGet(String url){
        try{
            URL urlObj = new URL(url);
            System.out.println("Sending HTTP GET to " + url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            RespondReader respondReader = new RespondReader();
            respondReader.readRespond(responseCode, con.getResponseMessage(), con.getInputStream());

        }  catch (ProtocolException e) {
            System.out.println("Protocol not supported by the server");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
