package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.FdDetails;

import java.util.HashMap;
import java.util.Map;

public class GetFdSummary extends BaseAPI<FdDetails>
{
    public GetFdSummary() throws Exception {
        super(Uri.SECUREDCARD_FD_SUMMARY, FdDetails.class);
    }
    @Step
    public FdDetails get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
