package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class V3CaseDetailsResponsePojo {
    public String uuid;
    public String status;
    public CaseDetailsDataPojo data;
    public Object errorCode;
    public Object errorMsg;
}
