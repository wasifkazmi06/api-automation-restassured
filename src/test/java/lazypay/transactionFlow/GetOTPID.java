package lazypay.transactionFlow;

import api.lazypay.transaction.GetAppOTP;
import pojos.lazypay.transactionFlow.GetAppOTPPojo;
import java.util.HashMap;
import java.util.Map;

public class GetOTPID {

    static GetAppOTP getAppOTP;
    static GetAppOTPPojo getAppOTPPojo;
    static String otpID;

    static {
        try {
            getAppOTP = new GetAppOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAppOTPMethod(String userId, String txnId) throws  Exception{
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("userId", userId);
        queryParamDetails.put("txnId", txnId);
        getAppOTPPojo = getAppOTP.get(queryParamDetails, headerDetails);
        try{
            for(int i=0; i<getAppOTPPojo.transactions.size(); i++){
                if(getAppOTPPojo.transactions.get(i).txnId.equals(txnId)){
                    otpID = getAppOTPPojo.transactions.get(i).otpId;
                }
            }
        }catch (NullPointerException e){
            }
        return otpID;
    }
}
