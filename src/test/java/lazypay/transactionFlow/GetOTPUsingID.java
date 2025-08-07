package lazypay.transactionFlow;

import api.lazypay.transaction.GetOTPByID;
import pojos.lazypay.transactionFlow.GetOTPByIDPojo;
import java.util.HashMap;
import java.util.Map;

public class GetOTPUsingID {

    static GetOTPByID getOTPByID;
    static GetOTPByIDPojo getOTPByIDPojo;

    static {
        try {
            getOTPByID = new GetOTPByID();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getOTPByIDMethod(String tenantId, String otpId) throws  Exception{
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("tenantId", tenantId);
        queryParamDetails.put("otpId", otpId);
        getOTPByIDPojo = getOTPByID.get(queryParamDetails, headerDetails);
        return getOTPByIDPojo.otpValue;

    }
}
