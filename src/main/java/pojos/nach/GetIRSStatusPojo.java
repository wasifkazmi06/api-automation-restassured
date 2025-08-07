package pojos.nach;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetIRSStatusPojo {

    public String repaySetupId;
    public String repayMethod;
    public String repayStatus;
    public String nextRepayStep;
    public String methodVendor;

    public String title;
    public String description;
    public String type;

}
