package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import util.DeleteUser;

import java.io.FileNotFoundException;
import java.util.Properties;

@Slf4j

public class InitiateKYCTests extends CommonMethodsImpl {

    public InitiateKYCTests() throws Exception {
    }
    public String kycCaseIdFromDb="";
    public String kycStatusFromDb="";
    DBValidations dbData = new DBValidations();

    /*
            Test cases for v9/initiateKyc API
            * Valid UUID
            * Invalid UUID
            * Empty UUID
            * Valid Product type
            * Invalid product type
            * Empty product type
            * No uuid & product fields
             */
    @Feature("verifyV9/InitiateKycApi")
    @Description("To verify the v9/initiateKyc API with valid & invalid data of uuid & product")
    @Test(priority = 1, dataProvider = "uuid&product_testdata", dataProviderClass = BaseTestData.class)
    public void initiateKyc(String uuid, String product) throws Exception {


        //Purge any existing user
        purgeUser(USER_MOBILE);

        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(uuid, product);
        // Validations based on UUID & product fields
        if (uuid == null) {
            Assert.assertEquals(initiateKYCV9pojo.message, "UUID cannot be empty or null", "check the error");
            return;

        }
        switch (uuid) {
            case BaseTestData.VALID_USER_UUID:
                if (product == BaseTestData.VALID_PRODUCT) {
                    Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");
                    log.info("Fetch the KycCaseId ::" + initiateKYCV9pojo.kycCaseId);
                    // Verifying the entry created in kyc_case table
                    kycCaseIdFromDb = dbData.getKycCaseId(uuid,product);
                    log.info("KycCaseId fetched from DB :: "+ kycCaseIdFromDb);
                    Assert.assertEquals(initiateKYCV9pojo.kycCaseId,kycCaseIdFromDb);
                    // Verifying the kyc status from kyc_case table
                    kycStatusFromDb = dbData.getKycStatus(uuid,product);
                    log.info("KycStatus fetched from DB ::"+ kycStatusFromDb);
                    Assert.assertEquals(initiateKYCV9pojo.kycStatus,kycStatusFromDb);

                } else if (product == BaseTestData.INVALID_PRODUCT) {
                    Assert.assertEquals(initiateKYCV9pojo.errorCode, "INVALID_PRODUCT_TYPE", "check the error");
                    log.info("error message is ::" + initiateKYCV9pojo.message);
                } else {
                    Assert.assertEquals(initiateKYCV9pojo.errorCode, "MISSING_PRODUCT", "check the error");
                    log.info("error message is ::" + initiateKYCV9pojo.message);
                }
                break;
            case BaseTestData.INVALID_USER_UUID:
                Assert.assertEquals(initiateKYCV9pojo.errorCode, "USER_NOT_FOUND", "check the error code if assersion fails");
                log.info("errorCode is ::" + initiateKYCV9pojo.errorCode);
                break;
            case BaseTestData.EMPTY_USER_UUID:
                Assert.assertEquals(initiateKYCV9pojo.message, "UUID cannot be empty or null", "check the error");
                log.info("error message is ::" + initiateKYCV9pojo.message);
                break;

        }
    }

    /*
       KYC versioning change test cases for v9/initiateKyc API
       * Valid source for BNPL
       * Valid source for XPRESS
       * Invalid source
       * Empty source
       */
    @Feature("verifyV9/InitiateKycApi")
    @Description("To verify the v9/initiateKyc API with valid & invalid data of uuid & product")
    @Test(priority = 2, dataProvider = "source_testdata", dataProviderClass = BaseTestData.class)
    public void initiateKycWithSource(String sourceCase,String source) throws Exception {

        //Purge any existing user
        purgeUser(USER_MOBILE);
        //calling InitiateAPI
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV9(VALID_USER_UUID, VALID_PRODUCT, source);
        // Validations based on UUID & product fields
        switch (sourceCase) {
            case "VALID_SOURCE_BNPL":
                Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");
                Assert.assertEquals(initiateKYCV9pojo.kycStatus, "DOCUMENTS_PENDING", "check the error message");
                // Verifying the entry created in kyc_case table
                kycCaseIdFromDb = dbData.getKycCaseId(VALID_USER_UUID,VALID_PRODUCT);
                log.info("KycCaseId fetched from DB :: "+ kycCaseIdFromDb);
                Assert.assertEquals(initiateKYCV9pojo.kycCaseId,kycCaseIdFromDb);
                // Verifying the kyc status from kyc_case table
                kycStatusFromDb = dbData.getKycStatus(VALID_USER_UUID,VALID_PRODUCT);
                log.info("KycStatus fetched from DB ::"+ kycStatusFromDb);
                Assert.assertEquals(initiateKYCV9pojo.kycStatus,kycStatusFromDb);
                break;
            case "INVALID_SOURCE":
                Assert.assertEquals(initiateKYCV9pojo.status, "400", "check the error code");
                Assert.assertEquals(initiateKYCV9pojo.errorCode, "INVALID_SOURCE", "check the error");
                break;
            case "EMPTY_SOURCE":
            case "VALID_SOURCE_XPRESS":
            case "NO_SOURCE_FEILD":
                Assert.assertNotNull(initiateKYCV9pojo.kycCaseId, "check the error message");
                break;

        }

    }


}




