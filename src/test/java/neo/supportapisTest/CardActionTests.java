package neo.supportapisTest;

import api.neobank.cardControl.GetNameSuggestions;
import api.neobank.cardControl.GetTrnsactionLimit;
import api.neobank.cardControl.SetTransactionLimit;
import api.neobank.supportapis.cardActions;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import neo.NeoConstants;
import neo.transaction.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.cardControl.GetNameSuggestionsPojo;
import pojos.neobank.cardControl.GetUserTransactionLimitPojo;
import pojos.neobank.cardControl.SetTransactionLimitPojo;
import pojos.neobank.support.preference.lazyCardActions;
import util.StringTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CardActionTests extends SupportApiData{

    public CardActionTests() throws Exception {
        super();
    }


    private String offlineAmount = "3500";
    private String onlineAmount = "4500";

    private String userNameSuggestions_umUuid="9003369316324599877";
    private String userNameSuggestions_uuid="12603349";
    cardActions card_actions = new cardActions();
    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test (priority = 1)
    @Description("Unlocking the card without UUID detail")
    public void UnlockCardWithoutUUID() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid","null").replace("flag",UNLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        Assert.assertEquals(cardAction.error,"Bad Request","Issue in Unlocking the card due to "+cardAction.errorCode);
        Assert.assertEquals(cardAction.status,400);
        log.info("response status code : " +cardAction.status);

    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    //@Test (priority = 2)
    @Description("Lock the card for valid uuid")
    public void LockTheCard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid()).replace("flag",LOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        String cardStatus_after = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status after locking the card in db is : "+cardStatus_after);
        Assert.assertEquals(cardStatus_after,LOCK_CARD_FLAG);
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 3)
    @Description("Unlocking the card which is already in Unlock status")
    public void Locking_lockedCard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions in db is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid()).replace("flag",LOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        Assert.assertEquals(cardAction.error,"Bad Request","error message : "+cardAction.message);
        Assert.assertEquals(cardAction.status,400);
        log.info("response status code : " +cardAction.status);
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 4)
    @Description("Lock the card for valid uuid")
    public void UnlockTheCard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions in db is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid()).replace("flag",UNLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        String cardStatus_after = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status after Unlocking the card in db is : "+cardStatus_after);
        Assert.assertEquals(cardStatus_after,UNLOCK_CARD_FLAG);
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 5)
    @Description("Unlocking the card which is already in Unlock status")
    public void Unlock_UnlockedCard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",TestCustomer_UUID).replace("flag",UNLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        Assert.assertEquals(cardAction.error,"Bad Request","error message : "+cardAction.message);
        Assert.assertEquals(cardAction.status,400);
        log.info("response status code : " +cardAction.status);
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test (priority = 6,groups = "blockCard")
    @Description("Lock the card for valid uuid")
    public void BlockTheCard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardStatus_before = DbData.getCarpreference("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status before performing any card actions is : "+cardStatus_before);
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",TestCustomer_UUID).replace("flag",BLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        log.info(cardAction.getResult());
        String cardStatus_after = DbData.getBlockedCardStatus("freezeStatus",getPlatformUSerData(TransactionData.CARD_ACTION_USER).getLpUuid());
        log.info("card status after Blocking the card is : "+cardStatus_after);
        Assert.assertEquals(cardStatus_after,BLOCK_CARD_FLAG);
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 7, groups = "Transactions")
    @Description("Set user online and offline Txn limits")
    public void setUserTxnLimit() {
        log.info("setTxnLimit test started");
        SetTransactionLimitPojo setTransactionLimitPojo = setuserTransactionLimit(offlineAmount, onlineAmount, "12603359");
        Assert.assertTrue(setTransactionLimitPojo.getSuccess().equalsIgnoreCase("true"), "Failed to set txn Limit");
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 8, groups = "Transactions")
    @Description("Get the offline and online txns limits")
    public void getUserTransactionLimit() {
        GetUserTransactionLimitPojo getUserTransactionLimit = getUserTransactionLimit("12603359");
        Assert.assertTrue(((Integer) getUserTransactionLimit.getCurrentOnlineLimit()).toString().equalsIgnoreCase(onlineAmount), "Failed to get the set txm limit");
        Assert.assertTrue(((Integer) getUserTransactionLimit.getCurrentOfflineLimit()).toString().equalsIgnoreCase(offlineAmount), "Failed to get the set txm limit");
    }

    @SneakyThrows
    @Feature("Card_Action_Flows")
    @Test(priority = 9, groups = "Transactions")
    @Description("Get the offline and online txns limits")
    public void getUserNameSuggestions() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        GetNameSuggestions getNameSuggestions = new GetNameSuggestions();
        String request = new StringTemplate(NeoConstants.USER_NAME_SUGGESTIONS).replace("umUuid", userNameSuggestions_umUuid).replace("uuid", userNameSuggestions_uuid).toString();
        GetNameSuggestionsPojo getNameSuggestionsPojo = getNameSuggestions.post(queryParamDetails, headerDetails, request);
        Assert.assertEquals(getNameSuggestionsPojo.getSuggestions().length, 3, "Failed to provide the name suggestions");
        boolean isNameOverLength = false;
        for (String suggestion : getNameSuggestionsPojo.getSuggestions()) {
            if (suggestion.length() > 15) {
                isNameOverLength = true;
                break;
            }
        }
        Assert.assertFalse(isNameOverLength, "Length of name suggestions is greater than 15 characters");
    }
}
