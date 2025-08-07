package heimdall.common;

import api.heimdall.apvChanges.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import pojos.heimdall.*;
import pojos.neobank.fd.Configuration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class BaseTest extends Utility {

    public BaseTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.APV_HIEMDALL_TESTDATA);
    FetchGeoLocation fetchLocation = new FetchGeoLocation();
    FetchProximityDistance fetchProximity = new FetchProximityDistance();
    FetchFields fetchFields = new FetchFields();
    FetchAddress fetchAddress = new FetchAddress();
    FetchCityList fetchCity = new FetchCityList();
    String errorCode;
    String errorMsg;


    public void geoLocationForValidCases(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request){
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        GeoLocationPojo locationDetails = fetchLocation.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(locationDetails.getStatus(), "SUCCESS", "incorrect response");
        Assert.assertNotNull(locationDetails.getData(), "failed to fetch location details for ");
        log.info("LatLng Data for pincode " + configuration.getGeoLocation_testData().getPincode() + " : lat : " + locationDetails.getData().getLat() + " & lng : " + locationDetails.getData().getLng());

    }
    public void geoLocationForInvalidCases(String testcase, Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) throws FileNotFoundException {
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        GeoLocationPojo locationDetails = fetchLocation.post(queryParamDetails, headerDetails, request);
        if(testcase.equalsIgnoreCase("Incorrect_AuthKey")){
            Assert.assertEquals(locationDetails.getStatus(),"403","Failed to get the response status");
        } else {
            Assert.assertEquals(locationDetails.getStatus(), "FAILED", "Response Mismatch");
            JsonObject errorresponse = getResponse(testcase);
            errorCode = errorresponse.get("errorCode").toString();
            errorMsg = errorresponse.get("errorMsg").toString();
            Assert.assertEquals(locationDetails.getErrorCode(), errorCode.replaceAll("^\"|\"$", ""));
            Assert.assertEquals(locationDetails.getErrorMsg(), errorMsg.replaceAll("^\"|\"$", ""));
            Allure.addAttachment(" For " + testcase, " : error_code is " + locationDetails.getErrorCode() + " error_message is " + locationDetails.getErrorMsg());
        }
    }

    public void fetchProximityDistance(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request,String testcase) throws FileNotFoundException {
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        LocationProximity proximityDistance = fetchProximity.post(queryParamDetails, headerDetails, request);
        if(testcase.contains("Invalid") || testcase.contains("Null") || testcase.contains("Empty")){
            JsonObject errorresponse = getResponse(testcase);
            String errorCode = errorresponse.get("errorCode").toString();
            String errorMsg = errorresponse.get("errorMsg").toString();
            Assert.assertEquals(proximityDistance.getErrorCode(), errorCode.replaceAll("^\"|\"$", ""));
            Assert.assertEquals(proximityDistance.getErrorMsg(), errorMsg.replaceAll("^\"|\"$", ""));
            Allure.addAttachment(" For "+ testcase," : error_code is " +proximityDistance.getErrorCode()+ " error_message is " + proximityDistance.getErrorMsg());

        } else if (testcase.equalsIgnoreCase("Incorrect_AuthKey")){
            Assert.assertEquals(proximityDistance.getStatus(),"403","Failed to get the response status");

        } else{
            Assert.assertEquals(proximityDistance.getStatus(),"SUCCESS","Failed to fetch response status for "+testcase);
            Assert.assertNotNull(proximityDistance.getData(), "Failed to fetch proximity data for " +testcase);
        }

    }

    public  void fetchFeilds(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String testcase,String billtype) throws FileNotFoundException {


        queryParamDetails.put("billType",billtype);
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        LocationProximity billtypefields = fetchFields.get(queryParamDetails, headerDetails);

        if(testcase.contains("Invalid") || testcase.contains("Null") || testcase.contains("Empty")){
            JsonObject errorresponse = getResponse(testcase);
            String errorCode = errorresponse.get("errorCode").toString();
            String errorMsg = errorresponse.get("errorMsg").toString();
            Assert.assertEquals(billtypefields.getErrorCode(), errorCode.replaceAll("^\"|\"$", ""));
            Assert.assertEquals(billtypefields.getErrorMsg(), errorMsg.replaceAll("^\"|\"$", ""));
            Allure.addAttachment(" For "+ testcase," : error_code is " +billtypefields.getErrorCode()+ " error_message is " + billtypefields.getErrorMsg());

        }else{
            Assert.assertEquals(billtypefields.getStatus(), "SUCCESS","Failed to fetch response status for "+testcase);
            ArrayList<String> actualServiceProviders = responseServiceProviders(billtypefields);
            for (BillTypeFieldsPojo type : configuration.getBillTypes_testData()) {
                if(type.getType().equalsIgnoreCase(billtype))
                    Assert.assertEquals(expectedServiceProviders(type), actualServiceProviders, "Getting incorrect service provider for "+testcase);
            }

        }


    }
    public void fetchaAddress(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String request, String testcase) throws FileNotFoundException {


        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        LocationProximity addressDetails = fetchAddress.post(queryParamDetails, headerDetails, request);
        if(testcase.contains("Invalid") || testcase.contains("Null") || testcase.contains("Empty")){
            JsonObject errorresponse = getResponse(testcase);
            String errorCode = errorresponse.get("errorCode").toString();
            String errorMsg = errorresponse.get("errorMsg").toString();
            Assert.assertEquals(addressDetails.getErrorCode(), errorCode.replaceAll("^\"|\"$", ""));
            Assert.assertEquals(addressDetails.getErrorMsg(), errorMsg.replaceAll("^\"|\"$", ""));
            Allure.addAttachment(" For "+ testcase," : error_code is " +addressDetails.getErrorCode()+ " error_message is " + addressDetails.getErrorMsg());

        }else{
            Assert.assertEquals(addressDetails.getStatus(),"SUCCESS","Failed to fetch response status for "+testcase);
            Assert.assertNotNull(addressDetails.getData(), "No address data available for "+testcase);
        }

    }
    public void fetchCityList(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String serviceprovider, String testcase) throws FileNotFoundException {

        queryParamDetails.put("serviceProvider",serviceprovider);
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        headerDetails.put("Accept", "*/*");

        CityList cityList = fetchCity.get(queryParamDetails,headerDetails);
        if(testcase.contains("Invalid") || testcase.contains("Null") || testcase.contains("Empty")){
            JsonObject errorresponse = getResponse(testcase);
            String errorCode = errorresponse.get("errorCode").toString();
            String errorMsg = errorresponse.get("errorMsg").toString();
            Assert.assertEquals(cityList.getErrorCode(), errorCode.replaceAll("^\"|\"$", ""));
            Assert.assertEquals(cityList.getErrorMsg(), errorMsg.replaceAll("^\"|\"$", ""));
            Allure.addAttachment(" For "+ testcase," : error_code is " +cityList.getErrorCode()+ " error_message is " + cityList.getErrorMsg());

        }else{
            Assert.assertEquals(cityList.getStatus(),"SUCCESS","Failed to fetch response status for "+testcase);
            Assert.assertNotNull(cityList.getData(), "Failed to fetch city list for "+testcase);
        }

    }

    public ArrayList<String> responseServiceProviders(LocationProximity locationDetails) {
        ArrayList<String> actual_providers = new ArrayList<>();
        for (ServiceProviders provider : locationDetails.getData().getServiceProviders()) {
            actual_providers.add(provider.getName());
        }
        return actual_providers;
    }

    public ArrayList<String> expectedServiceProviders(BillTypeFieldsPojo type) {
        ArrayList<String> expected_providers = new ArrayList<>();
        for (String serviceprovider : type.getServiceproviders()) {
            expected_providers.add(serviceprovider);

        }

        return expected_providers;
    }

    public String proximityPayload(String requestId, String source, ArrayList<latLngData> sourceLocation,latLngData targetLocation) throws JsonProcessingException {
        LocationProximityPayload testdata = new LocationProximityPayload();
        testdata.setRequestId(requestId);
        testdata.setSource(source);
        testdata.setSourceLocations(sourceLocation);
        testdata.setTargetLocation(targetLocation);
        ObjectMapper obj = new ObjectMapper();
        String payload = obj.writeValueAsString(testdata);
        return payload;

    }
    public String addressPayload(String requestId, String source, String type, String serviceProvider, String key, String value) throws JsonProcessingException {
        AddressRequestData testdata = configuration.getAddress_rquestData();
        Map<Object, String> requestData = new HashMap<>();
        Map<String,Object> data = new HashMap<>();

        if(type != null && type.equalsIgnoreCase("landline")&&key.contains("city"))
        {
            requestData.put("tel_no",testdata.getTel_no());

        }else if(type != null && type.equalsIgnoreCase("landline")&&key.contains("tel_no")){
            requestData.put("city",testdata.getCity());
        }
        requestData.put(key,value);
        requestData.put("service_provider",serviceProvider);
        data.put("requestData", requestData);
        data.put("requestId",requestId);
        data.put("source",source);
        data.put("type",type);
        ObjectMapper obj = new ObjectMapper();
        String payload = obj.writeValueAsString(data);
        System.out.println(payload);

        return payload;
    }

}
