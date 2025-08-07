package pojos.kyc.apis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class UploadDocumentPojo {

    public String uuid;
    public int kycCaseId;
    public int docId;
    public String uploadStatus;
    public String message;
    public String kycStatus;
    public String kycTypeId;
    public List<DocumentsPendingPojo> documentsPending;
    public List<DocumentsPendingPojo> documentsReceived;
    public Object addDocCategory;
    public ResponseMetadataPojo responseMetadata;
    public String ckycResponseStatus;
    public boolean ckycResponsePending;
    public String error;
    public String errorCode;
    public String status;
    public String timestamp;
    public PanDelinkMetadata metadata;

}
