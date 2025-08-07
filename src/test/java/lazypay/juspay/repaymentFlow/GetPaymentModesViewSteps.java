package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.GetPaymentModesView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import constants.UtilConstants;
import io.qameta.allure.Step;
import mbe.authentication.AuthTestData;
import mbe.authentication.DeviceConfigTest;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.juspay.repaymentFlow.GetPaymentModesViewPojo;
import util.ReadProperties;

import java.util.*;

import static mbe.authentication.APPLoginUser.OauthToken;


public class GetPaymentModesViewSteps {

    public static GetPaymentModesViewPojo getPaymentModesViewPojo=new GetPaymentModesViewPojo();
    public static GetPaymentModesView getPaymentModesView;
    static String card;
    static String netBanking;
    static String upi;
    static String savedCard;
    static String savedUPI;
    static String savedNetBanking;

    static {
        try {
            getPaymentModesView = new GetPaymentModesView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetPaymentModesViewSteps() throws Exception {
    }

    @Step
    public void verifyAllPaymentModesFortheUser (String userMobile) throws Exception {

      /*  SendOtp.SendingOTP(userMobile);
        String OauthToken= LoginUser.LoginIntoApp(userMobile);*/
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization",OauthToken);
        headerDetails.put("appversion", JPRepaymentTests.appVersion);
        headerDetails.put("userAgent",AuthTestData.userAgent);
        headerDetails.put("deviceId",AuthTestData.deviceId);
        headerDetails.put("location",AuthTestData.location);
        headerDetails.put("deviceIP",AuthTestData.deviceIP);
        headerDetails.put("requestId",AuthTestData.requestId);
        queryParamDetails.put("product","BNPL");
        String toCheckPaymentOption;


        getPaymentModesViewPojo = getPaymentModesView.get(queryParamDetails, headerDetails);
         JsonNode cardGroup=getPaymentModesViewPojo.getOtherPaymentOptionsCardGroup();
        if(cardGroup.isArray()) {
            ArrayNode arrayNode = (ArrayNode) cardGroup;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                JsonNode arrayValues = arrayElement.get("cardType");
                String valueForCardType = arrayValues.asText();
                if (valueForCardType.equals("CC_DC")) {
                    card = valueForCardType;
                } else if (valueForCardType.equals("nb")) {
                    netBanking = valueForCardType;
                } else if (valueForCardType.equals("upi")) {
                    upi = valueForCardType;
                }
            }
            if(StringUtils.isEmpty(card)){
                card="NO CARD TYPE";
            }
            if(StringUtils.isEmpty(netBanking)){
                netBanking="NO  CARD TYPE";
            }
            if(StringUtils.isEmpty(upi)){
                upi="NO  CARD TYPE";
            }
        }
               if(card.equalsIgnoreCase("CC_DC")||netBanking.equalsIgnoreCase("nb")||upi.equalsIgnoreCase("upi")){
                Assert.assertTrue(true,"We can able to see the payment modes");
               }
               else{
                   Assert.assertFalse(false,"We couldnt able to see the payment modes");
               }




        JsonNode savedGroup=getPaymentModesViewPojo.getSavedPaymentOptionsCardGroup();
        if(savedGroup.isArray()){
            ArrayNode arrayNode = (ArrayNode) savedGroup;
            for(int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                JsonNode arrayValues = arrayElement.get("cardType");
                String valueForCardType = arrayValues.asText();
                if (valueForCardType.equals("DC")) {
                    savedCard = valueForCardType;
                } else if (valueForCardType.equals("nb")) {
                    savedNetBanking = valueForCardType;
                } else if (valueForCardType.equals("UPI_COLLECT")) {
                    savedUPI = valueForCardType;
                }
            }}
                if(StringUtils.isEmpty(savedCard)){
                    savedCard="NO SAVED CARD TYPE";
                }
                if(StringUtils.isEmpty(savedNetBanking)){
                    savedNetBanking="NO SAVED CARD TYPE";
                }
        if(StringUtils.isEmpty(savedUPI)){
            savedUPI="NO SAVED CARD TYPE";
        }
                if(savedCard.equalsIgnoreCase("DC")||savedNetBanking.equalsIgnoreCase("nb")||savedUPI.equalsIgnoreCase("UPI_COLLECT")){
                    Assert.assertTrue(true,"We can able to see the payment modes");
                }

                else{
                    Assert.assertFalse(false,"We couldnt able to see the payment modes");
                }

        Assert.assertEquals(getPaymentModesViewPojo.screenName,"CHECKOUT_PAYMENT_MODES","Invalid Screen Name in the Checkout Page");


    }


}
