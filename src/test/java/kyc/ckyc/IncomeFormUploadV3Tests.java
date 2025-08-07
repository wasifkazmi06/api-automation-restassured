package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.enums.DocumentTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import pojos.kyc.apis.UploadDocumentPojo;
import util.DeleteUser;

import java.io.FileNotFoundException;
import java.util.Properties;
@Slf4j
public class IncomeFormUploadV3Tests extends CommonMethodsImpl {
    public static String kycCaseId;

    public IncomeFormUploadV3Tests() throws Exception {
    }

    @Feature("v3/uploadDocuments for PERSONAL_DETAILS_INCOME_FORM")
    @Description("To verify the PERSONAL_DETAILS_INCOME_FORM upload using v3/uploadDocuments API with multiple scenarios")
    @Test(priority = 1, dataProvider = "incomeFormTestData", dataProviderClass = BaseTestData.class)
    public void uploadDocumentsV3IncomeDetails(String incomeRangeTestCase, String incomeRange) throws Exception {
        // purging the user
        purgeUser(USER_MOBILE);
        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(TEST_USER_UUID, VALID_PRODUCT);
        kycCaseId = initiateKYCV9pojo.kycCaseId;
        // constructing personal details form data request
        JSONObject reqObject = ApiRequests.getEmploymentDataRequest(EMPLOYMENT_TYPE, FATHER_NAME, incomeRange, GENDER);
        log.info("log reqObject :: " + reqObject);
        // Calling upload V3 API
        UploadDocumentPojo uploadPersonalDetailsDocpojo = uploadDoc(TEST_USER_UUID, kycCaseId,
                DocumentTypeEnum.PERSONAL_DETAILS_INCOME_FORM.toString(), reqObject.toString());
        switch (incomeRangeTestCase) {
            case "VALID_INCOME_RANGE":
                Assert.assertEquals(uploadPersonalDetailsDocpojo.uploadStatus, "SUCCESS", "check the data");
                break;
            case "EMPTY_INCOME_RANGE":
                Assert.assertEquals(uploadPersonalDetailsDocpojo.status, "400");
                Assert.assertEquals(uploadPersonalDetailsDocpojo.errorCode, "INVALID_INCOME_RANGE");
                break;
        }
    }

    @Feature("v3/uploadDocuments for PERSONAL_DETAILS_INCOME_FORM")
    @Description("To verify the PERSONAL_DETAILS_INCOME_FORM upload using v3/uploadDocuments API with invalid-employmentType details")
    @Test(priority = 2, dataProvider = "employmentTypeTestData", dataProviderClass = BaseTestData.class)
    public void uploadDocV3WithInvalidEmploymentType(String employmentTypeTestCase, String employmentType) throws Exception {
        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(TEST_USER_UUID, VALID_PRODUCT);
        kycCaseId = initiateKYCV9pojo.kycCaseId;
        // constructing personal details form data request
        JSONObject reqObject = ApiRequests.getEmploymentDataRequest(employmentType, FATHER_NAME, INCOME_RANGE, GENDER);
        log.info("log reqObject :: " + reqObject);
        // Calling upload V3 API
        UploadDocumentPojo uploadPersonalDetailsDocpojo = uploadDoc(TEST_USER_UUID, kycCaseId,
                DocumentTypeEnum.PERSONAL_DETAILS_INCOME_FORM.toString(), reqObject.toString());
       Assert.assertEquals(uploadPersonalDetailsDocpojo.status,"400","check the error code");
       Assert.assertEquals(uploadPersonalDetailsDocpojo.errorCode,"INVALID_EMPLOYMENT_TYPE","check the error code");

    }
}
