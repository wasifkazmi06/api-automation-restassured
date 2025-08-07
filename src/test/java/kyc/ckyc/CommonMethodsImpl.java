package kyc.ckyc;

import api.kyc.apis.DeactivateKycCase;
import api.kyc.apis.InitiateKYCV9;
import api.kyc.apis.UploadDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyc.BaseTestData;
import kyc.KycConstants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.testng.Assert;
import pojos.kyc.apis.*;
import util.StringTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jyothi
 */

@Slf4j

public class CommonMethodsImpl extends BaseTestData {
    List<DocumentsPendingPojo> docsPending = null;
    UploadDocumentPojo uploadDoc = null;

    public CommonMethodsImpl() throws Exception {
        super();
    }


    public String getNextDocToUpload() throws Exception {
        //InitiateKYCV9
        InitiateKYCV9 initiateKYCV7 = new InitiateKYCV9();

        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");
        String jsonRequestBodyInitiate = new StringTemplate(KycConstants.INITIATEV9_REQUEST).replace("userid", BaseTestData.TEST_USER_UUID).toString();
        InitiateKYCV9Pojo initiateKYCV9pojo = initiateKYCV7.post(queryParamDetailsInitiate, headerDetailsInitiate, jsonRequestBodyInitiate);
        Assert.assertNotNull(initiateKYCV9pojo.kycCaseId);
        docsPending = initiateKYCV9pojo.documentsPending;
        log.info("print: " + docsPending.get(0).docTypeId);
        return docsPending.get(0).getDocTypeId();
    }

    // InitiateKYC V9 API
    public InitiateKYCV9Pojo initiateKYCV9(String uuid) throws Exception {
        InitiateKYCV9 initiateKYCV9 = new InitiateKYCV9();
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");
        String jsonRequestBodyInitiate = new StringTemplate(KycConstants.INITIATEV9_REQUEST).replace("userid", uuid).toString();
        return initiateKYCV9.post(queryParamDetailsInitiate, headerDetailsInitiate, jsonRequestBodyInitiate);

    }

    // UploadDcoument V3 API - This method will upload different doc's based on input values
    public UploadDocumentPojo uploadDoc(String uuid, String kycCaseId, String docTypeId, String dataJson) throws Exception {
        UploadDocument uploadDocument = new UploadDocument();
        Map<String, Object> queryParamDetailsUpload = new HashMap<>();
        HashMap<String, Object> headerDetailsUpload = new HashMap<>();
        headerDetailsUpload.put("metadata", new JSONObject());
        headerDetailsUpload.put("Content-Type", "multipart/form-data");
        headerDetailsUpload.put("Accept", "*/*");
        //header('Content-type:application/json;charset=utf-8');
        log.info("headers print " + headerDetailsUpload);


        ObjectMapper mapper = new ObjectMapper();
        // upload request pojo
        UploadDocRequest uploadReq = new UploadDocRequest();
        uploadReq.setUuid(uuid);
        uploadReq.setKycCaseId(kycCaseId);
        uploadReq.setDocTypeId(docTypeId);
        uploadReq.setData(dataJson);

        // write to file for multildata request upload
        switch (docTypeId) {
            case "PAN_DOC":
                uploadReq.setMimetype("text/plain");
                mapper.writeValue(new File("src/test/resources/kyc/apis/PANdoc.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.PAN_DOC);
                break;
            case "IPV_SELFIE":
                uploadReq.setMimetype("image/jpeg");
                mapper.writeValue(new File("src/test/resources/kyc/apis/selfiedoc.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.SELFIE_DOC);
                break;
            case "PERSONAL_DETAILS_INCOME_FORM":
                uploadReq.setMimetype("text/plain");
                mapper.writeValue(new File("src/test/resources/kyc/apis/incomeForm.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.PERSONAL_DETAILS_INCOME_FORM);
                break;
            case "ADDRESS_PROOF_VERIFICATION":
                uploadReq.setMimetype("text/plain");
                mapper.writeValue(new File("src/test/resources/kyc/apis/apvdoc.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.APV_DOC);
                break;
            case "USER_REVIEW_DETAILS":
                uploadReq.setMimetype("text/plain");
                mapper.writeValue(new File("src/test/resources/kyc/apis/userReviewDetails.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.USER_REVIEW_DETAILS);
                break;
            case "LOCATION_DETAILS":
                uploadReq.setMimetype("text/plain");
                mapper.writeValue(new File("src/test/resources/kyc/apis/locationDetails.json"), uploadReq);
                log.info("file is updated");
                uploadDoc = uploadDocument.postWithJsonHeader(queryParamDetailsUpload, headerDetailsUpload, BaseTestData.LOCATION_DETAILS);
                break;
            default:
                log.info("No Pending docs to upload");

        }
        return uploadDoc;
    }

    // InitiateKYC V9 API
    public InitiateKYCV9Pojo initiateKYCV9(String uuid, String product, String source) throws Exception {
        InitiateKYCV9 initiateKYCV9 = new InitiateKYCV9();
        String jsonRequestBodyInitiate = null;
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        headerDetailsInitiate.put("cache-control", "no-cache");

        jsonRequestBodyInitiate = new StringTemplate(KycConstants.INITIATEV9_REQUEST).replace("userid", uuid)
                .replace("product", product).replace("source", source).toString();

        JSONObject reqBodyObject = new JSONObject(jsonRequestBodyInitiate);
        if (Objects.isNull(uuid))
            reqBodyObject.remove("userId");
        if (Objects.isNull(product))
            reqBodyObject.remove("product");
        if (Objects.isNull(source))
            reqBodyObject.remove("source");

        return initiateKYCV9.post(queryParamDetailsInitiate, headerDetailsInitiate, reqBodyObject.toString());

    }

    public InitiateKYCV9Pojo initiateKYCV9(String uuid, String product) throws Exception {

        return initiateKYCV9(uuid, product, null);

    }

    public KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCase(String kycCaseId, String source, String uuid) throws Exception {
        DeactivateKycCase deactivateKycCase = new DeactivateKycCase();
        Map<String, Object> queryParamDetailsInitiate = new HashMap<>();
        HashMap<String, String> headerDetailsInitiate = new HashMap<>();
        String jsonRequestBodyInitiate = null;

        jsonRequestBodyInitiate = new StringTemplate(KycConstants.DEACTIVATE_KYC_CASE).replace("kycCaseId", kycCaseId)
                .replace("source", source).replace("userid", uuid).toString();
        JSONObject reqBodyObject = new JSONObject(jsonRequestBodyInitiate);
        if (Objects.isNull(kycCaseId))
            reqBodyObject.remove("kycCaseId");
        if (Objects.isNull(source))
            reqBodyObject.remove("source");
        if (Objects.isNull(uuid))
            reqBodyObject.remove("uuid");
        return deactivateKycCase.post(queryParamDetailsInitiate, headerDetailsInitiate, reqBodyObject.toString());


    }

    public KycGenericResponse<DeactivateKycCaseResponse> deactivateKycCase(String kycCaseId) throws Exception {
        return deactivateKycCase(kycCaseId, null, null);

    }


}

