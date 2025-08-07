package api.neobank.usersegmentation;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.usersegmentation.Get_All_Active_Segments_Pojo;
import pojos.neobank.usersegmentation.User_Segmentation_Pojo;

import java.util.HashMap;
import java.util.Map;

public class GetAllActiveSegments extends BaseAPI<Get_All_Active_Segments_Pojo> {

    public GetAllActiveSegments() throws Exception {
        super(Uri.US_GET_ALL_ACTIVE_SEGMENTS, Get_All_Active_Segments_Pojo.class);
    }

    @Step
    public Get_All_Active_Segments_Pojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
