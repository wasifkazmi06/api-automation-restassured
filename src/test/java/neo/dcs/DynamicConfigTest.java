package neo.dcs;


import api.neobank.dcs.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import pojos.neobank.dcs.DCS_Pojo;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicConfigTest extends DynamicConfigSupportData {

    public DynamicConfigTest() throws Exception {
        super();
    }

    public static String verticalID;
    public static String clientID;

    RegisterVertical registerVertical = new RegisterVertical();
    RegisterClient registerClient = new RegisterClient();
    GetSpecifiedClientDetails getSpecifiedClientDetails = new GetSpecifiedClientDetails();
    UpdateClientDetails updateClientDetails = new UpdateClientDetails();
    DeleteClientDetails deleteClientDetails = new DeleteClientDetails();
    AddConfigDetails addConfigDetails = new AddConfigDetails();
    GetConfigDetails getConfigDetails = new GetConfigDetails();
    DeleteConfigDetails deleteConfigDetails = new DeleteConfigDetails();


    @Description("To check if the Create vertical is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 1, groups = {"DCS"})
    public void registerVertical() {
        log.info("Registering vertical with " + verticalName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        String registerVerticalRequest = new StringTemplate(DCS_Constants.REGISTER_VERTICAL).replace("verticalName", verticalName).toString();
        log.info("Query{}", registerVerticalRequest);
        DCS_Pojo registerVerticalResponse = registerVertical.post(queryParamDetails, headerDetails, registerVerticalRequest);
        log.info("response status code : " + registerVerticalResponse.statusCode);
        Assert.assertEquals(registerVerticalResponse.getStatus(), "OK", "Failed to register vertical in DCS");
        verticalID = registerVerticalResponse.getPayload().getVerticalId();
        log.info("Registered verticalID is " + verticalID);
        Assert.assertTrue(verifyRegisteredVertical(verticalID), "Registered verticalID is incorrect");
    }

    @Description("To check if the Create Client isn functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 2, groups = {"DCS"})
    public void registerClient() {
        log.info("Registering client with " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");

        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        String registerClientRequest = new StringTemplate(DCS_Constants.REGISTER_CLIENT).replace("clientName", clientName).replace("verticalId", verticalID).toString();
        log.info("Query{}", registerClientRequest);
        DCS_Pojo registerClientResponse = registerClient.post(queryParamDetails, headerDetails, registerClientRequest);
        log.info("response status code : " + registerClientResponse.statusCode);
        Assert.assertEquals(registerClientResponse.getStatus(), "Created", "Failed to register client in DCS");
        clientID = registerClientResponse.getPayload().getClientId();
        log.info("Registered clientID is " + clientID);
        Assert.assertTrue(verifyRegisteredClient(clientID), "Registered clientID is incorrect");
    }

    @Description("To check if the get specified Client is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 3, groups = {"DCS"}, dependsOnMethods = {"registerClient"})
    public void getClientDetails() {
        log.info("getClientDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        DCS_Pojo getSpecifiedClientDetailsResponse = getSpecifiedClientDetails.get(queryParamDetails, headerDetails, clientID);
        log.info("response status code : " + getSpecifiedClientDetailsResponse.statusCode);
        Assert.assertEquals(getSpecifiedClientDetailsResponse.getStatus(), "OK", "Failed to get Specified client details");
        clientID = getSpecifiedClientDetailsResponse.getPayload().getClientId().toString();
        Assert.assertEquals(getSpecifiedClientDetailsResponse.getPayload().getClientId(), clientID, "ClientID is not matching to the requested one");
        Assert.assertEquals(getSpecifiedClientDetailsResponse.getPayload().getClientStatus(), "ACTIVE", "Client status is not set Active by default");

    }

    @Description("To check if the update specified Client is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 4, groups = {"DCS"}, dependsOnMethods = {"registerClient"})
    public void updateClientDetails() {
        log.info("updateClientDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        String updateClientRequest = new StringTemplate(DCS_Constants.UPDATE_CLIENT).replace("clientName", clientName + "_update").replace("clientId", clientID).replace("clientStatus", "inactive").replace("verticalId", verticalID).toString();
        log.info("Query{}", updateClientRequest);
        DCS_Pojo updateClientDetailsResponse = updateClientDetails.put(queryParamDetails, headerDetails, updateClientRequest);
        log.info("response status code : " + updateClientDetailsResponse.statusCode);
        Assert.assertEquals(updateClientDetailsResponse.getStatus(), "OK", "Failed to get Specified client details");
        Assert.assertTrue(verifyUpdateClient(clientID, verticalID), "Failed to update the client Details");
    }


    @Description("To check if the add config is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 5, groups = {"DCS"}, dependsOnMethods = {"registerClient"})
    public void addConfigDetails() {
        log.info("addConfigDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        String addConfigRequest = new StringTemplate(DCS_Constants.ADD_CONFIG).replace("clientId", clientID).replace("configStatus", "Active").replace("key", configName).replace("value", configValue).toString();
        log.info("Query{}", addConfigRequest);
        DCS_Pojo addConfigResponse = addConfigDetails.put(queryParamDetails, headerDetails, addConfigRequest);
        log.info("response status code : " + addConfigResponse.statusCode);
        Assert.assertEquals(addConfigResponse.getStatus(), "OK", DCS_Constants.FAILED_CONFIG_MSG);
    }

    @Description("To check if the add config is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 6, groups = {"DCS"}, dependsOnMethods = {"registerClient"})
    public void getConfigDetails() {
        log.info("getConfigDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("client_id", clientID);
        queryParamDetails.put("key", configName);
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        DCS_Pojo getConfigResponse = getConfigDetails.get(queryParamDetails, headerDetails);
        log.info("response status code : " + getConfigResponse.statusCode);
        Assert.assertEquals(getConfigResponse.getPayload().getKey(), configName, "Failed to get the expected config key");
        Assert.assertEquals(getConfigResponse.getPayload().getValue(), configValue, "Failed to get the expected config value");
        Assert.assertEquals(getConfigResponse.getStatus(), "OK", "Failed to add config to the client in DCS");
    }

    @Description("To check if the delete specified Client is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 7, groups = {"DCS"}, dependsOnMethods = {"registerClient", "addConfigDetails"})
    public void deleteConfigDetails() {
        log.info(" deleteConfigDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        String deleteConfigRequest = new StringTemplate(DCS_Constants.DELETE_CONFIG).replace("clientId", clientID).replace("key", configName).toString();
        DCS_Pojo deleteSpecifiedConfigDetailsResponse = deleteConfigDetails.delete(queryParamDetails, headerDetails, deleteConfigRequest);
        log.info("response status code : " + deleteSpecifiedConfigDetailsResponse.statusCode);
        Assert.assertEquals(deleteSpecifiedConfigDetailsResponse.getStatus(), "OK", "Failed to delete Specified config details");
    }

    @Description("To check if the delete specified Client is functional and as intended")
    @Feature("DCS_Config")
    @Test(priority = 8, groups = {"DCS"}, dependsOnMethods = {"registerClient"})
    public void deleteClientDetails() {
        log.info(" deleteClientDetails test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        DCS_Pojo deleteSpecifiedClientDetailsResponse = deleteClientDetails.delete(queryParamDetails, headerDetails, clientID);
        log.info("response status code : " + deleteSpecifiedClientDetailsResponse.statusCode);
        Assert.assertEquals(deleteSpecifiedClientDetailsResponse.getStatus(), "OK", "Failed to delete Specified client details");
    }

}
