package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Data {
    private ArrayList<Result> result;
    private latLngData targetLocation;

    private String billType;
    private ArrayList<ServiceProviders> serviceProviders;

    private String address;
    private String name;

}
