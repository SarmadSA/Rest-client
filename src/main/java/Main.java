import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {

        //OPTIONS
        String getUrl = "http://104.248.47.74/dkrest/test/get2";
        String postUrl = "http://104.248.47.74/dkrest/test/post";
        String jsonString = "{ \"a\": 1, \"b\": 91}";

        //POST
        PostC post = new PostC();
        JSONObject jsonObject = post.convertToJsonObject(jsonString);
        post.postSend(postUrl, jsonObject);

        //GET
        GetC get = new GetC();
        get.sendGet(getUrl);

    }
}
