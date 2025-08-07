package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePanDelinkPojo {
    public String uuid;
    public String status;
    public String statusCode;
    public String message;
}
