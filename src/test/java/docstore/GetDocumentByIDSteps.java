package docstore;

import api.docStore.GetDocumentById;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import pojos.docStore.GetDocumentByIdPojo;

import java.util.HashMap;
import java.util.Map;

public class GetDocumentByIDSteps {



    public GetDocumentByIDSteps() throws Exception {

    }

    GetDocumentById getDocumentByIdAPI = new GetDocumentById();

    @BeforeClass
    public void getDocumentByIdStepsPrerequisites() {

    }




    @Step
    public void getDocumentForValidDocId(Integer docId) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        queryParamDetails.put("docId", docId);


        GetDocumentByIdPojo getDocumentByIdPojo = getDocumentByIdAPI.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getDocumentByIdPojo.responseStatus, "SUCCESS");
        Assert.assertNotNull(getDocumentByIdPojo.document);
    }

    @Step
    public void getDocumentsForInvalidUUID(String uuid) throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        headerDetails.put("authToken", DocStoreData.AUTH_TOKEN);

        queryParamDetails.put("docId", DocStoreData.INVALID_DOCUMENT_ID);


        GetDocumentByIdPojo getDocumentByIdPojo = getDocumentByIdAPI.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getDocumentByIdPojo.responseStatus, "SUCCESS");
        Assert.assertNull(getDocumentByIdPojo.document);

    }
}
