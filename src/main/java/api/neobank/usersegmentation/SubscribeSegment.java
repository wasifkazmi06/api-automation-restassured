package api.neobank.usersegmentation;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.usersegmentation.User_Segmentation_Pojo;

import java.util.HashMap;
import java.util.Map;

public class SubscribeSegment extends BaseAPI<User_Segmentation_Pojo> {
    public SubscribeSegment() throws Exception {
        super(Uri.US_SUBSCRIBE, User_Segmentation_Pojo.class);
    }

    @Step
    public User_Segmentation_Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }

}