package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.CheckRepay;
import api.lazypay.juspay.repayment.CreateOrder;

import api.lazypay.juspay.repayment.RepayDetails;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import lazypay.LazypayConstants;
import lazypay.MakeTransaction;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.CheckRepayPojo;
import pojos.lazypay.juspay.repaymentFlow.CreateOrderPojo;
import pojos.lazypay.juspay.repaymentFlow.RepayDetailsPojo;
import util.StringTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static lazypay.juspay.repaymentFlow.RepayDetailsSteps.totalOutStanding;
import static lazypay.juspay.repaymentFlow.RepayDetailsSteps.identifier;

public class CreateOrderSteps {
    
    
    public  static CreateOrderPojo createOrderPojo= new CreateOrderPojo();
    public static CheckRepayPojo checkRepayPojo=new CheckRepayPojo();
    public static CreateOrder createOrder;
    public static CheckRepay checkRepay;
    public static ResultSet rs;
    public static String transactionId;

    public double lpoutstanding=0.0;
    public String orderId=null;
    public String txnId=null;
    public String statusInDB=null;
    public String ident=null;
    lazypay.repaymentFlow.RepayDetailsSteps repayDetailsSteps=new lazypay.repaymentFlow.RepayDetailsSteps ();
    public static RepayDetailsPojo repayDetailsPojo = new RepayDetailsPojo();
    public static RepayDetails repayDetails;



    static {
        try {
            createOrder = new CreateOrder();
            checkRepay = new CheckRepay();
            repayDetails = new RepayDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public CreateOrderSteps() throws Exception {
    }



    @Step
    public void createOrderRepaymentUPI() throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();

        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding)) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.UPI_PAYMENT_METHOD)
                .replace("identifier",identifier)
                .replace("vpa", RepaymentData_JP.USER_TEST_VPA_JP)
                .replace("bankCode", "")
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
        Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
        Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");
        txnId=createOrderPojo.lpTxnId;
        orderId=createOrderPojo.orderId;

        checkIfRepayTxnSuccess(orderId);

       /* rs = RepaymentData_JP.getTransactionDetail(txnId);
        while (rs.next()) {
            statusInDB = rs.getString("status");

        }*/
        Assert.assertEquals(checkRepayPojo.status,"SUCCESS","Transaction Failed");
        Assert.assertNotNull(checkRepayPojo.amount,"Process Repayment for UPI repayment failed as Transaction amount is empty");
        transactionId=checkRepayPojo.transactionId;
    }
    @Step
    
    public void createOrderRepaymentNB() throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();


        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding)) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.NB_PAYMENT_METHOD)
                .replace("identifier",identifier)
                .replace("vpa", "")
                .replace("bankCode", RepaymentData_JP.BANK_CODE)
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
        Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
        Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");
        txnId=createOrderPojo.lpTxnId.trim();
        orderId=createOrderPojo.orderId;

        checkIfRepayTxnSuccess(orderId);

       /* rs = RepaymentData_JP.getTransactionDetail(txnId);
        while (rs.next()) {
            statusInDB = rs.getString("status");

        }*/
        Assert.assertEquals(checkRepayPojo.status,"SUCCESS","Transaction Failed");
        Assert.assertNotNull(checkRepayPojo.amount,"Process Repayment for UPI repayment failed as Transaction amount is empty");
        transactionId=checkRepayPojo.transactionId;

    }

    @Step
    public void createOrderRepaymentCC() throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();


        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding)) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.CC_PAYMENT_METHOD)
                .replace("identifier",identifier)
                .replace("vpa", "")
                .replace("bankCode", RepaymentData_JP.BANK_CODE)
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
        Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
        Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");
        txnId=createOrderPojo.lpTxnId.trim();
        orderId=createOrderPojo.orderId;

        checkIfRepayTxnSuccess(orderId);

       /* rs = RepaymentData_JP.getTransactionDetail(txnId);
        while (rs.next()) {
            statusInDB = rs.getString("status");

        }*/
        Assert.assertEquals(checkRepayPojo.status,"SUCCESS","Transaction Failed");
        Assert.assertNotNull(checkRepayPojo.amount,"Process Repayment for UPI repayment failed as Transaction amount is empty");
        transactionId=checkRepayPojo.transactionId;

    }

    @Step
    public void createOrderRepaymentDC() throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();

        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding)) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.DC_PAYMENT_METHOD)
                .replace("identifier",identifier    )
                .replace("vpa", "")
                .replace("bankCode", "")
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertNotNull(createOrderPojo.lpTxnId, "Transaction ID null");
        Assert.assertNotNull(createOrderPojo.orderId,"order ID null");
        Assert.assertEquals(createOrderPojo.lpTxnStatus,"IN_PROGRESS","Invalid Status");
        txnId=createOrderPojo.lpTxnId.trim();
        orderId=createOrderPojo.orderId;

        checkIfRepayTxnSuccess(orderId);
       /* rs = RepaymentData_JP.getTransactionDetail(txnId);
        while (rs.next()) {
            statusInDB = rs.getString("status");

        }*/

        Assert.assertEquals(checkRepayPojo.status,"SUCCESS","Transaction Failed");
        Assert.assertNotNull(checkRepayPojo.amount,"Process Repayment for UPI repayment failed as Transaction amount is empty");
        transactionId=checkRepayPojo.transactionId;

    }

    @Step
    public void checkIfRepayTxnSuccess(String orderId) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        checkRepayPojo.status = null;
        queryParamDetails.put("orderId",orderId );
        queryParamDetails.put("mock","true" );
        int count =0;

        while((checkRepayPojo.status==null || !checkRepayPojo.status.equals("FAIL"))  && count <10) {
            if (!(checkRepayPojo.status==null) && checkRepayPojo.status.equals("SUCCESS")) break;
            checkRepayPojo = checkRepay.get(queryParamDetails, headerDetails);
            count++;
            Thread.sleep(7000);
        }
        Allure.addAttachment("Attempts made in repay enquiry: ", "with 7000 delay: " + count + " times");
    }


    @Step
    public void createOrderRepaymentUPIInValidAmount() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<2){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID, "", "v0", "v0",
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
        }

        ident=repayDetailsSteps.fetchIdentifier (RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID);
        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding - 1)) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.UPI_PAYMENT_METHOD)
                .replace("identifier",identifier)
                .replace("vpa", RepaymentData_JP.USER_TEST_VPA_JP)
                .replace("bankCode", "")
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

//        Assert.assertEquals(createOrderPojo.message,"Amount should be equal to total outstanding or last cycle outstanding");

        Assert.assertEquals(createOrderPojo.message,"Oops!! Something went wrong");
        Assert.assertEquals(createOrderPojo.errorCode,"LP_GENERIC_ERROR");
        Assert.assertNotNull(createOrderPojo.path);
    }

    @Step
    public void createOrderRepaymentUPIAmountLessThan1() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        identifier=repayDetailsSteps.fetchIdentifier(RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID);
        repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
        totalOutStanding=repayDetailsPojo.totalOutstanding;
        if(totalOutStanding<1){
            MakeTransaction.makeLPOTPTransaction(RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID, "", "v0", "v0",
                    TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);
            repayDetailsPojo = repayDetails.get(queryParamDetails, headerDetails, identifier);
            totalOutStanding=repayDetailsPojo.totalOutstanding;
        }

        ident=repayDetailsSteps.fetchIdentifier (RepaymentData_JP.USER_MOBILE_UPI_JP_INVALID);
        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(totalOutStanding - (totalOutStanding - .10))) //need to fetch from DB
                .replace("paymentMode", RepaymentData_JP.UPI_PAYMENT_METHOD)
                .replace("identifier",identifier)
                .replace("vpa", RepaymentData_JP.USER_TEST_VPA_JP)
                .replace("bankCode", "")
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertEquals(createOrderPojo.message,"Amount can't be less than 1.");
        Assert.assertEquals(createOrderPojo.errorCode,"INVALID_AMOUNT");
        Assert.assertNotNull(createOrderPojo.path);
    }


    @Step
    public void createOrderRepaymentWithMissingField() throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();



        String initiateRepayRequest= new StringTemplate(LazypayConstants.CREATEORDER_REQUEST)
                .replace("amount",String.valueOf(lpoutstanding)) //need to fetch from DB
                .replace("paymentMode", "")
                .replace("identifier",ident)
                .replace("vpa", RepaymentData_JP.USER_TEST_VPA_JP_INVALID)
                .replace("bankCode", "")
                .toString();

        createOrderPojo = createOrder.post(headerDetails, initiateRepayRequest);

        Assert.assertEquals(createOrderPojo.message,"Oops!! Something went wrong");
        Assert.assertEquals(createOrderPojo.errorCode,"LP_INVALID_REQ_PARAMETERS");
        Assert.assertNotNull(createOrderPojo.path);
    }



}
