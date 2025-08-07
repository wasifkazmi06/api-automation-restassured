package kyc.ckyc;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class UploadDocRequest {
    private String uuid;
    private String kycCaseId;
    private String docTypeId;
    private String mimetype;
    private String data;
    private boolean uploadedFromGallery;
}
