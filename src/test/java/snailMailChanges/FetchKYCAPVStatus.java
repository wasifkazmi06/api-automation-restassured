package snailMailChanges;

import api.snailmailChanges.FetchAPVStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.snailMail.APVStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FetchKYCAPVStatus extends snailmailTestData {

    public FetchKYCAPVStatus() throws Exception{
        super();
    }
    FetchAPVStatus apvStatus = new FetchAPVStatus();
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();

    @Description("To check user's apv status updated in KYC service")
    @Feature("SnailMail_HeimdallChanges")
    @Test(priority = 1,    dataProvider = "getAPVStatusTestdata")
    public void kycAPVStatusTests(String testcase, String uuid, String source){
        log.info("Getting started with the Pull APV status validations");
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");
        queryParamDetails.put("uuid",uuid);
        queryParamDetails.put("source",source);

        APVStatusResponsePojo kycAPVStatusResponseDetails = apvStatus.get(queryParamDetails, headerDetails);
        switch(testcase) {
            case "Valid_UUID&SOURCE":
            case "Valid_SOURCE_BNPL":
            case "Valid_SOURCE_XPRESS":
            case "WithUserHavingNoAPVDOC":
            case "Invalid_UUID":
                try {
                    Assert.assertEquals(kycAPVStatusResponseDetails.getStatus(),"SUCCESS","Incorrect status");
                    Assert.assertNotNull(kycAPVStatusResponseDetails.getData(), "There is No file present in Remote folder on the pull date to fetch the apv status");
                    log.info("User's apv status is : " + kycAPVStatusResponseDetails.getData().getKycApvStatus());
                }catch (AssertionError e){
                    log.info("No doc present in the file / User not present");
                    if(kycAPVStatusResponseDetails.getStatus().equals("404")){
                        Assert.assertEquals(kycAPVStatusResponseDetails.getErrorCode(), "DOCUMENT_NOT_FOUND", "incorrect error code");
                        Assert.assertEquals(kycAPVStatusResponseDetails.getMessage(), "Document not found", "incorrect error message");
                        log.info("errorCode: " + kycAPVStatusResponseDetails.getErrorCode() + " And errorMessage: " + kycAPVStatusResponseDetails.getErrorMsg());
                    }else{
                        Assert.assertEquals(kycAPVStatusResponseDetails.getStatus(), "400", "incorrect status");
                        Assert.assertEquals(kycAPVStatusResponseDetails.getErrorCode(), "USER_NOT_FOUND", "incorrect error code");
                        Assert.assertEquals(kycAPVStatusResponseDetails.getMessage(), "User not found", "incorrect error message");
                        log.info("errorCode: " + kycAPVStatusResponseDetails.getErrorCode() + " And errorMessage: " + kycAPVStatusResponseDetails.getErrorMsg());
                    }
                       }
                break;
            case "WithEmpty/Null_UUID":
                Assert.assertEquals(kycAPVStatusResponseDetails.getStatus(), "400", "incorrect status");
                Assert.assertEquals(kycAPVStatusResponseDetails.getErrorCode(), "INVALID_UUID", "incorrect error code");
                Assert.assertEquals(kycAPVStatusResponseDetails.getMessage(), "UUID cannot be empty or null", "incorrect error message");
                log.info("errorCode: " + kycAPVStatusResponseDetails.getErrorCode() + " And errorMessage: " + kycAPVStatusResponseDetails.getErrorMsg());
                break;
            case "Invalid_SOURCE":
            case "WithEmpty/Null_SOURCE":
                Assert.assertEquals(kycAPVStatusResponseDetails.getStatus(), "400", "incorrect status");
                Assert.assertEquals(kycAPVStatusResponseDetails.getErrorCode(), "INVALID_SOURCE", "incorrect error code");
                Assert.assertEquals(kycAPVStatusResponseDetails.getMessage(), "Source can only be BNPL/BBPS/XPRESS", "incorrect error message");
                log.info("errorCode: " + kycAPVStatusResponseDetails.getErrorCode() + " And errorMessage: " + kycAPVStatusResponseDetails.getErrorMsg());
                break;
        }
    }
}
