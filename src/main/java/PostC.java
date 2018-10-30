import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class PostC {
    private String respond = "No respond";

    public String getRespond() {
        return respond;
    }

    public void postSend(String url, JSONObject jsonData){
        if(jsonData != null){
            try{
                URL urlObj = new URL(url);
                System.out.println("Sending HTTP POST to " + url);
                HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                os.write(jsonData.toString().getBytes());
                os.flush();

                int responseCode = con.getResponseCode();
                RespondReader respondReader = new RespondReader();
                respond = respondReader.readRespond(responseCode, con.getResponseMessage(), con.getInputStream());

            }catch (ProtocolException e) {
                System.out.println("Protocol nto supported by the server");
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }

    public JSONObject convertToJsonObject(String jsonString){
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonString);
            return jsonObject;
        }
        catch (JSONException e){
            System.out.println("Got exception in JSON parsing: " + e.getMessage());
        }
        return null;
    }
}
