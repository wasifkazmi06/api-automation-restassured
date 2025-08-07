package pojos.cof.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EligibilityPojo {
    private UserDetailsPojo userDetails;
    private AmountPojo amount;
    private CbpConsentPojo cbpConsent;
}
