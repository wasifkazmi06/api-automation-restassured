package pojos.kyc.digilocker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponseData {

    private String status;
    private String transactionId;

    //incase of auto_declined/auto_approved
    private ResultsMetadata metadata;
}
