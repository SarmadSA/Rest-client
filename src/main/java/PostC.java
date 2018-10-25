import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class PostC {
    public static void main(String[] args) {
        String url = "http://104.248.47.74/dkrest/test/post";
        String jsonString = "{ \"a\": 1, \"b\": 91}";

        PostC post = new PostC();
        JSONObject jsonObject = post.convertToJsonObject(jsonString);
        post.postSend(url, jsonObject);
    }


    private void postSend(String url, JSONObject jsonData){
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
                if (responseCode == 200) {
                    System.out.println("Server reached");

                    // Response was OK, read the body (data)
                    InputStream stream = con.getInputStream();
                    String responseBody = convertStreamToString(stream);
                    stream.close();
                    System.out.println("Response from the server:");
                    System.out.println(responseBody);
                } else {
                    String responseDescription = con.getResponseMessage();
                    System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
                }
            }catch (ProtocolException e) {
                System.out.println("Protocol nto supported by the server");
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }

    private JSONObject convertToJsonObject(String jsonString){
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

    private String convertStreamToString(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append('\n');
            }
        } catch (IOException ex) {
            System.out.println("Could not read the data from HTTP response: " + ex.getMessage());
        }
        return response.toString();
    }
}
