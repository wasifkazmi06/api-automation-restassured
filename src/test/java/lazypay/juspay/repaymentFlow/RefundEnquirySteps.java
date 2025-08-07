package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.RefundEnquiry;
import api.lazypay.juspay.repayment.RepayRefund;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.lazypay.juspay.repaymentFlow.RefundEnquiryPojo;
import pojos.lazypay.juspay.repaymentFlow.RepayRefundPojo;

import java.util.HashMap;
import java.util.Map;

public class RefundEnquirySteps {

            public static RefundEnquiry refundEnquiry;
            public static RefundEnquiryPojo refundEnquiryPojo=new RefundEnquiryPojo();
            public  static RepayRefundPojo repayRefundPojo= new RepayRefundPojo();
            public static RepayRefund repayRefund;


    static {
        try {
            refundEnquiry = new RefundEnquiry();
            repayRefund=new RepayRefund();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public RefundEnquirySteps() throws Exception {
    }


    @Step
    public static void refundEnquiryFortheUser() throws Exception{
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("mock","true");
        queryParamDetails.put("lpTxnId",repayRefundPojo.lpTxnId);

        refundEnquiryPojo=refundEnquiry.get(queryParamDetails,headerDetails);
        Assert.assertEquals(refundEnquiryPojo.status,"SUCCESS","Refund Transaction is in Failed");

        }



    }

