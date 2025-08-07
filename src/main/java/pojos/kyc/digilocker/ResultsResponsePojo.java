package pojos.kyc.digilocker;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResultsResponsePojo {
    private String principalId;
    private String status;
    private String transactionId;
    private ResultsResponseData data;
    private Object errorCode;
    private Object errorMsg;

    private StartKYCErrorDataPojo errorData;

    //Error response for 500 errorcode
    private Date timestamp;
    private String error;
    private String path;
}
