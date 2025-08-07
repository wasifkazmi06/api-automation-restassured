package chatbot;

import api.chatbot.StartSession;
import api.platform.GetUserData;
import org.json.JSONObject;
import pojos.chatbot.StartSessionPojo;
import pojos.platform.getUserData.UserData;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class ChatbotToken {

    static StartSession startSessionAPI;
    static public UserData userData;
    static public GetUserData getUserData;
    static public StartSessionPojo startSessionPojo;

    static {
        try {
            startSessionAPI = new StartSession();
            getUserData = new GetUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setChatbotStartSessionData(String mobile){

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();
        queryParamDetailsGetUserPlatform.put("mobile", mobile);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String startSessionRequest = new StringTemplate(ChatbotConstants.START_SESSION_REQUEST)
                .replace("umUuid", userData.getUmUuid())
                .toString();

        JSONObject startSessionRequestJson = new JSONObject(startSessionRequest);

        startSessionPojo = startSessionAPI.post(queryParamDetails, headerDetails, startSessionRequestJson.toString());

    }
}
