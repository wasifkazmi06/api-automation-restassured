package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class LocationProximityPayload {
    private ArrayList<latLngData> sourceLocations;
    private latLngData targetLocation;

    private String requestId;
    private String Source;
}
