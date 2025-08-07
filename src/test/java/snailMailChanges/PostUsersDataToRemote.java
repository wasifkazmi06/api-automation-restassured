package snailMailChanges;

import api.snailmailChanges.PushDataToKlassicRemote;
import com.jcraft.jsch.JSchException;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.snailMail.SnailMailGenericResponsePojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PostUsersDataToRemote extends snailmailTestData {
    public PostUsersDataToRemote() throws Exception {

    }

    PushDataToKlassicRemote pushRequest = new PushDataToKlassicRemote();
    GeniricUtility genericutil = new GeniricUtility();
    String request;
    Map<String, Object> queryParamDetails = new HashMap<>();
    HashMap<String, String> headerDetails = new HashMap<>();
    LocalDateTime currentDate = LocalDateTime.now();
    int minute = currentDate.get(ChronoField.MINUTE_OF_HOUR);
    String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    @Description("To check user's data pushed to sftp server")
    @Feature("SnailMail_HeimdallChanges")
    @Test(priority = 1, dataProvider = "pushUserDataToRemote")
    public void pushUserDataToRemoteFolder(String testcase, String requestId, String source, String AuthKey) throws JSchException {
        log.info("Getting started with the Push APV request validations");
        String product = source;
        if(source==null){product=source1;}
        else if (source.equals("SMB")) {product = "SMB_PG";}

        //creating userdata for request payload
        JSONArray userData = userDataInRequestPayload(product);
        JSONObject requestPayload = new JSONObject();
        requestPayload.put("requestId", requestId);
        requestPayload.put("source", source);
        requestPayload.put("data", userData);
        request = requestPayload.toString();
        headerDetails.put("X-API-Key", AuthKey);
        headerDetails.put("Tenanat", "1");
        headerDetails.put("Content-Type", "application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        System.out.println(request);
        SnailMailGenericResponsePojo pushResponseDetails = pushRequest.post(queryParamDetails, headerDetails, request);
        switch (testcase) {
            case "ValidRequestIdANDSource":
            case "Valid_AuthKey_LP":
            case "Valid_AuthKey_PS":
                Assert.assertEquals(pushResponseDetails.getStatus(), "SUCCESS", "incorrect response");
                Assert.assertNull(pushResponseDetails.getData(), "Failed to push the data");
                break;
            case "Valid_source_LP":
            case "Valid_source_PS":
            case "Valid_source_SMB":
            case "Valid_source_APILENDING":
                Assert.assertEquals(pushResponseDetails.getStatus(), "SUCCESS", "incorrect response");
                Assert.assertNull(pushResponseDetails.getData(), "Failed to push the data");
                Assert.assertEquals(genericutil.readFileFromSFTP(source, formattedDate), userData.length(), "All data hasn't pushed to sftp");
                break;
            case "WithEmptyRequestId":
            case "WithNullRequestId":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect request data");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_REQUESTID", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Request id provided is either empty or null", "Incorrect error message");
                break;
            case "InvalidSource":
            case "WithEmptySource":
            case "WithNullSource":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect request data");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_SOURCE", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Source provided is either empty or null", "Incorrect error message");
                break;
        }
    }

    @Description("Checking all the request param validations")
    @Feature("SnailMail_HeimdallChanges")
    @Test(priority = 2, dataProvider = "pushRequestParamValidations")
    public void requestParamsValidations(String testcase, String username, String apvid, String useraddress,String agreementSignedDate,String product,String mobile) throws JSchException {
        log.info("Getting started with the Push APV data request validations");

        JSONArray userData = new JSONArray();
        userData.put(new JSONObject().put("userName", username).put("apvId",apvid).put("userAddress", useraddress).put("agreementSignDate", agreementSignedDate).
                put("product", product).put("mobile",mobile));
        if(testcase.equalsIgnoreCase("WithoutAPVId")){
            userData.put(new JSONObject().put("userName", username).put("userAddress", useraddress).put("agreementSignDate", agreementSignedDate).
                    put("product", product).put("mobile",mobile));
        }

        //creating userdata for request payload
        JSONObject requestPayload = new JSONObject();
        requestPayload.put("requestId", requestId);
        requestPayload.put("source", source1);
        requestPayload.put("data", userData);
        request = requestPayload.toString();
        headerDetails.put("X-API-Key", lp_auth_key);
        headerDetails.put("Tenanat", "1");
        headerDetails.put("Content-Type", "application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        System.out.println(request);
        SnailMailGenericResponsePojo pushResponseDetails = pushRequest.post(queryParamDetails, headerDetails, request);
        switch (testcase) {
            case "WithAPVId":
            case "WithoutAPVId":
                Assert.assertEquals(pushResponseDetails.getStatus(), "SUCCESS", "incorrect response");
                Assert.assertNull(pushResponseDetails.getData(), "Failed to push the data");
                Assert.assertEquals(genericutil.readFileFromSFTP(source1, formattedDate), userData.length(), "All data hasn't pushed to sftp");
                log.info("User data pushed to remote server successfully ");
                break;
            case "WithEmptyUserName":
            case "WithoutUserName":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect status");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_REQUEST_DATA", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "UserName provided is either empty or null", "Incorrect error message");
                break;
            case "WithEmptyUserAddress":
            case "WithoutUserAddress":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect status");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_ADDRESS", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Address provided is either empty or null", "Incorrect error message");
                break;
            case "WithoutAgreementSignedDate":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect status");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_DATE", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Date provided is null", "Incorrect error message");
                break;
            case "WithInvalidProduct":
            case "WithEmptyProduct":
            case "WithoutProductParam":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect status");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_PRODUCT", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Product provided is either empty, null or invalid", "Incorrect error message");
                break;
            case "WithInvalidMobile":
            case "WithEmptyMobile":
            case "WithoutMobileParam":
                Assert.assertEquals(pushResponseDetails.getStatus(), "FAILED", "incorrect status");
                Assert.assertEquals(pushResponseDetails.getErrorCode(), "INVALID_REQUEST_DATA", "incorrect error code");
                Assert.assertEquals(pushResponseDetails.getErrorMsg(), "Mobile provided is either empty or invalid", "Incorrect error message");
                break;
        }
    }
}