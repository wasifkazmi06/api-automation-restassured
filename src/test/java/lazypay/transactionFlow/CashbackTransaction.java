package lazypay.transactionFlow;

import api.lazypay.transaction.Cashback;
import api.platform.GetUserData;
import lazypay.LazypayConstants;
import lazypay.MakeTransaction;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.CashbackPojo;
import pojos.platform.getUserData.UserData;
import util.StringTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CashbackTransaction {

    static CashbackPojo cashbackPojo = new CashbackPojo();
    static Cashback cashback;
    static public GetUserData getUserData;
    static public UserData userData;
    static String cashbackRequest = null;

    public static ResultSet rs;

    public static String cashBackAmount = System.getProperty("cashbackAmount");
    public static String transactionId = System.getProperty("transactionId");

    static {
        try {
            cashback = new Cashback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CashbackTransaction() {
    }

    @Test
    public static void cashbackTransaction() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile",MakeTransaction.mobile);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        Assert.assertEquals(userData.getPrimaryMobile().getValue(), MakeTransaction.mobile, "User mobile not matching in platform get user response!");
        String lpUserId = userData.getId();

        if (lpUserId == null || lpUserId.isEmpty()) {

            rs = TransactionData.getUserDetail(MakeTransaction.mobile);
            while (rs.next()) {
                lpUserId = rs.getString("uuid");
            }
        }


        if (lpUserId != null) {
            MakeTransaction.makeLPOTPTransaction(MakeTransaction.mobile, "", "v0", "v0",
                    MakeTransaction.merchantAccessKey, MakeTransaction.amount, "", false);

            cashbackRequest = new StringTemplate(LazypayConstants.CASHBACK_REQUEST)
                    .replace("amount", cashBackAmount)
                    .replace("lpUserId", lpUserId)
                    .replace("parentTxnId", MakeTransaction.payPojo.transactionId)
                    .replace("campaignId", UUID.randomUUID().toString())
                    .toString();


            cashbackPojo = cashback.post(headerDetails, cashbackRequest);

            Assert.assertEquals(cashbackPojo.status.toLowerCase(),  "success", "Check that cashback amount is greater than transaction amount");
            Assert.assertEquals(cashbackPojo.reason, "Cashback txns successfully created.",  "Check that Cashback amount is is greater than transaction amount");

        } else {
            log.warn("User doesn't have account in Lazypay");
        }


    }


    @Test
    public static void cashbackTransactionOnly() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile",MakeTransaction.mobile);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        Assert.assertEquals(userData.getPrimaryMobile().getValue(), MakeTransaction.mobile, "User mobile not matching in platform get user response!");
        String lpUserId = userData.getId();

        if (lpUserId == null || lpUserId.isEmpty()) {

            rs = TransactionData.getUserDetail(MakeTransaction.mobile);
            while (rs.next()) {
                lpUserId = rs.getString("uuid");
            }
        }


        if (lpUserId != null) {
            cashbackRequest = new StringTemplate(LazypayConstants.CASHBACK_REQUEST)
                    .replace("amount", cashBackAmount)
                    .replace("lpUserId", lpUserId)
                    .replace("parentTxnId", transactionId)
                    .replace("campaignId", UUID.randomUUID().toString())
                    .toString();


            cashbackPojo = cashback.post(headerDetails, cashbackRequest);

            Assert.assertEquals(cashbackPojo.status.toLowerCase(),  "success", "Check that cashback amount is greater than transaction amount");
            Assert.assertEquals(cashbackPojo.reason, "Cashback txns successfully created.",  "Check that Cashback amount is is greater than transaction amount");

        } else {
            log.warn("User doesn't have account in Lazypay");


        }


    }


    @AfterClass
    public void finishUp() throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

}
