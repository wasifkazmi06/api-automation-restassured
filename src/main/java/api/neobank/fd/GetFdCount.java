package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdList;

import java.util.HashMap;
import java.util.Map;

public class GetFdCount extends BaseAPI<FdList> {

    public GetFdCount() throws Exception {
        super(Uri.SECUREDCARD_FD_COUNT, FdList.class);
    }
    @Step
    public FdList get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
