import java.io.IOException;
import java.io.InputStream;

public class RespondReader {

    public void readRespond(int responseCode, String respondMessage, InputStream inputStream) throws IOException {
        if (responseCode == 200) {
            System.out.println("Server reached");

            InputStream stream = inputStream;
            StreamConverter converter = new StreamConverter();
            String responseBody = converter.convertStreamToString(stream);
            stream.close();
            System.out.println("Response from the server:");
            System.out.println(responseBody);
        } else {
            String responseDescription = respondMessage;
            System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
        }
    }
}
