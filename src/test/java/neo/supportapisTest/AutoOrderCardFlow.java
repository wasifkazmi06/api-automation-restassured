package neo.supportapisTest;

import api.neobank.cardControl.FetchShipmentUpdates;

import api.neobank.supportapis.CustPreference;
import api.neobank.supportapis.cardActions;
import api.neobank.supportapis.cardReplacement;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.cardControl.FetchShipmentUpdatesPojo;
import pojos.neobank.support.preference.CustomerPref;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AutoOrderCardFlow extends SupportApiData {

    public AutoOrderCardFlow() throws Exception {
        super();
    }

    CustPreference customerPreference = new CustPreference();

    @Description("To check if the Auto trigger is functional and as intended")
    @Feature("SupportApi")
    @Test(priority = 1, groups = {"Support", "AutoOrder"})
    public void autoOrderCardFlow_Case1() {
        log.info("Resetting data for the Auto order card flow");
        Assert.assertTrue(issueNewCard().equalsIgnoreCase("success"), "Failed to issue new card");
        updateCardStatusForAutoOrder();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String customerPerfRequest = new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid", NEO_CARD_REPLACEMENT_UUID).replace("status", Allowed_Status).replace("type", Transactiontype_CONTACTLESS).replace("source", "UNSECURED").toString();
        log.info("Query{}", customerPerfRequest);
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, customerPerfRequest);
        Assert.assertTrue(validateFireTime(), "Fire Time is updated more than 60 secs");
    }

    @Description("To check if the Auto trigger is functional and as intended")
    @Feature("SupportApi")
    @Test(priority = 1, groups = {"Support", "AutoOrder"})
    public void autoOrderCardFlow_Case2() {
        log.info("updating the data to check for the auto trigger physical card");
        updateNextFireTimeData();
        Assert.assertTrue(checkAutoTriggerStatus(), "Failed to request to physical card");
    }

    @SneakyThrows
    @Description("To check if the fetch shipment apis is functional and as intended")
    @Feature("SupportApi")
    @Test(priority = 1, groups = {"card_controller"})
    public void fetchShipmentUpdates() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        FetchShipmentUpdates fetchShipmentUpdates = new FetchShipmentUpdates();
        FetchShipmentUpdatesPojo fetchShipmentUpdatesPojo = fetchShipmentUpdates.get(queryParamDetails, headerDetails, "12603359");
        Assert.assertEquals(fetchShipmentUpdatesPojo.status, "Shipment Delivered");

    }
}
