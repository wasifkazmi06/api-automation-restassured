package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;
import pojos.heimdall.AddressRequestData;
import pojos.heimdall.BillTypeFieldsPojo;
import pojos.heimdall.GeoLocationPojo;
import pojos.heimdall.LocationProximity;
import pojos.kyc.digilocker.RequestTestData;

import java.util.ArrayList;

@Getter
@Setter
public class Configuration{


    private FdVerifyUpi verifyUpi_testData;
    private FdDetails fdDetails_testData;
    private NomineeDetails createOrder_nomineedetails;

    //APV_Heimdall service changes
    private GeoLocationPojo geoLocation_testData;
    private LocationProximity proximity_Distance_testData;
    private ArrayList<BillTypeFieldsPojo> billTypes_testData;
    private AddressRequestData address_rquestData;

    // digilocker
    private RequestTestData digilocker_data;
}