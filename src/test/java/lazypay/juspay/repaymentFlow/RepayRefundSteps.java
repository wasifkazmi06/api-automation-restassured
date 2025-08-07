package lazypay.juspay.repaymentFlow;

//import api.lazypay.juspay.repayment.InitiateRepayProcess;
import api.lazypay.juspay.repayment.RepayRefund;
import io.qameta.allure.Step;
import lazypay.LazypayConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
//import pojos.lazypay.juspay.repaymentFlow.InitiateRepayProcessPojo;
import pojos.lazypay.juspay.repaymentFlow.RepayRefundPojo;
import util.StringTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static lazypay.juspay.repaymentFlow.CreateOrderSteps.transactionId;
import static lazypay.juspay.repaymentFlow.RepayDetailsSteps.identifier;

public class RepayRefundSteps {

    public static RepayRefundPojo repayRefundPojo=new RepayRefundPojo();

    public static RepayRefund repayRefund;


    public static ResultSet rs_trans;


    static {
        try {
            repayRefund = new RepayRefund();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public RepayRefundSteps() throws Exception {
    }
    @Step
    @Test
    public void verifyRepayRefund() throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        double lpoutstanding=5.0;
        String merchantTxnId=null;


        rs_trans= RepaymentData_JP.getTransactionDetail(transactionId);
        while(rs_trans.next()) {

            merchantTxnId = rs_trans.getString("merchantTxnId");
        }
        String initiateRepayRequest= new StringTemplate(LazypayConstants.REPAYREFUND_REQUEST_CC)
                .replace("amount",String.valueOf(lpoutstanding)) //need to fetch from DB
                .replace("transactionId", merchantTxnId)
                .replace("identifier",identifier)
                .toString();

        repayRefundPojo = repayRefund.post(headerDetails, initiateRepayRequest);
        Assert.assertEquals(repayRefundPojo.respMessage,"Transaction is in progress","Incorrect response ,no refund is occuring");
        Assert.assertEquals(repayRefundPojo.status,"IN_PROGRESS","Incorrect response ,no refund is occuring");

    }
    @Step
    public void verifyInValidRepayRefund( ) throws SQLException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        double lpoutstanding=0.0;
        String merchantTxnId=null;

        rs_trans= RepaymentData_JP.getTransactionDetail(transactionId);

        while (rs_trans.next()) {
            merchantTxnId=rs_trans.getString("merchantTxnId");
            lpoutstanding=rs_trans.getDouble("amount");

        }

        String initiateRepayRequest= new StringTemplate(LazypayConstants.REPAYREFUND_REQUEST_CC)
                .replace("amount",String.valueOf(lpoutstanding)) //need to fetch from DB
                .replace("transactionId", merchantTxnId)
                .replace("identifier",identifier)
                .toString();

        repayRefundPojo = repayRefund.post(headerDetails, initiateRepayRequest);
        Assert.assertEquals(repayRefundPojo.respMessage,"Refund has failed","Incorrect response ,no refund is occuring");
        Assert.assertEquals(repayRefundPojo.status,"FAIL","Incorrect response ,no refund is occuring");

    }
}
