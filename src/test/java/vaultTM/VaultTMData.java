package vaultTM;

import dbUtils.Lazypay_MySQLSsh_DBAccessObject;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import java.sql.ResultSet;

public class VaultTMData {

    public static final String expectedInternalAccountStatus="ACCOUNT_STATUS_OPEN";
    public static final String expectedClosedAccountStatus="ACCOUNT_STATUS_CLOSED";
    public static final String productId="dostana_bnpl";
    public static final String scheduleMigrationType = "SCHEDULE_MIGRATION_TYPE_RECREATE_ALL_SCHEDULES_AND_GROUPS";

    private static String SELECT_ACCOUNT_DETAILS="select * from lazypay.account where user_id in (select userId from lazypay.users where mobile = '$');";

    private static String SELECT_COF_LOAN_DETAILS="SELECT * FROM griphook_db.loan_account WHERE bnpl_account_id in " +
            "(SELECT account_id FROM lazypay.account where user_id in (select userId from lazypay.users where mobile = '$')) and status = 'ACTIVE' order by id desc;";

    @SneakyThrows
    public static ResultSet getBNPLAccountData(String mobile) {

        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_ACCOUNT_DETAILS.replace("$", mobile));
    }

    @SneakyThrows
    public static ResultSet getCOFAccountData(String mobile) {

        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_COF_LOAN_DETAILS.replace("$", mobile));
    }
}
