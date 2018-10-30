import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

public class TaskSolver {

    public String returnHello(int sessionId){
        String solution = "{\"sessionId\": " + sessionId + ", \"msg\": \"Hello\"}";;
        return solution;
    }

    public String printResponndmsg(int sessionId, JSONObject respond){
        String solution = "{\"sessionId\": " + sessionId + ", \"msg\": \"" + respond.getJSONArray("arguments").get(0) + "\"}";;
        return solution;
    }

    public String multiplyArray(int sessionId, JSONObject respond){
        int result = 1;
        for(int i = 0; i < respond.getJSONArray("arguments").length(); i++){
            TypeConverter converter = new TypeConverter();
            result *= converter.jsonObjectToType(respond.getJSONArray("arguments").get(i));
        }
        System.out.println(result);
        String solution = "{\"sessionId\": " + sessionId + ", \"result\": " + result + "}";
        return solution;
    }

    public String crackMD5Pin(int sessionId, JSONObject respond){
        String mdCode = (String) respond.getJSONArray("arguments").get(0);

        String cradedPin = "1";
        for (int i = 0; i < 10000; i++) {
            String t;
            if(i < 10){
                t = "000" + i;
            }
            else if(i < 100){
                t = "00" + i;
            }
            else if(i < 1000){
                t = "0" + i;
            }
            else{
                t = "" + i;
            }

            String hashedPin = DigestUtils.md5Hex(t);

            if(hashedPin.equals(mdCode)){
                System.out.println("***** Hashing found *****");
                System.out.println("Cracked PIN: " + t);
                System.out.println("Hashed as: " + hashedPin);
                cradedPin = t;
                break;
            }
        }
        System.out.println(cradedPin);
        String solution = "{\"sessionId\": " + sessionId + ", \"pin\": " + Integer.parseInt(cradedPin) + "}";
        return solution;
    }

    public String findIPInRange(int sessionId, JSONObject respond){
        String subnetMask = (String) respond.getJSONArray("arguments").get(1);
        String[] subnetSegments = subnetMask.split("\\.");
        int numberOfPossibleIPs = 0;
        for(int i = 0; i < subnetSegments.length; i++){
            int rest = 255 - Integer.parseInt(subnetSegments[i]);
            numberOfPossibleIPs += rest;
        }
        numberOfPossibleIPs -=2; //remove broadcasting address and net#
        System.out.println("Possible number of IPs: " + numberOfPossibleIPs);
        String ip = (String) respond.getJSONArray("arguments").get(0);
        String solutionIp = "";
        if(numberOfPossibleIPs > 0){
            String[] ipSegments = ip.split("\\.");
            solutionIp = ipSegments[0] + "." + ipSegments[1] + "." + ipSegments[2] + ".1";
        }
        else{
            //No possible IPs only the given one
            solutionIp = ip;
        }
        System.out.println(solutionIp);
        String solution = "{\"sessionId\": " + sessionId + ",\"ip\": \"" + solutionIp + "\"}";;
        return solution;
    }
}
