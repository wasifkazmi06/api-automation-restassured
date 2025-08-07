package pojos.upi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostIntentPojo {

    public String upiTxnIntentStatus;
    public String upiTxnIntentType;
    public String upiTxnIntentId;
    public Boolean txnEligible;
    public MerchantDetailsPojo merchantDetails;
    public EligibilityResponsePojo eligibilityResponse;
    public AmountPojo amount;


}


