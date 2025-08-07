package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetTransactionSuccessPojo {

    public String screenId;
    public String screenName;
    public String screenType;
    public String staticScreen;


    public ObjectNode headerTitle;
    public ObjectNode headerSubtitle;
    public ObjectNode headerIcon;
    public ObjectNode txnStatusWidgetGroup;
    public ObjectNode bannerWidgetList;
    public ObjectNode primaryButton;
    public String statusCode;
    public String nachRequired;
    public String timestamp;
    public String status;
    public String error;
    public String path;
    public String errorCode;
    public String message;






}
