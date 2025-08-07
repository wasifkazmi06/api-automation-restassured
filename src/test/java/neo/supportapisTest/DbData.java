package neo.supportapisTest;

import dbUtils.FD_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import dbUtils.Lazycard_MySQL_DBAccessObject;


import java.sql.ResultSet;

@Slf4j
public class DbData {

    private static final String SET_CARD_PREF_ONLINE_NOTALLOWED="UPDATE lazycards SET contactLessTransactionEnabled = 'NOTALLOWED', onlineTransactionEnabled= 'NOTALLOWED',posTransactionEnabled='NOTALLOWED' WHERE lazypayUuid = $ AND status ='ACTIVE';";
    private  static final String GET_CARD_PREFERENCE="SELECT $ FROM lazycards l WHERE lazypayUuid = # AND status ='ACTIVE';";
    private  static final String GET_BLOCKED_CARD_PREFERENCE="SELECT $ FROM lazycards l WHERE lazypayUuid = # AND status ='BLOCKED';";
    private  static final String GET_SECURED_CARD_QUEUE="select $ from sbox_fd_service.user_queue where lp_uuid = #;";
    private  static final String GET_SECURED_CARD_VerifiedUPIId="select $ from sbox_fd_service.user_upi_details where upi_id = '#';";
    private  static final String GET_SECURED_CARD_QUEUECreationData="select $ from sbox_fd_service.user_queue where lp_uuid = # ;";
    private  static final String GET_SECURED_QUEUERecord="select count(*) from sbox_fd_service.user_queue where lp_uuid = $;";

    @SneakyThrows
    @Step
    public static void reSetCardPreference(String uuid)
    {
        log.info("Query{}",SET_CARD_PREF_ONLINE_NOTALLOWED);
        Lazycard_MySQL_DBAccessObject.updateOnMySqlDb(SET_CARD_PREF_ONLINE_NOTALLOWED.replace("$",uuid));
    }
    public String getUserUuidByNumber()
    {
        return null;
    }

    @SneakyThrows
    @Step
    public  static String getCarpreference(String preference,String uuid)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_CARD_PREFERENCE.replace("$",preference).replace("#",uuid));
        while (rs.next())
          //  s=rs.getString("contactLessTransactionEnabled");
            s=rs.getString(preference);
        return s;
    }

    @SneakyThrows
    @Step
    public  static String getBlockedCardStatus(String preference,String uuid)
    {

        String s=null;
        ResultSet rs= Lazycard_MySQL_DBAccessObject.selectFromMySqlDb(GET_BLOCKED_CARD_PREFERENCE.replace("$",preference).replace("#",uuid));
        while (rs.next())
            //  s=rs.getString("contactLessTransactionEnabled");
            s=rs.getString(preference);
        return s;
    }

    @SneakyThrows
    @Step
    public  static int getSecuredCardQueueCount(String preference, String lpUuid)
    {

        int s = 0;
        ResultSet rs= FD_MySQL_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_QUEUE.replace("$",preference).replace("#",lpUuid));
        while (rs.next())
              s= rs.getInt(preference);
        return s;
    }
    @SneakyThrows
    @Step
    public  static int getVerifiedUPIId(String preference, String upiId)
    {

        int nameScore = 0;
        ResultSet rs= FD_MySQL_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_VerifiedUPIId.replace("$",preference).replace("#",upiId));
        while (rs.next())
            nameScore= rs.getInt(preference);
        return nameScore;
    }

    @SneakyThrows
    @Step
    public  static int getUserQueueStatus(String preference, String lpUuid)
    {

        int userStatus = 0;
        ResultSet rs= FD_MySQL_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_QUEUECreationData.replace("$",preference).replace("#",lpUuid));
        while (rs.next())
            userStatus= rs.getInt(preference);
        return userStatus;
    }

}
