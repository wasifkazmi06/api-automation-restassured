package api.upi.registration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.registration.GenerateSMSPojo;
import pojos.upi.registration.NotifySMSPojo;

import java.util.HashMap;
import java.util.Map;

public class NotifySMS extends BaseAPI<NotifySMSPojo> {

    public NotifySMS() throws Exception {
        super(Uri.NOTIFY_SMS, NotifySMSPojo.class);
    }
    @Step
    public NotifySMSPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
