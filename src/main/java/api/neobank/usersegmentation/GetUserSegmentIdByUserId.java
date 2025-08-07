package api.neobank.usersegmentation;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.usersegmentation.User_Segmentation_Pojo;

import java.util.HashMap;
import java.util.Map;

public class GetUserSegmentIdByUserId extends BaseAPI<User_Segmentation_Pojo> {
    public GetUserSegmentIdByUserId() throws Exception {
        super(Uri.US_GET_USER_SEGMENT_ID, User_Segmentation_Pojo.class);
    }

    @Step
    public User_Segmentation_Pojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}