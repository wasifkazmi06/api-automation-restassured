package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitPanFormResponsePojo {

    public String umUUID;
    public String message;
    public String nextStep;
    public String waitingForStep;
    public String productOffering;
    public String lastCompletedCheckpoint;
    public String timestamp;
    public Double status;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
}
