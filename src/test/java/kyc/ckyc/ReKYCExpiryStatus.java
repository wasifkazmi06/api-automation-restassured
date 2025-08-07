package kyc.ckyc;
import api.kyc.apis.ExpireKYCStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import kyc.dataProviders.KYCExpiryStatusDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.KYCStatusPojo;
import java.util.HashMap;
import java.util.Map;

/*
author - shruthi.marjedi@paysense.in
task- LPKYC-1573
*/

@Slf4j
public class ReKYCExpiryStatus {

    HashMap<String,String> headers = new HashMap<>();
    Map<String,Object> queryparams=new HashMap<>();
    String pathparam="";
    CheckKYCStatus checkKYCStatus=new CheckKYCStatus();

    ExpireKYCStatus expireKYCStatus=new ExpireKYCStatus();

    public ReKYCExpiryStatus() throws Exception {
    }

    @Description("Check KYC Expiry status of a user")
    @Feature("KYC")
    @Test(dataProvider = "KYCExpiryStatusDataProvider", dataProviderClass = KYCExpiryStatusDataProvider.class)
    public void KYCExpired(String testCase,String pathParam,int expectedResponseCode,String expectedStatus){
        headers.put("cache-control", "no-cache");
        pathparam=pathParam;
        Response response =expireKYCStatus.getWithParam(queryparams, headers,pathparam);
        KYCStatusPojo kycStatusPojo = response.getBody().as(KYCStatusPojo.class);
        if(testCase.equalsIgnoreCase("KYCRequestWithoutPathParam")) Assert.assertEquals(response.getStatusCode(),404);
        else checkKYCStatus.validateResponse(response.getStatusCode(),expectedResponseCode,kycStatusPojo.status,expectedStatus);
        log.info("KYC Status of the user is checked for :  " + testCase);
    }

}