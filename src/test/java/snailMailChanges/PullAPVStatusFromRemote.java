package snailMailChanges;

import api.snailmailChanges.PullAPVStatusFromKlassicRemote;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.snailMail.SnailMailGenericResponsePojo;
import util.StringTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PullAPVStatusFromRemote extends snailmailTestData
{
    public  PullAPVStatusFromRemote()throws Exception {
        super();
    }
    PullAPVStatusFromKlassicRemote pullRequest = new PullAPVStatusFromKlassicRemote();
    String request ;
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();
    LocalDate filteredate = currentDate.minusDays(10);
    String formatted = filteredate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Description("To check user's apv status pulling from sftp remote folder of klassic")
    @Feature("SnailMail_HeimdallChanges")
    @Test(priority = 1, dataProvider = "pullStatusTestData")
    public void pullAPVStatus(String testcase, String requestId, String source, String AuthKey) {
        log.info("Getting started with the Pull APV status validations");
        if(testcase.equalsIgnoreCase("WithDateFilterParam")){
            request = new StringTemplate().replace("requestId", requestId).replace("source", source).
                    replace("pullDateFrom",formatted).toString();
        }else{request = new StringTemplate().replace("requestId", requestId).replace("source", source).toString();}
        headerDetails.put("X-API-Key", AuthKey);
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        SnailMailGenericResponsePojo pullResponseDetails = pullRequest.post(queryParamDetails, headerDetails, request);
        switch(testcase){
            case "ValidRequestIdANDSource":
            case "Valid_source_LP":
            case "Valid_source_PS":
            case "Valid_source_SMB":
            case "Valid_source_APILENDING":
            case "Valid_AuthKey_LP":
            case "Valid_AuthKey_PS":
            case "WithDateFilterParam":
                if(pullResponseDetails.getStatus().equalsIgnoreCase("SUCCESS")){
                    Assert.assertNotNull(pullResponseDetails.getData(), "There is No file present in Remote folder on the pull date to fetch the apv status");
                    log.info("User apv status: " + pullResponseDetails.getData().iterator().next().getStatus());
                }else{
                    log.info("No data present in the file");
                    Assert.assertEquals(pullResponseDetails.getStatus(), "FAILED", "incorrect response");
                    Assert.assertEquals(pullResponseDetails.getErrorCode(), "INTERNAL_SERVER_ERROR", "incorrect error code");
                    Assert.assertEquals(pullResponseDetails.getErrorMsg(), "Oops !! Something went wrong", "incorrect error message");
                    log.info("errorCode: " + pullResponseDetails.getErrorCode() +" And errorMessage: "+pullResponseDetails.getErrorMsg());
                }
                break;
            case "InvalidRequestId":
                Assert.assertEquals(pullResponseDetails.getStatus(), "FAILED", "incorrect request data");
                Assert.assertEquals(pullResponseDetails.getErrorCode(), "INTERNAL_SERVER_ERROR", "incorrect error code");
                break;
            case  "WithEmptyRequestId":
            case "WithNullRequestId":
                Assert.assertEquals(pullResponseDetails.getStatus(), "FAILED", "incorrect request data");
                Assert.assertEquals(pullResponseDetails.getErrorCode(), "INVALID_REQUESTID", "incorrect error code");
                Assert.assertEquals(pullResponseDetails.getErrorMsg(),"Request id provided is either empty or null","Incorrect error message");
                break;
            case "InvalidSource":
            case "WithEmptySource":
            case "WithNullSource":
                Assert.assertEquals(pullResponseDetails.getStatus(), "FAILED", "incorrect request data");
                Assert.assertEquals(pullResponseDetails.getErrorCode(), "INVALID_SOURCE", "incorrect error code");
                Assert.assertEquals(pullResponseDetails.getErrorMsg(),"Source provided is either empty or null","Incorrect error message");
                break;

        }



    }

}
