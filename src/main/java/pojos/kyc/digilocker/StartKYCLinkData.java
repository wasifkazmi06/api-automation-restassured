package pojos.kyc.digilocker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartKYCLinkData {
    private String transactionId;
    private String startDigilockerUrl;
}
