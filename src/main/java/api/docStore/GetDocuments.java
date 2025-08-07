package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.GetDocumentsPojo;

import java.util.HashMap;
import java.util.Map;

public class GetDocuments extends BaseAPI<GetDocumentsPojo> {

    public GetDocuments() throws Exception {
        super(Uri.GET_DOCUMENTS, GetDocumentsPojo.class);
    }

    @Step
    public GetDocumentsPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}