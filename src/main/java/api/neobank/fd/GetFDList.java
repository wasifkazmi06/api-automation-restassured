package api.neobank.fd;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.fd.ReadFdList;

import java.util.HashMap;
import java.util.Map;

public class GetFDList extends BaseAPI<ReadFdList> {

    public GetFDList() throws Exception {
        super(Uri.SECUREDCARD_FD_LIST, ReadFdList.class);
    }
    @Step
    public ReadFdList get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
