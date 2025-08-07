package Xpress;

import io.restassured.response.Response;
import org.testng.Assert;
import pojos.Xpress.FetchUserStatusResponsePojo;
import util.StringTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SecureService extends XpressData {
    public SecureService() throws Exception {
    }


    public static void fetchStatusAndUpdateBnplLimit(String mobile) {
        Response updateBnplLimitResponse = null;
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("mobile", mobile);
        Response fetchBnplStatus = fetchUserStatus.getWithResponse(queryParams, xpressGenericHeaders());
        FetchUserStatusResponsePojo fetchUserStatusResponsePojo = fetchBnplStatus.as(FetchUserStatusResponsePojo.class);
        if (String.valueOf(fetchBnplStatus.statusCode()).contains("404") || fetchUserStatusResponsePojo.availableCreditLimit == 0.0) {
            updateBnplLimitResponse = riskUpdateCreditOverride.postWithResponse(xpressGenericHeaders(), createBnplLimitPayload(mobile));
            if (!String.valueOf(updateBnplLimitResponse.statusCode()).equals("200")) {
                Assert.assertEquals(updateBnplLimitResponse.statusCode(), 200, "Error in updating BNPL limit as the status code is not correct");
            } else {
                log.info("BNPL limit is Successfully updated for xpress user");
            }
        }
        Assert.assertEquals(fetchBnplStatus.statusCode(), 200, "Fetch BNPL status api failed as the status code is not correct.");
        return;
    }

    public static String createBnplLimitPayload(String mobile) {
        String updateBnplReqBody = null;
        updateBnplReqBody = new StringTemplate(XpressData.UPDATE_BNPL_LIMIT_REQUEST)
                .replace("mobile", mobile)
                .toString();
        return updateBnplReqBody;
    }
}
