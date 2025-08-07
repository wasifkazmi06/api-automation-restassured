package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.RefundEnquiryPojo;

import java.util.HashMap;
import java.util.Map;

public class RefundEnquiry extends BaseAPI<RefundEnquiryPojo> {

    public RefundEnquiry() throws Exception {
        super(Uri.REFUNDENQUIRY_JP, RefundEnquiryPojo.class);
    }
    @Step
    public RefundEnquiryPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails ) {
        return super.get(queryParamDetails, headerDetails);
    }
}
