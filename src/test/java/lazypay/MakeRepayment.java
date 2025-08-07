package lazypay;

import api.lazypay.juspay.repayment.CheckRepay;
import api.lazypay.juspay.repayment.CreateOrder;
import api.lazypay.juspay.repayment.UpdateRepayStatus;
import api.lazypay.repayment.RepayDetails;
import constants.UtilConstants;
import lazypay.juspay.repaymentFlow.RepaymentData_JP;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.juspay.repaymentFlow.CheckRepayPojo;
import pojos.lazypay.juspay.repaymentFlow.CreateOrderPojo;
import pojos.lazypay.juspay.repaymentFlow.UpdateRepayStatusPojo;
import pojos.lazypay.repaymentFlow.RepayDetailsPojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MakeRepayment {

    public  static CreateOrderPojo createOrderPojo= new CreateOrderPojo();
    public static CheckRepayPojo checkRepayPojo=new CheckRepayPojo();
    public static RepayDetailsPojo repayDetailsPojo = new RepayDetailsPojo();
    public static UpdateRepayStatusPojo updateRepayStatusPojo = new UpdateRepayStatusPojo();

    public static CreateOrder createOrder;
    public static CheckRepay checkRepay;
    public static RepayDetails repayDetails;
    public static UpdateRepayStatus updateRepayStatus;

//    public static String repaymentMethod = System.getProperty("repaymentMethod");
//    public static double repayAmount = Double.parseDouble(System.getProperty("repayAmount"));
//    public static String repayAmountType = System.getProperty("repayAmountType");


    public static String repaymentMethod;
    public static double repayAmount;
    public static String repayAmountType;

    public static String paymentMode;
    public static String bankCode;
    public static String vpa;
    public static boolean repayFlag = true;

    static {
        try {
            createOrder = new CreateOrder();
            checkRepay = new CheckRepay();
            repayDetails = new RepayDetails();
            updateRepayStatus = new UpdateRepayStatus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MakeRepayment() {

        repaymentMethod = System.getProperty("repaymentMethod");
        repayAmount = Double.parseDouble(System.getProperty("repayAmount"));
        repayAmountType = System.getProperty("repayAmountType");
    }

    //use when class instantiated from another class
    public MakeRepayment(String repaymentMethod, double repayAmount, String repayAmountType) {
        this.repaymentMethod = repaymentMethod;
        this.repayAmount = repayAmount;
        this.repayAmountType = repayAmountType;
    }

    public void verifyRepayDetails(String mobile) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, mobile);

        if(repayDetailsPojo.totalOutstanding<=0){
           log.info("User has 0 outstanding hence repayment is not possible");
           repayFlag = false;
        }

        Assert.assertNotNull(repayDetailsPojo.identifier, "Check if user exists in lazypay DB.");
    }


    public RepayDetailsPojo getRepayDetails(String mobile, String dataFromRepayDetail) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails,mobile);


        if(repayDetailsPojo.totalOutstanding<=0){
            log.info("User has 0 outstanding hence repayment is not possible");
        }
        Assert.assertNotNull(repayDetailsPojo.identifier, "Check if user exists in lazypay DB.");
        return repayDetailsPojo;
    }

    public static void getRepayDetails(String mobile) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, mobile);

        if(repayDetailsPojo.totalOutstanding<=0){
            log.info("User has 0 outstanding hence repayment is not possible");
        }

        Assert.assertNotNull(repayDetailsPojo.identifier, "Check if user exists in lazypay DB.");
        Assert.assertNotNull(repayDetailsPojo.totalOutstanding, "Check if user has some outstandinng.");
    }



    public  void makeRepaymentMethod(String mobile) {
    HashMap<String, String> headerDetails = new HashMap<>();

    verifyRepayDetails(mobile);

    String createOrderRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
            .replace("amount",String.valueOf(repayDetailsPojo.totalOutstanding))
            .replace("paymentMode", RepaymentData_JP.DC_PAYMENT_METHOD)
            .replace("identifier",repayDetailsPojo.identifier)
            .replace("vpa", "")
            .replace("bankCode", "")
            .toString();

    createOrderPojo = createOrder.post(headerDetails, createOrderRequest);

    Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
    Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
    Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");

    updateRepayStatus();

    getOrderStatus();

    }

    @Test
    public void makeRepaymentMethodV1() {

        HashMap<String, String> headerDetails = new HashMap<>();

        verifyRepayDetails(MakeTransaction.mobile);

        switch (repaymentMethod){
            case "UPI":
                paymentMode = RepaymentData_JP.UPI_PAYMENT_METHOD;
                bankCode = "";
                vpa=RepaymentData_JP.USER_TEST_VPA_JP;
                break;
            case "NetBanking":
                paymentMode = RepaymentData_JP.NB_PAYMENT_METHOD;
                bankCode = RepaymentData_JP.BANK_CODE;
                vpa="";
                break;
            case "CreditCard":
                paymentMode = RepaymentData_JP.CC_PAYMENT_METHOD;
                bankCode = "";
                vpa="";
                break;
            case "DebitCard":
                paymentMode = RepaymentData_JP.DC_PAYMENT_METHOD;
                bankCode = "";
                vpa="";
                break;
        }

        switch (repayAmountType){
            case "PayableOutstanding":
                repayAmount=repayDetailsPojo.payableOs;
                break;
            case "DueAmount":
                repayAmount=repayDetailsPojo.dueAmount;
                break;
            case "MAD":
                repayAmount=repayDetailsPojo.madAmount;
                break;
            case "Adhoc":
                break;
        }

        String createOrderRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(repayAmount))
                .replace("paymentMode", paymentMode)
                .replace("identifier",repayDetailsPojo.identifier)
                .replace("vpa", vpa)
                .replace("bankCode", bankCode)
                .toString();

        createOrderPojo = createOrder.post(headerDetails, createOrderRequest);

        Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
        Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
        Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");

        updateRepayStatus();

        getOrderStatus();

    }

    public void updateRepayStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("api-key", ReadProperties.testConfigMap.get(UtilConstants.API_KEY).toString());

        String updateRepayStatusRequest = new StringTemplate(LazypayConstants.UPDATE_REPAY_STATUS)
                .replace("merchantTxnId","")
                .replace("orderId", createOrderPojo.orderId)
                .replace("pgTxnId",createOrderPojo.lpTxnId)
                .replace("reason", "Test")
                .replace("status", "SUCCESS")
                .replace("type", "REPAY")
                .toString();

        updateRepayStatusPojo = updateRepayStatus.put(queryParamDetails, headerDetails, updateRepayStatusRequest);

        Assert.assertEquals(updateRepayStatusPojo.status,"SUCCESS", "");
        Assert.assertEquals(updateRepayStatusPojo.transactionId, createOrderPojo.lpTxnId, "Check if user has some outstandinng.");
        Assert.assertEquals(updateRepayStatusPojo.type, "REPAY", "Check if user has some outstandinng.");
//        Assert.assertEquals(updateRepayStatusPojo.mobile, MakeTransaction.mobile, "Check if user has some outstandinng.");

    }

    public void getOrderStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("orderId", createOrderPojo.orderId);
        queryParamDetails.put("mock","true");

        checkRepayPojo=checkRepay.get(queryParamDetails,headerDetails);

        Assert.assertEquals(checkRepayPojo.status, "SUCCESS" ,"Transaction Failed");
        Assert.assertNotNull(checkRepayPojo.transactionId,"Process Repayment failed as transaction amount is empty");

    }

}
