package nach.presentment;

import api.nach.presentment.InitiateRazorpayWebhook;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import nach.NACHConstants;
import org.testng.Assert;
import util.StringTemplate;

import java.util.HashMap;

public class InitiateRazorpayWebhookStep {


    static public InitiateRazorpayWebhook initiateRazorpayWebhook;

    static {
        try {
             initiateRazorpayWebhook  = new InitiateRazorpayWebhook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public InitiateRazorpayWebhookStep()  {
    }

    @Step
    public static Response initiateRazorpayWebhook(String event, String id, String amount,
                                                      String order_id, String error_code,
                                                      String error_description, String error_reason,
                                                      String error_source, String error_step,
                                                      String status){

        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();

        queryParamDetails.put("eventType", "RAZOR_PAY");
        System.out.println(headerDetails);
        String initiateWebhookRazorpayRequest = new StringTemplate(NACHConstants.WEBHOOK_RAZORPAY_PRESENTMENT)
                .replace("event", event)
                .replace("id", id)
                .replace("amount", amount)
                .replace("order_id", order_id)
                .replace("error_code", error_code)
                .replace("error_description", error_description)
                .replace("error_reason", error_reason)
                .replace("error_source", error_source)
                .replace("error_step", error_step)
                .replace("status", status)
                .toString();

        Response response = initiateRazorpayWebhook.post1(queryParamDetails,headerDetails, initiateWebhookRazorpayRequest);
        Assert.assertTrue(response.getBody().asString().equals("Success") , "Check that new collection is created successfully");
        return response;
    }

}
