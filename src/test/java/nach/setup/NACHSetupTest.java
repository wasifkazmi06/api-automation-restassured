package nach.setup;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import mbe.authentication.APPLoginUser;
import mbe.authentication.DeviceConfigTest;
import nach.NACHData;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.DeleteUser;
import util.ReturnRandomTxnId;

public class NACHSetupTest {
    public static String appVersion;
    public static String authorization;

    @BeforeSuite
    public void nachSetupPrerequisites() throws Exception {

    appVersion = DeviceConfigTest.fetchUpdatedAppVersion();

    }

    @BeforeTest
    public void resetUser() throws Exception {

        DeleteUser.deleteUserMethod(NACHData.digioNBUser, System.getProperty("env"), "col,ds");
        DeleteUser.deleteUserMethod(NACHData.razorpayNBUser, System.getProperty("env"), "col,ds");
        DeleteUser.deleteUserMethod(NACHData.digioNBMADUser, System.getProperty("env"), "col,ds");
        DeleteUser.deleteUserMethod(NACHData.razorpayNBMADUser, System.getProperty("env"), "col,ds");

    }

    @Description("Setup NACH for a valid user using Digio as vendor for full amount.")
    @Feature("SetupDigio")
    @Test(priority = 1,groups = { "NACHSetup", "sanity"})
    public void digioNetBankingNACHSetup() throws Exception {

        authorization = APPLoginUser.LoginIntoApp(NACHData.digioNBUser);

        GetLandingScreenStep.getLandingScreenValidUser();
        UpdateRepaymentAmountTypeStep.updateRepaymentAmountTypeValidUser();
        GetIRSBankFormStep.getIRSBankFormStepValidUser();
        GetSupportedBankListStep.getSupportedBankListValidUser();
        InitiateIRSStep.initiateIRSStepValidUserDigio();
        AuthenticateSetupNBStep.authenticateSetupNBValidUserDigio();
        NACHData.acceptNACHSetupDigio(String.valueOf(InitiateIRSStep.setupID));
        GetIRSStatusStep.getIRSStatusStepValidUserDigio();

    }

    @Description("Setup NACH for a valid user using Razorpay as vendor for full amount.")
    @Feature("SetupRazorpay")
    @Test(priority = 2,groups = { "NACHSetup", "sanity"})
    public void razorpayNetBankingNACHSetup() throws Exception {

        authorization = APPLoginUser.LoginIntoApp(NACHData.razorpayNBUser);

        String razorPayTokenId = "token_Ky1PV"+ ReturnRandomTxnId.returnTxnIDMethod("RP_PID");

        GetLandingScreenStep.getLandingScreenValidUser();
        UpdateRepaymentAmountTypeStep.updateRepaymentAmountTypeValidUser();
        GetIRSBankFormStep.getIRSBankFormStepValidUser();
        GetSupportedBankListStep.getSupportedBankListValidUser();
        InitiateIRSStep.initiateIRSStepValidUserRazorpay();
        AuthenticateSetupNBStep.authenticateSetupNBValidUserRazorpay();
        NACHData.acceptNACHSetupRazorpay(String.valueOf(InitiateIRSStep.setupID), AuthenticateSetupNBStep.razorPayPaymentId, razorPayTokenId);
        GetIRSStatusStep.getIRSStatusStepValidUserRazorpay();

    }
    @Description("Setup NACH for a valid user using Digio as vendor for MAD amount.")
    @Feature("SetupDigio")
    @Test(priority = 3,groups = { "NACHSetup", "sanity"})
    public void digioNetBankingMADNACHSetup() throws Exception {

        authorization = APPLoginUser.LoginIntoApp(NACHData.digioNBMADUser);

        GetLandingScreenStep.getLandingScreenValidUser();
        UpdateRepaymentAmountTypeStep.updateRepaymentAmountTypeValidMADUser();
        GetIRSBankFormStep.getIRSBankFormStepValidUser();
        GetSupportedBankListStep.getSupportedBankListValidUser();
        InitiateIRSStep.initiateIRSStepValidMADUserDigio();
        AuthenticateSetupNBStep.authenticateSetupNBValidUserDigio();
        NACHData.acceptNACHSetupDigio(String.valueOf(InitiateIRSStep.setupID));
        GetIRSStatusStep.getIRSStatusStepValidUserDigio();

    }

    @Description("Setup NACH for a valid user using Razorpay as vendor for MAD amount.")
    @Feature("SetupRazorpay")
    @Test(priority = 4,groups = { "NACHSetup", "sanity"})
    public void razorpayNetBankingMADNACHSetup() throws Exception {

        authorization = APPLoginUser.LoginIntoApp(NACHData.razorpayNBMADUser);

        String razorPayTokenId = "token_Ky1PV"+ ReturnRandomTxnId.returnTxnIDMethod("RP_PID");

        GetLandingScreenStep.getLandingScreenValidUser();
        UpdateRepaymentAmountTypeStep.updateRepaymentAmountTypeValidMADUser();
        GetIRSBankFormStep.getIRSBankFormStepValidUser();
        GetSupportedBankListStep.getSupportedBankListValidUser();
        InitiateIRSStep.initiateIRSStepValidMADUserRazorpay();
        AuthenticateSetupNBStep.authenticateSetupNBValidUserRazorpay();
        NACHData.acceptNACHSetupRazorpay(String.valueOf(InitiateIRSStep.setupID), AuthenticateSetupNBStep.razorPayPaymentId, razorPayTokenId);
        GetIRSStatusStep.getIRSStatusStepValidUserRazorpay();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in GetLandingScreen API")
    @Feature("NegativeCase")
    @Test(priority = 5,groups = { "NACHSetup"})
    public void incorrectAuthGetLandingScreen() throws Exception {

        GetLandingScreenStep.getLandingScreenIncorrectAuth();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in UpdateRepaymentAmountType API")
    @Feature("NegativeCase")
    @Test(priority = 6,groups = { "NACHSetup"})
    public void incorrectAuthUpdateRepaymentAmountType() throws Exception {

        UpdateRepaymentAmountTypeStep.updateRepaymentAmountTypeIncorrectAuth();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in GetIRSBankForm API")
    @Feature("NegativeCase")
    @Test(priority = 7,groups = { "NACHSetup"})
    public void incorrectAuthGetIRSBankForm() throws Exception {

        GetIRSBankFormStep.getIRSBankFormStepIncorrectAuth();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 8,groups = { "NACHSetup"})
    public void incorrectAuthGetSupportedBankList() throws Exception {

        GetSupportedBankListStep.getSupportedBankLisIncorrectAuth();

    }


    @Description("An error should be thrown if an incorrect authorisation is passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 9,groups = { "NACHSetup"})
    public void incorrectAuthInitiateIRS() throws Exception {

        InitiateIRSStep.initiateIRSStepIncorrectAuth();

    }

    @Description("An error should be thrown if bank account number is not passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 10,groups = { "NACHSetup"})
    public void noAccountNumberInitiateIRS() throws Exception {

        authorization = APPLoginUser.LoginIntoApp(NACHData.nachGenricUser);

        InitiateIRSStep.initiateIRSStepNoAccountNumber();

    }

    @Description("An error should be thrown if an invalid bank account number is passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 11,groups = { "NACHSetup"})
    public void invalidAccountNumberInitiateIRS() throws Exception {

        InitiateIRSStep.initiateIRSStepInvalidAccountNumber();

    }

    @Description("An error should be thrown if bank name is not passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 12,groups = { "NACHSetup"})
    public void noBankNameInitiateIRS() throws Exception {

        InitiateIRSStep.initiateIRSStepNoBankame();

    }

    @Description("An error should be thrown if beneficiary name is not passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 13,groups = { "NACHSetup"})
    public void noNameInitiateIRS() throws Exception {

        InitiateIRSStep.initiateIRSStepNoName();

    }

    @Description("An error should be thrown if IFSC code is not passed in InitiateIRS API")
    @Feature("NegativeCase")
    @Test(priority = 14,groups = { "NACHSetup"})
    public void noIFSCInitiateIRS() throws Exception {

        InitiateIRSStep.initiateIRSStepNoIFSC();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in AuthenticateSetup API")
    @Feature("NegativeCase")
    @Test(priority = 15,groups = { "NACHSetup"})
    public void incorrectAuthAuthenticateSetup() throws Exception {

        AuthenticateSetupNBStep.authenticateSetupIncorrectAuth();

    }

    @Description("An error should be thrown if an incorrect authorisation is passed in GetIRSStatus API")
    @Feature("NegativeCase")
    @Test(priority = 16,groups = { "NACHSetup"})
    public void incorrectAuthGetIRSStatus() throws Exception {

        GetIRSStatusStep.getIRSStatusStepIncorrectAuth();

    }

}
