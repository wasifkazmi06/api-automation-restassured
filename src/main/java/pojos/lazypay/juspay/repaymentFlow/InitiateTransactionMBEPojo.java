package pojos.lazypay.juspay.repaymentFlow;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InitiateTransactionMBEPojo {
    public ObjectNode payload;
    public String txnId;
    public String timestamp;
    public String status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
    public String title;
    public String description;
    public String type;
    public String subtitle;


    //  private Payload payload;
    private ObjectNode meta;
    private ObjectNode actions;
    private ObjectNode analytics;

    }

