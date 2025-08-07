package kyc.ckyc;

import api.kyc.apis.FetchKYCStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import kyc.dataProviders.KYCStatusCheckDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.KYCStatusPojo;

import java.util.HashMap;
import java.util.Map;

/*
author - shruthi.marjedi@paysense.in
task- LPKYC-1571
*/

@Slf4j
public class CheckKYCStatus {

    FetchKYCStatus fetchKYCStatus = new FetchKYCStatus();
    HashMap<String, String> headerDetailsInitiate = new HashMap<>();
    Map<String, Object> queryParams = new HashMap<>();

    public CheckKYCStatus() throws Exception {
    }

    @Description("Check KYC status of a user")
    @Feature("KYC")
    @Test(dataProvider = "KycStatusDataProvider", dataProviderClass = KYCStatusCheckDataProvider.class)
    public void testKycStatus(String testCase, String uuid, String product, String expectedStatus)  {
        headerDetailsInitiate.put("cache-control", "no-cache");
        queryParams.put("uuid", uuid);
        queryParams.put("product", product);
        Response response =fetchKYCStatus.getWithResponse(queryParams, headerDetailsInitiate);
        KYCStatusPojo kycStatusPojo = response.getBody().as(KYCStatusPojo.class);
        if(testCase.equalsIgnoreCase("ValidQueryParamter")) validateResponse(response.getStatusCode(),200,kycStatusPojo.kycStatus,expectedStatus);
        else validateResponse(response.getStatusCode(),400,kycStatusPojo.errorCode,expectedStatus);
        log.info("KYC Status of the user is checked for :  " + testCase);
    }

    public void validateResponse(int actualResponseCode,int expectedResponseCode,String actualStatusCode,String expectedStatusCode){
        Assert.assertEquals(actualResponseCode, expectedResponseCode);
        Assert.assertEquals(actualStatusCode,expectedStatusCode);

    }
}

