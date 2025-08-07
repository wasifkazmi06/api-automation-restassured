package neo.supportapisTest;

import api.neobank.supportapis.cardActions;
import api.neobank.supportapis.cardReplacement;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import dbUtils.Lazycard_MySQL_DBAccessObject;
import neo.BaseTestNeo;
import neo.NeoConstants;
import pojos.neobank.support.CardControlPojo;
import pojos.neobank.support.preference.lazyCardActions;
import util.StringTemplate;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SupportApiData extends BaseTestNeo {

    private static final String SET_CARD_PREF_ONLINE_NOTALLOWED="UPDATE lazycards SET contactLessTransactionEnabled = 'NOTALLOWED', onlineTransactionEnabled= 'NOTALLOWED',posTransactionEnabled='NOTALLOWED' WHERE lazypayUuid = 5837325 AND status ='ACTIVE';";
    private  static final String GET_CARD_PREFERENCE="SELECT $ FROM lazycards l WHERE lazypayUuid = 5837325 AND status ='ACTIVE';";
    public static final String BLOCK_USER_QUERY="UPDATE users SET isBlocked = $1 WHERE uuid = $2;";
    public static final String SET_PHYSICAL_CARD_REQUESTED="UPDATE `lazycard`.`lazycards` SET physicalCardRequested = 'NOTREQUESTED', formFactor = 'VIRTUAL' WHERE lazypayUuid=12603348 and status='ACTIVE';";
    public static final String GET_PHYSICAL_CARD_REQUESTED="select $ from lazycards where lazypayUuid='5837325' and status='ACTIVE';";
    public static final String GET_ISKYC_ADDRESS="select isKycAddress from address where lazypayUuid='5830086'";
    public static final String GET_ADDRESS_ID="select * from address where lazypayUuid='5830086'";
    public static final String DELETE_ADDRESS="DELETE a.* from address a where id IN(select iid from (select id iid from address where lazypayUuid='117315') x);";
    public static final String DELETE_ADDRESS_MAPPING="DELETE a.* from lazycardAddressMapping a where id IN(select iid from (select id iid from lazycardAddressMapping where addressId='"+GET_ADDRESS_ID+"') x);";
    public static final String GET_CARD_REQUEST_STATUS="select physicalCardRequested from lazycards where lazypayUuid = 12603348 AND status ='ACTIVE';";
    public static final String GET_CARD_ID="select id from lazycards where lazypayUuid=5837325";
    public static final String DELETE_BLOCKED_CARD="DELETE from lazycards where id=?";
    public static final String PHYSICAL_CARD_ORDER_ID="select id from lazycardPhysicalOrders where lazypayUuid='12603348' AND status = 'SUCCESS';";
    public static final String SET_CARD_PREF_TO_ON_BOARDING_STATE="UPDATE lazycard.lazycards SET status='PENDING', lockStatus=NULL, contactLessTransactionEnabled=NULL, freezeStatus=NULL, onlineTransactionEnabled=NULL, posTransactionEnabled=NULL, formFactor='VIRTUAL', physicalCardRequested=NULL, offlineTxnLimit=NULL, onlineTxnLimit=NULL, bankingPartnersId=2, lastCardDigit=NULL, cardType=NULL, createdDate='2022-04-08 19:29:23.652000000', updatedDate='2022-04-08 19:29:24.246000000' WHERE lazypayUuid=12603348 and status='ACTIVE';";
    public static final String GET_KIT_NUMBER="SELECT kitNumber FROM lazycard.lazycards WHERE lazypayUuid = 12603348 AND status ='ACTIVE';";
    public static final String DELETE_AUTO_CARD_ORDER="DELETE FROM lazycard.auto_card_orders WHERE kit_number IN (SELECT kitNumber FROM lazycard.lazycards WHERE lazypayUuid = 12603348 AND status ='ACTIVE');";
    public static final String GET_AUTO_ORDER_CARD_ID="SELECT id FROM lazycard.auto_card_orders WHERE lp_uuid=12603348;";
    public static final String DELETE_AUTO_CARD_ORDERS = "DELETE FROM lazycard.auto_card_orders WHERE lp_uuid=12603348;";
    /**
     * test data
     */

    public static final String BLOCK_CARD_FLAG="B";
    public static final String LOCK_CARD_FLAG="L";
    public static final String UNLOCK_CARD_FLAG="UL";

    public static final String AUTHORIZATION_TOKEN="141d0b78-767f-4881-808e-34057f0a9bde";
    public static final String SOURCE="card_controls";
    public static Connection connection;

    public static final String Allowed_Status="ALLOWED";
    public static final String NotAllowed_Status="NOTALLOWED";
    public static final String Transactiontype_ONLINE="ONLINE";
    public static final String Transactiontype_OFFLINE="OFFLINE";
    public static final String Transactiontype_CONTACTLESS="CONTACTLESS";
    public static final String TestCustomer_UUID="5837318";
    public static final String NEO_CARD_UUID="5837336";
    public static final String NEO_CARD_REPLACEMENT_UUID="12603348";

    cardActions card_actions = new cardActions();
    cardReplacement cardreplacement = new cardReplacement();

    public SupportApiData() throws Exception {
    }

    @SneakyThrows
    @Step
    public  static String getAddressId()
    {
        String str=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_ADDRESS_ID);
        while (rs.next()) {
            str = rs.getString("id");
        }
        return str;
    }
    @SneakyThrows
    @Step
    public static String getAddressMapping()
    {

        String str=null;
        String addressId=getAddressId();
        ResultSet rs=Lazycard_MySQL_DBAccessObject.selectFromMySqlDb("select id from lazycardAddressMapping where id='"+addressId+"';");
        while (rs.next()) {
            str = rs.getString("id");
        }
        return str;
    }
    @SneakyThrows
    @Step
    public static String getPhysicalCardOrderId()
    {

        String str=null;
        ResultSet rs=Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(PHYSICAL_CARD_ORDER_ID);
        while (rs.next()){
        str=rs.getString("id");
        }
        return str;
    }
    @SneakyThrows
    @Step
     public static int verifyIsKycAddress(String isKycAddress){
        int n=0;
        ResultSet rs=Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_ISKYC_ADDRESS);
        while (rs.next())
            //  s=rs.getString("contactLessTransactionEnabled");
            n=rs.getInt(isKycAddress);
        return n;
     }


    @SneakyThrows
    @Step
    public  static void updateStatus()
    {
        log.info("Query{}",SET_PHYSICAL_CARD_REQUESTED);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb(SET_PHYSICAL_CARD_REQUESTED);
    }
    @SneakyThrows
    @Step
    public  static void deleteAddress()
    {
        String addressId=getAddressId();
        Lazycard_MySQL_DBAccessObject.deleteOnMySqlDb("DELETE from address where id='"+addressId+"';");
    }
    @SneakyThrows
    @Step
    public  static void deletePhysicalCardOrder()
    {
        String physicalOrderId=getPhysicalCardOrderId();
        if (physicalOrderId != null) {
            Lazycard_MySQL_DBAccessObject.deleteOnMySqlDb("DELETE from lazycardPhysicalOrders where id='" + physicalOrderId + "';");
        }
    }
    @SneakyThrows
    @Step
    public  static void deleteAddressMapping()
    {
        String addressMappingId=getAddressMapping();
        Lazycard_MySQL_DBAccessObject.deleteOnMySqlDb("DELETE from address where id='"+addressMappingId+"';");
    }
    @SneakyThrows
    @Step
    public  static String getPhysicalCardStatus(String physicalCardRequested)
    {
        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_CARD_REQUEST_STATUS);
        while (rs.next())

            s=rs.getString(physicalCardRequested);
        return s;
    }

    @SneakyThrows
    @Step
    public static void reSetCardPreference()
    {
        log.info("Query{}",SET_CARD_PREF_ONLINE_NOTALLOWED);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb(SET_CARD_PREF_ONLINE_NOTALLOWED);
    }
    public String getUserUuidByNumber()
    {
        return null;
    }

    @SneakyThrows
    @Step
    public  static String getCarpreference(String preference)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_CARD_PREFERENCE.replace("$",preference));
        while (rs.next())
            s=rs.getString("contactLessTransactionEnabled");
        return s;
    }
    @SneakyThrows
    @Step
    public static void blockUnBlockLazyPayUser(String uuid,String control)
    {
        log.info("Query{}",SET_CARD_PREF_ONLINE_NOTALLOWED);
        String query=new String();
        if (control.equalsIgnoreCase("block"))
            query=BLOCK_USER_QUERY.replace("$1","1").replace("$2",uuid);
        else if(control.equalsIgnoreCase("unblock"))
            query=BLOCK_USER_QUERY.replace("$1","0").replace("$2",uuid);
        log.info("Query for block/unblock lazypay user{}"+query);
        Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(query);
    }

    @SneakyThrows
    @Step
    public static void updateCardStatusForAutoOrder() {
        log.info("Query{}", SET_CARD_PREF_TO_ON_BOARDING_STATE);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb(SET_CARD_PREF_TO_ON_BOARDING_STATE);
        log.info("Query{}", DELETE_AUTO_CARD_ORDER);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb(DELETE_AUTO_CARD_ORDER);
        deletePhysicalCardOrder();
        deleteAutoCardOrder();
    }

    @SneakyThrows
    @Step
    private static void deleteAutoCardOrder() {
        log.info("Query{}", DELETE_AUTO_CARD_ORDERS);
        Lazycard_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_AUTO_CARD_ORDERS);
    }

    @SneakyThrows
    @Step
    public static String getAutoCardIdStatus() {
        String val = null;
        log.info("Query{}", GET_AUTO_ORDER_CARD_ID);
        ResultSet rs = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_AUTO_ORDER_CARD_ID);
        while (rs.next())
            val = rs.getString("id");
        return val;
    }

    @SneakyThrows
    @Step
    private static String getNextFireTimeData() {
        String val = null;
        String id = getAutoCardIdStatus();
        ResultSet rs = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb("SELECT NEXT_FIRE_TIME FROM lazycard.QRTZ_TRIGGERS WHERE TRIGGER_NAME='CARD_ORDER_" + id + "';");
        while (rs.next())
            val = rs.getString("NEXT_FIRE_TIME");
        return val;
    }


    public static boolean validateFireTime() {
        long fireTime = Long.parseLong(getNextFireTimeData());
        long currentTimestamp = System.currentTimeMillis();
        long updatedTimeStamp = currentTimestamp + (48 * 60 * 60 * 1000);
        log.info("fireTime: " + fireTime + " updatedTime: " + updatedTimeStamp);
        long diff = updatedTimeStamp - fireTime;
        log.info("Current diff in millis: " + diff);
        return diff < 60000 ? true : false;
    }

    @SneakyThrows
    @Step
    public String updateNextFireTimeData() {
        String val = null;
        String id = getAutoCardIdStatus();
        long updateFireTime = (System.currentTimeMillis() + 10000);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb("UPDATE lazycard.QRTZ_TRIGGERS SET NEXT_FIRE_TIME=" + updateFireTime + " WHERE TRIGGER_NAME='CARD_ORDER_" + id + "';");
        Thread.sleep(20000);
        //wait(15000);
        ResultSet resultSet = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb("SELECT NEXT_FIRE_TIME FROM lazycard.QRTZ_TRIGGERS WHERE TRIGGER_NAME='CARD_ORDER_" + id + "';");
        log.info("resultSet data : " + (resultSet == null));
        return val;

    }

    @SneakyThrows
    @Step
    public static boolean checkAutoTriggerStatus() {
        String triggerStatus = null;
        String id = getAutoCardIdStatus();
        System.out.println("id is :" + id);
        log.info("auto trigger id is :" + id);
        ResultSet status = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb("SELECT status FROM lazycard.auto_card_orders WHERE id=" + id + ";");
        System.out.println("status is :" + status);
        while (status.next())
            triggerStatus = status.getString("status");
        log.info("status is : " + triggerStatus);
        ResultSet status2 = Lazycard_MySQL_DBAccessObject.selectFromMySqlDb("SELECT status, auto_trigger FROM lazycard.auto_card_orders WHERE id=" + id + ";");
        return triggerStatus.equals("SUCCESS") ? true : false;
    }


    public String issueNewCard() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String cardReplacementRequest = new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid", NEO_CARD_REPLACEMENT_UUID).replace("flag", BLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, cardReplacementRequest);
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid", SupportApiData.NEO_CARD_REPLACEMENT_UUID);
        CardControlPojo cardControlPojo = cardreplacement.post(pathparam, headerDetails);
        return cardControlPojo.result;
    }

}
