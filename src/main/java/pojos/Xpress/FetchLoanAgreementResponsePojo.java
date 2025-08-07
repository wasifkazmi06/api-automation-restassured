package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchLoanAgreementResponsePojo {
    public String preview_url;
    public String otp_url;
    public ArrayList<String> multi_preview_urls;
    public String timestamp;
    public Double status;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String errorDetails;
    public String maskedErrorCode;
    public String message;
}
