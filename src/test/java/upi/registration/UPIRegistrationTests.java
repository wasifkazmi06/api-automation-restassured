package upi.registration;

import dbUtils.UPI_MySQLSsh_DBAccessObject;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import upi.UPIData;
import java.sql.SQLException;
import static upi.registration.RegistrationStatusSteps.registrationStatusPojo;

public class UPIRegistrationTests {

    GenerateSMSSteps generateSMSSteps = new GenerateSMSSteps();
    NotifySMSSteps notifySMSSteps = new NotifySMSSteps();
    RegistrationStatusSteps registrationStatusSteps = new RegistrationStatusSteps();

    public UPIRegistrationTests() throws Exception {
    }

    @BeforeTest
    public void LPUPITransactionalFlowTestsPrerequisites() throws Exception {
    }

    @AfterClass
    public void finishUp() throws SQLException, ClassNotFoundException {
        UPI_MySQLSsh_DBAccessObject.closeMySqlDbConnection();
    }

    @Description("To verify registration status is REGISTERED_PENDING_ACTIVATION for a non-KYC user")
    @Feature("UPIRegistrationHappyCases")
    @Test(priority = 0, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyRegistrationIsPendingForNonKYCUser() throws Exception {

        //Step 1 - MBE calls get UPI status to check user's registration status. If already registered purge the user's UPI data.
        registrationStatusSteps.getUPIRegistrationStatus();
        try {
            if (!registrationStatusPojo.upiStatus.equals("NOT_REGISTERED")) {
                UPIData.deleteUserRegistration(UPIData.UPI_USER);
                registrationStatusSteps.verifyUPIRegistrationStatusForNewUser();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //Step 2 - User initiates UPI registration by pressing the activate button, the generate SMS API is called.
        generateSMSSteps.verifySendSMSForNewUser();
        //Step 3 - After SMS is generated and sent, the notify SMS API is called.
        notifySMSSteps.verifyNotifySMSForNewUser();
        //Step 4 - Verify user's registration status is REGISTERED_PENDING_ACTIVATION
        registrationStatusSteps.verifyUPIPendingRegistrationStatus();

    }

    @Description("To verify registration status is REGISTERED_PENDING_ACTIVATION for a non-MITC user")
    @Feature("UPIRegistrationHappyCases")
    @Test(priority = 1, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyRegistrationIsPendingForNonMITCUser() throws Exception {

        //Step 1 - Verify user's registration status is REGISTERED_PENDING_ACTIVATION
        registrationStatusSteps.verifyUPIPendingRegistrationStatus();

    }

    @Description("To verify UPI registration happy flow for a eligible user")
    @Feature("UPIRegistrationHappyCases")
    @Test(priority = 2, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyUserRegistrationFlowHappyFlow() throws Exception {

        //Step 1 - Mark user registered in DB
        try{
            if(registrationStatusPojo.upiStatus.equals("REGISTERED_PENDING_ACTIVATION") || registrationStatusPojo.upiStatus.equals("REGISTRATION_IN_PROGRESS"))
            {
                UPIData.updateUserAsRegistered(UPIData.UPI_USER);
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        //Step 2 - Verify user's upi status is REGISTERED
        registrationStatusSteps.verifyUPIRegistrationStatusForRegisteredUser();

    }

    @Description("To verify mobile is mandatory for the get registration API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 3, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnMobileForGetRegistrationStatus() {

        registrationStatusSteps.verifyMandatoryValidationOnMobileForGetRegistrationStatus();
    }

    @Description("To verify mobile format validation for the get registration API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 4, groups = {"UPIRegistration"})
    public void verifyFormatValidationOnMobileForGetRegistrationStatus() {

        registrationStatusSteps.verifyFormatValidationOnMobileForGetRegistrationStatus();
    }

    @Description("To verify source is mandatory for the get registration API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 5, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnSourceForGetRegistrationStatus() {

        registrationStatusSteps.verifyMandatoryValidationOnSourceForGetRegistrationStatus();
    }

    @Description("To verify incorrect source validation for the get registration API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 6, groups = {"UPIRegistration"})
    public void verifyKeyValueValidationOnSourceForGetRegistrationStatus() {

        registrationStatusSteps.verifyKeyValueValidationOnSourceForGetRegistrationStatus();
    }

    @Description("To verify device ID is mandatory for the generate SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 7, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnDeviceIDForGenerateSMS() {

        generateSMSSteps.verifyMandatoryValidationOnDeviceIDForGenerateSMS();
    }

    @Description("To verify mobile is mandatory for the generate SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 8, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnMobileForGeneratedSMS() {

        generateSMSSteps.verifyMandatoryValidationOnMobileForGenerateSMS();
    }

    @Description("To verify sim ID is mandatory for the generate SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 9, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnSimIDForGeneratedSMS() {

        generateSMSSteps.verifyMandatoryValidationOnSimIDForGenerateSMS();
    }

    @Description("To verify device ID is mandatory for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 10, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnDeviceIDForNotifySMS() {

        notifySMSSteps.verifyMandatoryValidationOnDeviceIDForNotifySMS();
    }

    @Description("To verify mobile is mandatory for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 11, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnMobileForNotifySMS() {

        notifySMSSteps.verifyMandatoryValidationOnMobileForNotifySMS();
    }

    @Description("To verify sim ID is mandatory for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 12, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnSimIDForNotifySMS() {

        notifySMSSteps.verifyMandatoryValidationOnSimIDForNotifySMS();
    }

    @Description("To verify status is mandatory for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 13, groups = {"UPIRegistration"})
    public void verifyMandatoryValidationOnStatusForNotifySMS() {

        notifySMSSteps.verifyMandatoryValidationOnStatusForNotifySMS();
    }

    @Description("To verify incorrect status value validation for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 14, groups = {"UPIRegistration"})
    public void verifyKeyValueValidationOnStatusForNotifySMS() {

        notifySMSSteps.verifyKeyValueValidationOnStatusForNotifySMS();
    }

    @Description("To verify incorrect registration ID validation for the notify SMS API")
    @Feature("UPIRegistrationNegativeCases")
    @Test(priority = 15, groups = {"UPIRegistration"})
    public void verifyIncorrectRegistrationIDValidationForNotifySMS() {

        notifySMSSteps.verifyIncorrectRegistrationIDValidationForNotifySMS();
    }
}
