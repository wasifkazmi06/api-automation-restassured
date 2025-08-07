package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class LocationProximity {


    private Double sourceLocation_lat;
    private Double sourceLocation_lng;
    private Double targetLocation_lat;
    private Double targetLocation_lng;
    private ArrayList<latLngData> sourceLocations;
    private latLngData targetLocation;

    private String requestId;
    private String Source;
    private String status;
    private Data data;
    private String errorCode;
    private String errorMsg;
    private String error;
    private Date timestamp;
    private String path;


}
