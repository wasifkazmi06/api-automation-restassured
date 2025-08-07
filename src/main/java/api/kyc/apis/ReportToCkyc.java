package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.ReportToCKYCPojo;

import java.util.HashMap;

public class ReportToCkyc extends BaseAPI<ReportToCKYCPojo> {
    public ReportToCkyc() throws Exception {
        super(Uri.REPORT_TO_CKYC, ReportToCKYCPojo.class);
    }

    @Step
    public String postWithoutResponseBody(HashMap<String, String> pathParam, HashMap<String, String> headerDetails) {
        return  super.postWithoutResponseBody(pathParam,headerDetails);
    }
}
