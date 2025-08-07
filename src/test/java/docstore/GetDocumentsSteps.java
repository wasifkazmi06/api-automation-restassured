package docstore;

import api.docStore.GetDocuments;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.GetDocumentsPojo;


import java.util.HashMap;
import java.util.Map;

public class GetDocumentsSteps {

    public GetDocumentsSteps() throws Exception {

    }

    GetDocuments getDocumentsAPI = new GetDocuments();

    @BeforeClass
    public void getDocumentsStepsPrerequisites() {

    }



    @Step
    public void getDocumentsForValidUUIDGivenDocType(String uuid, String docTypeID) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        queryParamDetails.put("uuid", uuid);
        queryParamDetails.put("documentType", docTypeID);


        GetDocumentsPojo getDocumentsPojo = getDocumentsAPI.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getDocumentsPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(getDocumentsPojo.documents);
    }


    @Step
    public void getDocumentsForValidUUID(String uuid) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        queryParamDetails.put("uuid", DocStoreData.UUID);


        GetDocumentsPojo getDocumentsPojo = getDocumentsAPI.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getDocumentsPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(getDocumentsPojo.documents);
    }

    @Step
    public void getDocumentsForInvalidUUID(String uuid) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        queryParamDetails.put("uuid", DocStoreData.INVALID_UUID);


        GetDocumentsPojo getDocumentsPojo = getDocumentsAPI.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getDocumentsPojo.responseStatus, "FAILED");

    }
}
