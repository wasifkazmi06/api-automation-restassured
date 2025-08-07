package lazypay.userOnboarding;

import api.lazypay.userOnboarding.UserOnboardingV1;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.LazypayConstants;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pojos.lazypay.userOnboardingFlow.UserOnboardingPojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import io.restassured.http.*;
import util.StringTemplate;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MakeUserOnboard {

    public static UserOnboardingV1 userOnboardingV1;

    public static String mobile = System.getProperty("mobile");
    public static String merchantAccessKey = System.getProperty("merchantAccessKey");
    public static String source = System.getProperty("source");
    public static String email = System.getProperty("email");
    public static String isMigrated = System.getProperty("isMigrated");
    public static String productVersionId = System.getProperty("productVersionId");

    public static ResultSet rs;
    public static String accountId;



    public MakeUserOnboard() throws Exception {

    }

    static {
        try {
             userOnboardingV1 = new UserOnboardingV1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @BeforeSuite(alwaysRun = true)
    public void verifyUserState(){

        if(isMigrated.equalsIgnoreCase("Existing")) {
            UserOnboardingData.updateMigratingUser(mobile,"null","1");
            UserOnboardingData.deleteAccount(mobile);
        }
        else if(isMigrated.equalsIgnoreCase("Yes")) {
            UserOnboardingData.deleteAccount(mobile);
            UserOnboardingData.deleteUser(mobile);
        }

        Reporter.log("\n Users updated in lazypay.users and lazypay.accounts", true);

    }


    @AfterClass
    public void finishUp() throws SQLException, ClassNotFoundException {
        Lazypay_MySQL_DBAccessObject.closeMySqlDbConnection();
    }

    @Description("To verify user onboarding of a new user")
    @Feature("UserOnboardingV1")
    @Test(priority = 1, groups = {"regression", "sanity"})
    public void UserOnboardingV1_NewUser() throws Exception {


        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("mobile",mobile);
        queryParamDetails.put("source",source);
        queryParamDetails.put("accessKey",merchantAccessKey);
        queryParamDetails.put("email",email);


        UserOnboardingPojo userOnboardingPojo = userOnboardingV1.post(queryParamDetails, headerDetails, "");

        Assert.assertNotNull(userOnboardingPojo.uuid, "verify that user is valid and not present in LP users");
        Assert.assertNotNull(userOnboardingPojo.mobile, "verify that user is valid and service is up");
        Reporter.log("\nResponse status:" + userOnboardingPojo.status, true);
        Allure.addAttachment("UserOnboardingV1_NewUser", "Onboarding Response status: " + userOnboardingPojo.status);

    }

    @Description("To verify user onboarding of a new user")
    @Feature("UserOnboardingV1")
    @Test(priority = 2, groups = {"regression", "sanity"})
    public void VerifyLedgerCustId() throws Exception {
            String ledgerCustId = "";

            rs = UserOnboardingData.getUserDetail(mobile);
            while (rs.next()) {
                ledgerCustId = rs.getString("ledgerCustId");

            }
            if(source.equals("APP_BACKEND"))
                Assert.assertNull(ledgerCustId);
            else
                Assert.assertNotNull(ledgerCustId, "verify that user should be created in secureApp");

    }

    @Description("To verify user onboarding of a new user")
    @Feature("UserOnboardingV1")
    @Test(priority = 3, groups = {"regression", "sanity"})
    public void VerifyAccountId() throws Exception {

        rs = UserOnboardingData.getAccountDetail(mobile);
            while (rs.next()) {
        accountId = rs.getString("account_id");

    }
        if(source.equals("APP_BACKEND"))
            Assert.assertNull(accountId);
        else
            Assert.assertNotNull(accountId, "verify that user should be created in secureApp");

}


    @Description("To update product version of user")
    @Feature("UserOnboardingV1")
    @Test(priority = 4, groups = {"regression", "sanity"})
    public void updateProductVersion() {
            if(productVersionId.equalsIgnoreCase("112") || productVersionId.equalsIgnoreCase("")
                || source.equalsIgnoreCase("APP_BACKEND")) {
                Allure.addAttachment("updateProductVersion", "skipping because productVersionId default or empty, productVersionId: "+ productVersionId);
                return;
            }

        String accountVersionRequest = new StringTemplate(LazypayConstants.ACCOUNT_VERSION)
                .replace("request_id", util.ReturnRandomTxnId.returnRandomNumber())
                .replace("account_id", accountId)
                .replace("product_version_id", productVersionId)
                .toString();


        given().header("X-Auth-Token", "A0002257153714151895982!sJ5+eEEQnfWijc7dg9HSj0bD1xwHbQW6C/h1sCw38skBVSvlx9EiU0ZpC088wRXQ/XMOTfEnFmVm4WtnIEPYsTYjcLk=")
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .and().body(accountVersionRequest)
                .when().post("https://core-api-tm-sbox.lazypay.net/v1/account-updates")
                .then().assertThat()
                .statusCode(200).and()
                .body("product_version_update.product_version_id", equalTo(productVersionId));

        UserOnboardingData.updateLedgerProductVersionInAccount(mobile, productVersionId);

        Reporter.log("Account Id: " + accountId + " linked with productVersionId: "+ productVersionId, true);
        Allure.addAttachment("updateProductVersion", "Account Id: " + accountId + " linked with productVersionId: "+ productVersionId);
    }

}






