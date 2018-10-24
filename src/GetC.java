import java.io.*;
import java.net.ProtocolException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetC {
    public static void main(String[] args) {
        GetC get = new GetC();
        get.sendGet("http://104.248.47.74/dkrest/test/get2");
    }

    private void sendGet(String url){
        try{
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            if(con.getResponseCode() == 200){
                System.out.println("Server reached");
                InputStream stream = con.getInputStream();
                String responseBody = convertStreamToString(stream);
                stream.close();
                System.out.println("Response from the server:");
                System.out.println(responseBody);
            }
            else{
                System.out.println("Request failed, response code: " + con.getResponseCode() + " (" + con.getResponseMessage() + ")");
            }

        }  catch (ProtocolException e) {
            System.out.println("Protocol not supported by the server");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Read the whole content from an InputStream, return it as a string
     * @param is Inputstream to read the body from
     * @return The whole body as a string
     */
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
