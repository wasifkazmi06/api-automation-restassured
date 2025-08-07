package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.GetDocumentByIdPojo;

import java.util.HashMap;
import java.util.Map;

public class GetDocumentById  extends BaseAPI<GetDocumentByIdPojo> {

    public GetDocumentById() throws Exception {
        super(Uri.GET_DOCUMENT_BY_ID, GetDocumentByIdPojo.class);
    }

    @Step
    public GetDocumentByIdPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
