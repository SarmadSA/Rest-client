import java.io.IOException;
import java.io.InputStream;

public class RespondReader {

    public String readRespond(int responseCode, String respondMessage, InputStream inputStream) throws IOException {
        if (responseCode == 200) {
            System.out.println("Server reached");

            InputStream stream = inputStream;
            StreamConverter converter = new StreamConverter();
            String responseBody = converter.convertStreamToString(stream);
            stream.close();
            return responseBody;
        } else {
            return "Request failed, response code: " + responseCode + " (" + respondMessage + ")";
        }
    }
}
