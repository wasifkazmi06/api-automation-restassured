package nach.presentment;

import api.nach.presentment.SendCollectionsToLP;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.nach.presentment.InitiateCollectionPojo;


import java.util.HashMap;

public class SendCollectionsToLpCron {

    static SendCollectionsToLP sendCollectionsToLP ;
    static InitiateCollectionPojo initiateCollectionPojo;

    static {
        try {
            initiateCollectionPojo = new InitiateCollectionPojo();
            sendCollectionsToLP = new SendCollectionsToLP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendCollectionsToLpCron() {
    }

    @Step()
    public static Response initiateSendCollectionsToLpCron() {
        Response response;
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParam = new HashMap<>();

        response = sendCollectionsToLP.getWithResponse(queryParam, headerDetails);
        Allure.addAttachment("response : ", response.asString());
        return  response;
    }

}
