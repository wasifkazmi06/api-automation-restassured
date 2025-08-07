package nach.presentment;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import nach.NACHPresentmentData;
import org.testng.Reporter;

import java.sql.ResultSet;
import java.util.HashMap;

public class GetCollectionDetailsStep {



    public static String razorpayPaymentId;
    public static String orderId = null;

    public static HashMap<String, String> collectionDetails = new HashMap<>();
    public static HashMap<String, String> razorpayNBNachSetupDetails = new HashMap<>();
    public static HashMap<String, String> razorpayOrderDetails = new HashMap<>();



    static {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public GetCollectionDetailsStep() {
    }


    @Description("Fetch Collection data from collectionDb")
    @Feature("BNPLRazorPayPresentment")
    @Step
    public static void getCollectionDetail(String um_uuid) throws Exception {
        String collectionId ="";
        String collectionSetupId ="";
        String amount ="";
        String merchantTxnId ="";
        String state ="";
        String status ="";
        String methodVendorId = "";

        ResultSet rs;
        int count=0;
        while (count < 10) {
            rs = NACHPresentmentData.nachCollection(um_uuid);
            while (rs.next()) {
                collectionId = rs.getString("id");
                collectionSetupId = rs.getString("collection_setup_id");
                amount = rs.getString("amount");
                merchantTxnId = rs.getString("merchant_txn_id");
                state = rs.getString("state");
                status = rs.getString("status");
                methodVendorId = rs.getString("method_vendor_id");
            }
            if (collectionId.isEmpty() || collectionId == null) {
                count++;
                Thread.sleep(1000);
            } else count = 10;

        }
        collectionDetails.put("collectionId",collectionId);
        collectionDetails.put("collectionSetupId",collectionSetupId);
        collectionDetails.put("amount",amount);
        collectionDetails.put("merchantTxnId",merchantTxnId);
        collectionDetails.put("state",state);
        collectionDetails.put("status",status);
        collectionDetails.put("methodVendorId",methodVendorId);

    }

    @Description("Fetch Collection data from razorpay NB Nach table")
    @Feature("BNPLRazorPayPresentment")
    @Step
    public static void getRazorpayNBNachCollection(String methodVendorId) throws Exception {
        String razorpayPaymentStatus ="";
        String razorpayRejectCode ="";
        String razorpayRejectMessage ="";
        String repayDate ="";


        ResultSet rs;
        int count=0;
        while(count <10 ) {

            if(orderId == null || razorpayPaymentId == null || orderId != null) {
                rs = NACHPresentmentData.razorpayNBNachCollection(collectionDetails.get("methodVendorId"));
                while (rs.next()) {
                    orderId = rs.getString("order_id");
                    razorpayPaymentId = rs.getString("payment_id");
                    razorpayPaymentStatus = rs.getString("payment_status");
                    razorpayRejectCode = rs.getString("reject_code");
                    razorpayRejectMessage = rs.getString("reject_message");
                    repayDate = rs.getString("repay_date");

                }
                if(orderId == null || razorpayPaymentId == null){
                    count++;
                    Thread.sleep(1000);
                }
                else count = 10;
            }
        }

        if (orderId == null || razorpayPaymentId == null){
            Allure.addAttachment("Order Id : " + orderId ," razorpayPaymentId: " + razorpayPaymentId + "One of the Id remained null");
            Reporter.log("Order Id : " + orderId + " razorpayPaymentId: " + razorpayPaymentId + "One of the Id remained null");
        }

        razorpayNBNachSetupDetails.put("orderId",orderId);
        razorpayNBNachSetupDetails.put("razorpayPaymentId",razorpayPaymentId);
        razorpayNBNachSetupDetails.put("razorpayPaymentStatus",razorpayPaymentStatus);
        razorpayNBNachSetupDetails.put("razorpayRejectCode",razorpayRejectCode);
        razorpayNBNachSetupDetails.put("razorpayRejectMessage",razorpayRejectMessage);
        razorpayNBNachSetupDetails.put("repayDate",repayDate);

    }

    @Description("Fetch Collection data from razorpay order")
    @Feature("BNPLRazorPayPresentment")
    @Step
    public static void getRazorpayOrder(String orderId) throws Exception {
        String amountRazorpayOrderId = "";
        String statusRazorpayOrderId = "";
        String sourceRazorpayOrderId = "";
        String razorpayOrderId = null;

        ResultSet rs;
        int count=0;
        while(count <10 ) {

                rs = NACHPresentmentData.razorpayOrderCollection(orderId);
                while (rs.next()) {
                    razorpayOrderId = rs.getString("razor_pay_order_id");
                    amountRazorpayOrderId = rs.getString("amount");
                    statusRazorpayOrderId = rs.getString("status");
                    sourceRazorpayOrderId = rs.getString("source");

                }
                if (orderId == null) {
                    count++;
                    Thread.sleep(1000);
                } else count = 10;

        }
        razorpayOrderDetails.put("razorpayOrderId",razorpayOrderId);
        razorpayOrderDetails.put("amountRazorpayOrderId",amountRazorpayOrderId);
        razorpayOrderDetails.put("statusRazorpayOrderId",statusRazorpayOrderId);
        razorpayOrderDetails.put("sourceRazorpayOrderId",sourceRazorpayOrderId);

    }


}
