package pojos.heimdall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class GeoLocationPojo {

    //request
    private String address;
    private String pincode;
    private String source1;
    private String source2;
    private String invalidPincode1;
    private String invalidPincode2;
    private String invalidSource;


    //response
    private String requestId;
    private String status;

    private latLngData data;

    private String errorCode;
    private String errorMsg;
    private String error;
    private Date timestamp;
    private String path;
}
