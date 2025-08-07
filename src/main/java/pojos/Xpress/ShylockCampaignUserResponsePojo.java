package pojos.Xpress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShylockCampaignUserResponsePojo {

    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
}
