import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {

        //SETUP
        String protocol = "http";
        String host = "104.248.47.74";
        String email = ""; //Empty for security reasons (public repository)
        String phone = ""; //Empty for security reasons (public repository)

        PostC post = new PostC();
        GetC get = new GetC();
        TaskSolver taskSolver = new TaskSolver();

        String taskDirectory = "/dkrest/gettask/";
        String solutionDirectory = "/dkrest/solve";
        String resutlDirectory = "/dkrest/results/";
        String authdirectory = "/dkrest/auth";
        String solutionUrl = protocol + "://" + host + solutionDirectory;

        //1.AUTHORIZATION
        String authUrl = protocol + "://" + host + authdirectory;
        String authInfo = "{ \"email\": \"" + email + "\", \"phone\": \"" + phone + "\"}";
        JSONObject authJson = post.convertToJsonObject(authInfo);
        post.postSend(authUrl, authJson);
        System.out.println("Response from the server:");
        System.out.println(post.getRespond());

        //GET RESPOND FROM SERVER & FIND SESSION ID
        JSONObject jsonResponse = new JSONObject(post.getRespond());
        int sessionId = jsonResponse.getInt("sessionId");

        //2.TASK REQUEST & 3.PARSE TASK DATA
        for(int taskNr = 1; taskNr <= 4; taskNr++){
            String taskUrl = protocol + "://" + host + taskDirectory + taskNr + "?" + "sessionId=" + sessionId;
            get.sendGet(taskUrl);
            System.out.println("Response from the server:");
            System.out.println(get.getRespond());
            JSONObject jsonResponseTask = new JSONObject(get.getRespond());

            //4.SOLVE TASKS
            String solution = "";
            switch (taskNr){
                case 1:
                    solution = taskSolver.returnHello(sessionId);
                    break;
                case 2:
                    solution = taskSolver.printResponndmsg(sessionId, jsonResponseTask);
                    break;
                case 3:
                    solution = taskSolver.multiplyArray(sessionId, jsonResponseTask);
                    break;
                case 4:
                    solution = taskSolver.crackMD5Pin(sessionId, jsonResponseTask);
                    break;
            }

            //POST TASKS SOLUTIONS
            JSONObject solutionJson = post.convertToJsonObject(solution);
            post.postSend(solutionUrl,solutionJson);
            System.out.println(post.getRespond());
        }

        //REQUEST SECRET TASK
        double secretNumber = Math.sqrt(4064256); //2016
        String secretTaskURL = protocol + "://" + host + taskDirectory + secretNumber + "?" + "sessionId=" + sessionId;
        get.sendGet(secretTaskURL);
        System.out.println(get.getRespond());

        //SOLVE SECRET TASK
        JSONObject jsonResponseSecret = new JSONObject(get.getRespond());
        String secretSolution = taskSolver.findIPInRange(sessionId, jsonResponseSecret);

        //POST SECRET TASK SOLUTION
        post.postSend(solutionUrl,post.convertToJsonObject(secretSolution));
        System.out.println(post.getRespond());

        //5.ASK FOR RESULT
        String resultURL = protocol + "://"  + host + resutlDirectory + sessionId;
        get.sendGet(resultURL);
        System.out.println(get.getRespond());
    }
}