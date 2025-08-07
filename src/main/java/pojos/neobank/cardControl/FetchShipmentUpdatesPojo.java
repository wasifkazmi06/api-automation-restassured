package pojos.neobank.cardControl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchShipmentUpdatesPojo {

    public String status;
    public String remark;
    public String orderId;
    public String courierName;
    public String waybill;
    public String lastUpdated;
    public String estimateDateOfDelivery;
    public String location;
    public String orderDate;
    public String dispatchDate;
    public String cardStatus;
    public GlobalConfig globalConfig;
    public Tracking_history[] tracking_history;


}
