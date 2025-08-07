package lazypay.userOnboarding;

import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;

@Slf4j
public class UserOnboardingData {

    /**
     * Merchant
     */
    public static final String ACCESS_KEY="CCWHRSH9UULECPLQCFD2";
    public static final String ACCESS_KEY_SUB_MERCHANT_DISABLED="THDQF724VBOKRKK3SPQI";





    private static String SELECT_USER_DETAILS="select * from lazypay.users where mobile = $;";
    private static String SELECT_ACCOUNT="select * from lazypay.account where user_id in (select userId from lazypay.users where mobile = '$');";
    private static String DELETE_USER="delete from lazypay.users where mobile in ('$');";
    private static String DELETE_ACCOUNT="delete from lazypay.account where user_id in (select userId from lazypay.users where mobile = '$');";
    private static String UPDATE_MIGRATING_USER="update lazypay.users set ledgerCustId = '#', userMigrated = & where mobile = '$';";
//    private static String UPDATE_MIGRATING_USER="update lazypay.users set ledgerCustId = null, userMigrated = & where mobile = '$';";

    private static String UPDATE_LEDGER_PRODUCT_ACCOUNT="update lazypay.account set ledger_product_version_id = '#' where user_id in (select userId from lazypay.users where mobile = '$');";



    @SneakyThrows
    public static ResultSet getUserDetail(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_USER_DETAILS.replace("$", mobile));
    }


    @SneakyThrows
    public static void deleteUser(String mobile)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_USER.replace("$", mobile));
    }

    @SneakyThrows
    public static ResultSet getAccountDetail(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_ACCOUNT.replace("$", mobile));
    }


    @SneakyThrows
    public static void deleteAccount(String mobile)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_ACCOUNT.replace("$", mobile));
    }

    @SneakyThrows
    public static void updateMigratingUser(String mobile, String ledgerCustId, String userMigrated)
    {
        if(ledgerCustId.equals("null")){
            Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_MIGRATING_USER.replace("'#'",ledgerCustId).replace("&",userMigrated).replace("$", mobile));
        }
        else
        Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_MIGRATING_USER.replace("#",ledgerCustId).replace("&",userMigrated).replace("$", mobile));

    }

    @SneakyThrows
    public static void updateLedgerProductVersionInAccount(String mobile, String ledgerProductVersionId)
    {

        Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_LEDGER_PRODUCT_ACCOUNT.replace("#",ledgerProductVersionId).replace("$", mobile));

    }


}

