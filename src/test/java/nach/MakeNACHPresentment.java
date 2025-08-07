package nach;

import api.platform.GetUserData;
import lombok.extern.slf4j.Slf4j;
import nach.presentment.GetCollectionDetailsStep;
import nach.presentment.InitiateCollectionRequestStep;
import org.testng.annotations.Test;
import static nach.presentment.GetCollectionDetailsStep.*;
import nach.presentment.NACHpresentmentTestBNPL;
import nach.presentment.NACHpresentmentTestCashLoan;
import pojos.platform.getUserData.UserData;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MakeNACHPresentment {

    public static String mobile = System.getProperty("mobile");
    public static String amount = System.getProperty("amount");
    public static String NACHProduct = System.getProperty("NACHProduct");
    public static String transactionId = System.getProperty("transactionId");
    public static String presentmentStatus = System.getProperty("presentmentStatus");
    public static String errorDescription = System.getProperty("errorDescription");

    public static String um_uuid;
    static NACHpresentmentTestBNPL nachPresentmentTestBNPL;
    static NACHpresentmentTestCashLoan nachPresentmentTestCashLoan;
    static GetCollectionDetailsStep getCollectionDetailsStep;
    static public GetUserData getUserData;
    static public UserData userData;

    static {
        try {
            nachPresentmentTestBNPL = new NACHpresentmentTestBNPL();
            nachPresentmentTestCashLoan = new NACHpresentmentTestCashLoan();
            getCollectionDetailsStep = new GetCollectionDetailsStep();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MakeNACHPresentment() {
    }

    @Test
    public static void makeNACHPresentment() throws Exception {
        BNPLCollectionInitiate(mobile, amount, NACHProduct, transactionId);
        if (presentmentStatus.equalsIgnoreCase("PASS")) {
            nachPresentmentTestBNPL.BNPLCollectionSuccessWebhook();
            nachPresentmentTestBNPL.verifySendCollectionsToLpCron();
            if (NACHProduct.equalsIgnoreCase("BNPL"))
                nachPresentmentTestBNPL.verifyCollectionSuccess();
            else
                nachPresentmentTestCashLoan.verifyCollectionSuccess();
        }
        else {
            if(!errorDescription.isEmpty()) {
                String[] errorDescriptionStringArr= new String[2];
                errorDescriptionStringArr = (errorDescription.split("-"));
                System.out.print(errorDescriptionStringArr[1]);
                NACHPresentmentData.error_description = errorDescriptionStringArr[1];
            }
            nachPresentmentTestBNPL.BNPLCollectionFailedWebhook();
            nachPresentmentTestBNPL.verifyCollectionFailed();
        }
    }

    public static void BNPLCollectionInitiate(String mobile, String amount, String product, String transactionId) throws Exception {

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile", NACHPresentmentData.razorpayNBUser_BNPL_SUCCESS);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        um_uuid = userData.getUmUuid();

        InitiateCollectionRequestStep.initiateCollection(amount, product, um_uuid, transactionId);

        getCollectionDetail(um_uuid);
        getRazorpayNBNachCollection(collectionDetails.get("methodVendorId"));
        getRazorpayOrder(razorpayNBNachSetupDetails.get("orderId"));

        if (product.equalsIgnoreCase("BNPL")) {
            nachPresentmentTestBNPL.um_uuid = um_uuid;
            nachPresentmentTestBNPL.amount = amount;
        }
        else {
            nachPresentmentTestCashLoan.um_uuid = um_uuid;
            nachPresentmentTestCashLoan.amount = amount;
        }
    }

}