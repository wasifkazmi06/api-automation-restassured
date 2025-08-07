package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import org.json.JSONObject;
import pojos.kyc.apis.UploadDocumentPojo;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class UploadDocument extends  BaseAPI<UploadDocumentPojo> {
    public UploadDocument() throws Exception {
        super(Uri.KYC_UPLOAD_DOCUMENT, UploadDocumentPojo.class);
    }

    @Step
    public UploadDocumentPojo postWithJsonHeader(Map<String, Object> queryParamDetails, HashMap<String, Object> headerDetails, File file) {
        return  super.postWithJsonHeader(queryParamDetails,headerDetails,file);
    }
}
