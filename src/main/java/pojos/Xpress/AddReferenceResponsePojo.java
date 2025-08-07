package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddReferenceResponsePojo {

    public Double id;
    public String fullName;
    public String refId;
    public String phoneNumber;
    public Integer relation;
    public Integer referenceMode;
    public Integer referenceCreatedBy;
    public Integer status;
    public Integer codeVerificationStatus;

    public String timestamp;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
}
