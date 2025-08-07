package pojos.billpayment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBillerElement {
    public String billerId;
    public String billerName;
    public String flowType;
    public Object region;
    public String regionCode;

}
