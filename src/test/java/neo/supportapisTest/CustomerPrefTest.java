package neo.supportapisTest;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;

import lombok.SneakyThrows;


import neo.NeoConstants;
import org.testng.annotations.Test;

import api.neobank.supportapis.CustomerPreference;
import pojos.neobank.support.preference.CustomerPref;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static neo.supportapisTest.SupportApiData.TestCustomer_UUID;

public class CustomerPrefTest {

    public CustomerPrefTest() throws Exception {
        super();
    }

    CustomerPreference customerPreference=new CustomerPreference();
    @SneakyThrows
    @Test
    @Description("TEST SAMPLE")
    @Owner("swat")
    public void testCustomer() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid",TestCustomer_UUID).replace("status","ALLOWED").replace("type","CONTACTLESS").toString();
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);
        System.out.println(customerPref.getResult());
        SupportApiData.reSetCardPreference();
    }
    @SneakyThrows
    @Test
    @Description("TEST SAMPLE")
    @Owner("swat")
    public void testCustomer1() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid",TestCustomer_UUID).replace("status","ALLOWED").replace("type","POS").toString();
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);
        System.out.println(customerPref.getResult());
        SupportApiData.reSetCardPreference();
    }

}