package docstore;



import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.DeleteUser;
import util.ReturnRandomTxnId;

import java.util.Properties;

@Slf4j
public class DocStoreTest {

    public DocStoreTest() throws Exception {
    }


    StoreDocumentSteps storeDocumentSteps = new StoreDocumentSteps();
    GetDocumentsSteps getDocumentsSteps = new GetDocumentsSteps();
    GetDocumentByIDSteps getDocumentByIDSteps = new GetDocumentByIDSteps();

    StoreSensitiveInfoSteps storeSensitiveInfoSteps = new StoreSensitiveInfoSteps();
    UpdateSensitiveInfoSteps updateSensitiveInfoSteps = new UpdateSensitiveInfoSteps();
    RetrieveSensitiveInfoSteps retrieveSensitiveInfoSteps = new RetrieveSensitiveInfoSteps();
    DeleteSensitiveInfoSteps deleteSensitiveInfoSteps = new DeleteSensitiveInfoSteps();


    @BeforeClass
    public void docStoreFlowTestPrerequisites() {
    }

    @BeforeTest
    public static void purgeUserDSData() throws Exception {
        Properties properties = System.getProperties();
        String env = properties.getProperty("env");
        DeleteUser.deleteUserMethod(DocStoreData.USER_MOBILE_NUMBER, env, "ds");
    }

    @Description("To verify Aadhar Front document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 1, groups = {"docStore", "sanity", "regression"})
    public void verifyAadharFrontDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.AADHAAR_FRONT_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the get Documents API returns all document of given DocType for an valid user uuid")
    @Feature("StoreDocument")
    @Test(priority = 2, groups = {"docStore", "sanity", "regression"})
    public void verifyGetDocumentsOfGivenDocTypeIDforValidUUID() throws Exception {

        getDocumentsSteps.getDocumentsForValidUUIDGivenDocType(DocStoreData.UUID, DocStoreData.AADHAAR_FRONT_DOC);

    }

    @Description("To verify the get Document By ID API return document for an valid user docid")
    @Feature("StoreDocument")
    @Test(priority = 3, groups = {"docStore", "sanity", "regression"})
    public void verifyGetDocumentByIdforValidDocID() throws Exception {

        getDocumentByIDSteps.getDocumentForValidDocId(storeDocumentSteps.document_id);

    }

    @Description("To verify Aadhar Back document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 4, groups = {"docStore", "sanity", "regression"})
    public void verifyAadharBackDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.AADHAAR_BACK_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify IPV SELFIE document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 5, groups = {"docStore", "sanity", "regression"})
    public void verifyIPV_SELFIE_DocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.IPV_SELFIE_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the  OSV SIGNATURE document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 6, groups = {"docStore", "sanity", "regression"})
    public void verifyOSV_SIGNATURE_DocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.OSV_SIGNATURE_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the  Additional Front document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 7, groups = {"docStore", "sanity", "regression"})
    public void verifyAddDocFrontDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.ADDITIONAL_DOC_FRONT_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the  Additional Back document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 8, groups = {"docStore", "sanity", "regression"})
    public void verifyAddDocBackDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.ADDITIONAL_DOC_BACK_DOC, DocStoreData.MIMETYPEJPG);

    }


    @Description("To verify the  DownloadedNach document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 9, groups = {"docStore", "sanity", "regression"})
    public void verifyDownloadedNachDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.DOWNLOADED_NACH_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the  LoanPDF document are stored of a valid user using StoreDocument API")
    @Feature("StoreDocument")
    @Test(priority = 10, groups = {"docStore", "sanity", "regression"})
    public void verifyLoanPDFDocumentStore() throws Exception {

        storeDocumentSteps.storeDocumentForValidUUID(DocStoreData.UUID,DocStoreData.LOAN_TNC_PDF_DOC, DocStoreData.MIMETYPEJPG);

    }

    @Description("To verify the store Document Failed for an invalid user uuid")
    @Feature("StoreDocument")
    @Test(priority = 11, groups = {"docStore", "sanity", "regression"})
    public void verifyStoreDocumentFailsForInvalidUUID() throws Exception {

        storeDocumentSteps.storeDocumentForInvalidUUID();

    }

    @Description("To verify the get Documents API returns all document for an valid user uuid")
    @Feature("StoreDocument")
    @Test(priority = 12, groups = {"docStore", "sanity", "regression"})
    public void verifyGetDocumentsforValidUUID() throws Exception {

        getDocumentsSteps.getDocumentsForValidUUID(DocStoreData.UUID);

    }

    @Description("To verify the get Documents API fails for an invalid user uuid")
    @Feature("StoreDocument")
    @Test(priority = 13, groups = {"docStore", "sanity", "regression"})
    public void verifyGetDocumentsforInvalidUUID() throws Exception {

        getDocumentsSteps.getDocumentsForValidUUID(DocStoreData.UUID);

    }

    @Description("To verify the get Document By ID API return document null for an invalid user docid")
    @Feature("StoreDocument")
    @Test(priority = 14, groups = {"docStore", "sanity", "regression"})
    public void verifyGetDocumentByIdforInvalidDocID() throws Exception {

        getDocumentByIDSteps.getDocumentsForInvalidUUID(DocStoreData.INVALID_DOCUMENT_ID);

    }




    @Description("To verify Pan sensitive info are stored of a valid user using StoreSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 15, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoStoredForValidUuid() throws Exception {

        storeSensitiveInfoSteps.storeSensitiveInfoForValidUUID(SensitiveInfoData.UUID,SensitiveInfoData.PAN, SensitiveInfoData.PAN_NUMBER);

    }

    @Description("To verify the Sensitive Info Failed for an invalid user uuid")
    @Feature("SensitiveInfo")
    @Test(priority = 16, groups = {"docStore", "sanity", "regression"})
    public void verifySensitiveInfoFailsForInvalidUUID() throws Exception {

        storeSensitiveInfoSteps.storeDocumentForInvalidUUID();

    }

    @Description("To verify Pan sensitive info is retrieved of a valid user using RetrieveSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 17, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoRetrievedForValidUuid() throws Exception {

        retrieveSensitiveInfoSteps.retrieveSensitiveInfoForValidUUID(SensitiveInfoData.UUID, SensitiveInfoData.PAN, storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify Pan sensitive info is failed to retrieved for invalid ref using RetrieveSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 18, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoFailsRetrievedForInValidUuid() throws Exception {

        retrieveSensitiveInfoSteps.retrieveSensitiveInfoForInvalidUUID();

    }

    @Description("To verify Pan sensitive info is updated for a valid user using UpdateSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 19, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoUpdatedForValidUuid() throws Exception {

        updateSensitiveInfoSteps.updateSensitiveInfoForValidUUID(storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify Pan sensitive info is failed to update for a invalid ref key using UpdateSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 20, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoUpdateFailsForValidInvalidRefkey() throws Exception {

        updateSensitiveInfoSteps.updateSensitiveInfoForInvalidRefKey();

    }

    @Description("To verify Pan sensitive info is deleted of a valid user using DeleteSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 21, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoDeletedForValidUuid() throws Exception {

        deleteSensitiveInfoSteps.deleteSensitiveInfoForValidUUID(SensitiveInfoData.PAN, storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify Pan sensitive info delete fails for an invalid ref key using DeleteSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 22, groups = {"docStore", "sanity", "regression"})
    public void verifyPanSensitiveInfoDeleteFailsForInValidRefKey() throws Exception {

        deleteSensitiveInfoSteps.deleteSensitiveInfoForInvalidRefKey();

    }


    @Description("To verify Aadhar sensitive info are stored of a valid user using StoreSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 23, groups = {"docStore", "sanity", "regression"})
    public void verifyAadharSensitiveInfoStoredForValidUuid() throws Exception {

        storeSensitiveInfoSteps.storeSensitiveInfoForValidUUID(SensitiveInfoData.UUID,SensitiveInfoData.AADHAAR, ReturnRandomTxnId.returnTxnIDMethod("AADHAAR"));

    }

    @Description("To verify Aadhar sensitive info is retrieved of a valid user using RetrieveSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 24, groups = {"docStore", "sanity", "regression"})
    public void verifyAadharSensitiveInfoRetrievedForValidUuid() throws Exception {

        retrieveSensitiveInfoSteps.retrieveSensitiveInfoForValidUUID(SensitiveInfoData.UUID, SensitiveInfoData.AADHAAR, storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify Aadhar sensitive info is updated for a valid user using UpdateSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 25, groups = {"docStore", "sanity", "regression"})
    public void verifyAadharSensitiveInfoUpdatedForValidUuid() throws Exception {

        updateSensitiveInfoSteps.updateSensitiveInfoForValidUUID(storeSensitiveInfoSteps.refkey);

    }


    @Description("To verify Bank account details sensitive info are stored of a valid user using StoreSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 26, groups = {"docStore", "sanity", "regression"})
    public void verifyBankAccountSensitiveInfoStoredForValidUuid() throws Exception {

        storeSensitiveInfoSteps.storeSensitiveInfoForValidUUID(SensitiveInfoData.UUID,SensitiveInfoData.BANKACCOUNTTYPE, SensitiveInfoData.BANKACCOUNTNUMBER);

    }

    @Description("To verify BankAccount sensitive info is retrieved of a valid user using RetrieveSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 27, groups = {"docStore", "sanity", "regression"})
    public void verifyBankAccountSensitiveInfoRetrievedForValidUuid() throws Exception {

        retrieveSensitiveInfoSteps.retrieveSensitiveInfoForValidUUID(SensitiveInfoData.UUID, SensitiveInfoData.BANKACCOUNTTYPE, storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify BankAccount sensitive info is updated for a valid user using UpdateSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 28, groups = {"docStore", "sanity", "regression"})
    public void verifyBankAccountSensitiveInfoUpdatedForValidUuid() throws Exception {

        updateSensitiveInfoSteps.updateSensitiveInfoForValidUUID(storeSensitiveInfoSteps.refkey);

    }

    @Description("To verify BankAccount sensitive info is deleted of a valid user using DeleteSensitiveInfo API")
    @Feature("SensitiveInfo")
    @Test(priority = 29, groups = {"docStore", "sanity", "regression"})
    public void verifyBankAccountSensitiveInfoDeletedForValidUuid() throws Exception {

        deleteSensitiveInfoSteps.deleteSensitiveInfoForValidUUID(SensitiveInfoData.BANKACCOUNTTYPE, storeSensitiveInfoSteps.refkey);

    }


}
