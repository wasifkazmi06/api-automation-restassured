package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CheckEligibilityPojo {
    public ArrayList<EligibilityResponseList> responseList;
}
