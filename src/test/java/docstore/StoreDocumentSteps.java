package docstore;

import api.docStore.StoreDocument;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.StoreDocumentPojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class StoreDocumentSteps {


    public Integer document_id = null;
    
    public StoreDocumentSteps() throws Exception {

    }

    StoreDocument storeDocumentAPI = new StoreDocument();

    @BeforeClass
    public void storeDocumentStepsPrerequisites() {

    }



    @Step
    public void storeDocumentForValidUUID(String uuid, String docTypeID, String docExtenstion) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        String storeDocumentRequest = new StringTemplate(DocStoreConstants.STORE_DOCUMENT_REQUEST)
                .replace("uuid", uuid)
                .replace("documentType", docTypeID)
                .replace("extension", docExtenstion)
                .toString();

        JSONObject storeDocumentRequestJson = new JSONObject(storeDocumentRequest);

        StoreDocumentPojo storeDocumentPojo = storeDocumentAPI.post(queryParamDetails, headerDetails, storeDocumentRequestJson.toString());
        this.document_id = storeDocumentPojo.documentStoreId;
        Assert.assertEquals(storeDocumentPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(storeDocumentPojo.documentStoreId);

    }

    @Step
    public void storeDocumentForInvalidUUID() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        String storeDocumentRequest = new StringTemplate(DocStoreConstants.STORE_DOCUMENT_REQUEST)
                .replace("uuid", DocStoreData.INVALID_UUID)
                .replace("documentType", DocStoreData.AADHAAR_FRONT_DOC)
                .replace("extension", DocStoreData.MIMETYPEJPG)
                .toString();

        JSONObject storeDocumentRequestJson = new JSONObject(storeDocumentRequest);

        StoreDocumentPojo storeDocumentPojo = storeDocumentAPI.post(queryParamDetails, headerDetails, storeDocumentRequestJson.toString());
        Assert.assertEquals(storeDocumentPojo.responseStatus, "FAILED");

    }



}
