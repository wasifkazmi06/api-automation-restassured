package pojos.kyc.digilocker;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class StatusResponsePojo {
    private String principalId;
    private String status;
    private ArrayList<StatusResponseData> data;

    //Error response
    private StartKYCErrorDataPojo errorData;

    //Error response for 500 errorcode
    private Date timestamp;
    private String error;
    private String path;
}
