package neo.supportapisTest;

import api.neobank.supportapis.CustPreference;
import api.neobank.supportapis.CustomerPreference;
import api.neobank.supportapis.cardActionStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import neo.transaction.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.support.preference.CustomerPref;
import pojos.neobank.support.preference.cardStatus;
import util.StringTemplate;

import java.util.*;

@Slf4j
public class CustPreferenceResponse extends SupportApiData{

    public CustPreferenceResponse() throws Exception {
        super();
    }

    CustPreference customerPreference = new CustPreference();
    cardActionStatus cardStatus = new cardActionStatus();

    @SneakyThrows
    @Test
    @Description("Setting the Customer Preference with valid inputs")
    @Owner("Kareeshma")
    public void CustomerPreference() {
       long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid",TestCustomer_UUID).replace("status",Allowed_Status).replace("type",Transactiontype_CONTACTLESS).toString();
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);

        //Verify the updated status from the response getting by hitting cardactionstatus api
        cardStatus cardActionStatus = cardStatus.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(cardActionStatus.contactlessTransactionStatus,Allowed_Status);
        log.info("Contactless Transaction Status is : " +cardActionStatus.contactlessTransactionStatus);

        // Verify the updated status has been replicating in db as well
        String statusInDb = DbData.getCarpreference("contactLessTransactionEnabled",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        Assert.assertEquals(statusInDb,Allowed_Status);
    }
    @SneakyThrows
    @Test
    @Description("Set the Customer Preference with invalid UUID as null, status as NOTALLOWED & type as ONLINE")
    @Owner("Kareeshma")
    public void customerPreferenceWithNullUUID() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid","null").replace("status",NotAllowed_Status).replace("type",Transactiontype_ONLINE).toString();
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(customerPref.error,"Bad Request","Issue in setting up the Customer Preference due to "+customerPref.errorCode);
        Assert.assertEquals(customerPref.status,400);
        log.info("response status code : " +customerPref.status);

    }
    @SneakyThrows
    @Test
    @Description("Set the Customer Preference with different transaction types")
    @Owner("Kareeshma")
    public void differentTransactionTypes() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        List<String> transactionTypes = new ArrayList<>();
        transactionTypes.add(Transactiontype_OFFLINE);transactionTypes.add(Transactiontype_ONLINE);transactionTypes.add(Transactiontype_CONTACTLESS);
        headerDetails.put("Tenanat","1");
        for (String type : transactionTypes) {
            String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid",TestCustomer_UUID).replace("status",NotAllowed_Status).replace("type",type).toString();
            CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);
            Assert.assertEquals(customerPref.result,"true");

        }

    }
    @SneakyThrows
    @Test
    @Description("Set the Customer Preference with invalid status and uuid")
    @Owner("Kareeshma")
    public void custPreferenceWithInvalidStatus() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CUSTOMER_PREFERENCE_REQUEST).replace("uuid","null").replace("status","HOLD").replace("type",Transactiontype_ONLINE).toString();
        CustomerPref customerPref = customerPreference.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(customerPref.error,"Bad Request","Issue in setting up the Customer Preference due to "+customerPref.errorCode);
        Assert.assertEquals(customerPref.status,400);
        log.info("response status code : " +customerPref.status);
    }

}
