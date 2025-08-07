package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private double proximity;
    private latLngData sourceLocation;
}
