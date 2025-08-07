package heimdall.common;

import org.testng.annotations.DataProvider;
import pojos.heimdall.AddressRequestData;
import pojos.heimdall.BillTypeFieldsPojo;
import pojos.heimdall.GeoLocationPojo;
import pojos.heimdall.latLngData;

import java.util.ArrayList;



public class APVHeimdallTestData {


    public APVHeimdallTestData() throws Exception {
        super();
    }

    BaseTest tests = new BaseTest();
    GeoLocationPojo locationRequest = tests.configuration.getGeoLocation_testData();
    ArrayList<BillTypeFieldsPojo> billtypeRequest = tests.configuration.getBillTypes_testData();
    ArrayList<latLngData> sourcelatLong = tests.configuration.getProximity_Distance_testData().getSourceLocations();
    latLngData targetlatLong = tests.configuration.getProximity_Distance_testData().getTargetLocation();
    AddressRequestData addressRequest = tests.configuration.getAddress_rquestData();
    String emptyvalue = "";

    @DataProvider(name = "validDataForGeoLocation")
    public Object[][] validDataForGeoLocation() {
        return new Object[][]{
                {"ValidPincodeANDAddress", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource1(),"3a5cb67f70e9fab1"},
                {"WithEmptyAddress", emptyvalue, locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource1(),"3a5cb67f70e9fab1"},
                {"WithNullAddress", null, locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource1(),"3a5cb67f70e9fab1"},
                {"Valid_source_LP", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource1(),"3a5cb67f70e9fab1"},
                {"Valid_source_PS", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource2(),"3a5cb67f70e9fab1"},
                {"Valid_AuthKey_LP", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource2(),"c129393471de3bd1"},
                {"Valid_AuthKey_PS", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource2(),"3a5cb67f70e9fab1"},
                {"Valid_AuthKey_Payments", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource2(),"31ff179aadcf26a8"},

        };
    }

    @DataProvider(name = "invalidDataForGeoLocation")
    public Object[][] invalidDataForGeoLocation() {
        return new Object[][]{
                {"Invalid_pincode_With6Digit", locationRequest.getAddress(), locationRequest.getInvalidPincode1(), locationRequest.getRequestId(), locationRequest.getSource1(),"c129393471de3bd1"},
                {"Invalid_pincode_Without6Digit", locationRequest.getAddress(), locationRequest.getInvalidPincode2(), locationRequest.getRequestId(), locationRequest.getSource1(),"c129393471de3bd1"},
                {"WithEmptyPincode", locationRequest.getAddress(), emptyvalue, locationRequest.getRequestId(), locationRequest.getSource1(),"c129393471de3bd1"},
                {"WithNullPincode", locationRequest.getAddress(), null, locationRequest.getRequestId(), locationRequest.getSource1(),"c129393471de3bd1"},
                {"Invalid_source", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getInvalidSource(),"c129393471de3bd1"},
                {"WithEmptySource", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), emptyvalue,"c129393471de3bd1"},
                {"WithNullSource", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), null,"c129393471de3bd1"},
                {"WithEmptyRequestId", locationRequest.getAddress(), locationRequest.getPincode(), emptyvalue, locationRequest.getSource1(),"c129393471de3bd1"},
                {"WithNullRequestId", locationRequest.getAddress(), locationRequest.getPincode(), null, locationRequest.getSource1(),"c129393471de3bd1"},
                {"Incorrect_AuthKey", locationRequest.getAddress(), locationRequest.getPincode(), locationRequest.getRequestId(), locationRequest.getSource2(),"31ff179aadcf"},

        };

    }

    @DataProvider(name = "getProximityData")
    public Object[][] getProximityData() {
        return new Object[][]{
                {"ValidRequestData", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"3a5cb67f70e9fab1"},
                {"Valid_AuthKey_LP", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"Valid_AuthKey_PS", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"3a5cb67f70e9fab1"},
                {"Valid_AuthKey_Payments", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"31ff179aadcf26a8"},
                {"With_Different_Source&targetLocations", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"With_Single_SourceLocation", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"Valid_source_LP", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"Valid_source_PS", locationRequest.getRequestId(), locationRequest.getSource2(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"Invalid_source", locationRequest.getRequestId(), locationRequest.getInvalidSource(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"WithEmptySource", locationRequest.getRequestId(), emptyvalue, sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"WithNullSource", locationRequest.getRequestId(), null, sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"WithEmptyRequestId", emptyvalue, locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"WithNullRequestId", null, locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3bd1"},
                {"WithNullSourceLocation", locationRequest.getRequestId(), locationRequest.getSource2(), null, targetlatLong,"c129393471de3bd1"},
                {"WithNullTargetLocation", locationRequest.getRequestId(), locationRequest.getSource2(), sourcelatLong, null,"c129393471de3bd1"},
                {"Incorrect_AuthKey", locationRequest.getRequestId(), locationRequest.getSource1(), sourcelatLong, targetlatLong,"c129393471de3b"},

        };
    }


    @DataProvider(name = "getFieldsForBilltypes")
    public Object[][] getFieldsForBilltypes() {
        return new Object[][]{
                {"ValidBillType_PNG", billtypeRequest.get(0).getType()},
                {"ValidBillType_LPG", billtypeRequest.get(1).getType()},
                {"ValidBillType_Landline", billtypeRequest.get(2).getType()},
                {"Invalid_BillType", locationRequest.getInvalidSource()},
                {"WithEmptyBillType",emptyvalue},
                {"WithNullBillType", null},

        };
    }
    @DataProvider(name = "getAddressData")
    public Object[][] getAddressData() {
        return new Object[][]{
                {"ValidLPGRequest_HP", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"ValidLPGRequest_BharatGas", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"Bharat Gas","lpg_id",addressRequest.getLpg_id()},
                {"ValidLandlineRequest_BSNL", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(2).getType(),"BSNL","tel_no",addressRequest.getTel_no()},
                {"ValidLandlineRequest_MTNL", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(2).getType(),"MTNL","tel_no",addressRequest.getTel_no()},
                {"Valid_ElectricityRequest", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(3).getType(),"BESCOM","consumer_id",addressRequest.getElectricity_consumer_id()},
                {"Valid_PNGRequest", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(0).getType(),"IG","bp_no",addressRequest.getBp_no()},

                {"Invalid_source", locationRequest.getRequestId(), locationRequest.getInvalidSource(), billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithEmptySource", locationRequest.getRequestId(), emptyvalue, billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithNullSource", locationRequest.getRequestId(), null, billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithEmptyRequestId", emptyvalue, locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithNullRequestId",null, locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"Invalid_BillType", locationRequest.getRequestId(), locationRequest.getSource1(), locationRequest.getInvalidSource(),"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithEmptyBillType", locationRequest.getRequestId(), locationRequest.getSource1(), emptyvalue,"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithNullBillType", locationRequest.getRequestId(), locationRequest.getSource1(), null,"HP","lpg_id",addressRequest.getLpg_id()},
                {"WithInvalid_ServiceProvider", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"BSNL","lpg_id",addressRequest.getLpg_id()},
                {"AddressRequest_WithEmptyServiceProvider", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),emptyvalue,"lpg_id",addressRequest.getLpg_id()},
                {"AddressRequest_WithNullServiceProvider", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),null,"lpg_id",addressRequest.getLpg_id()},

                {"Invalid_lpgId", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",addressRequest.getLpg_id()+"test"},
                {"WithEmptyLpgId", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",emptyvalue},
                {"WithNullLpgId", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(1).getType(),"HP","lpg_id",null},

                {"Invalid_telNo", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(2).getType(),"BSNL","tel_no",addressRequest.getTel_no()+"test"},
                {"WithEmptyTelNo", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(2).getType(),"BSNL","tel_no",emptyvalue},
                {"WithNullTelNo", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(2).getType(),"BSNL","tel_no",null},

                {"Invalid_consumerId_Electricity", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(3).getType(),"BESCOM","consumer_id","1"},
                {"WithEmptyConsumerId_Electricity", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(3).getType(),"BESCOM","consumer_id",emptyvalue},
                {"WithNullConsumerId_Electricity", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(3).getType(),"BESCOM","consumer_id",null},

                {"WithEmptyBpNo", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(0).getType(),"IG","bp_no",emptyvalue},
                {"WithNullBpNo", locationRequest.getRequestId(), locationRequest.getSource1(), billtypeRequest.get(0).getType(),"IG","bp_no",null},


        };
    }

    @DataProvider(name = "getCityListWithProvider")
    public Object[][] getCityListWithProvider() {
        return new Object[][]{
                {"ValidServiceProvider_BSNL", billtypeRequest.get(2).getServiceproviders().get(0)},
                {"ValidServiceProvider_MTNL", billtypeRequest.get(2).getServiceproviders().get(1)},
                {"WithInvalid_ServiceProvider", locationRequest.getInvalidSource()},
                {"WithNullServiceProvider", null},

        };
    }


}
