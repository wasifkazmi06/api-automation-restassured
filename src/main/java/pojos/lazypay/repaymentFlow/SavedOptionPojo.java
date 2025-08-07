package pojos.lazypay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SavedOptionPojo {

    public List<CardDetailsPojo> cards;
    public List<String> vpaList;
    public int isClEnabled;
    public int isRevolveEligible;
    public int isPartialRepaymentEligible;
    public int showSavedCCBannerForRevolveUser;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    //public List<ErrorCodePojo> error;

}
