package api.neobank.usersegmentation;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.usersegmentation.User_Segmentation_Pojo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RemoveUsersFromSegment extends BaseAPI<User_Segmentation_Pojo> {


    public RemoveUsersFromSegment() throws Exception {
        super(Uri.US_REMOVE_USERS, User_Segmentation_Pojo.class);
    }

    @Step
    public User_Segmentation_Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, File file) {
        return super.post(queryParamDetails, headerDetails, file);
    }

    @Step
    public User_Segmentation_Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.post(queryParamDetails, headerDetails);
    }

}