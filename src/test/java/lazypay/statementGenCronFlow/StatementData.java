package lazypay.statementGenCronFlow;

import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import java.sql.ResultSet;

@Slf4j
public class StatementData {

    private static String SELECT_USER_DETAILS="select * from lazypay.users where mobile = $;";
    private static String SELECT_DEBIT_AMOUNT="select Round(sum(amount),2) from lazypay.transactions where debit  in (select uuid from lazypay.users where mobile = $) and status = 'SUCCESS';";
    private static String SELECT_CREDIT_AMOUNT="select Round(sum(amount),2) from lazypay.transactions where credit  in (select uuid from lazypay.users where mobile = $) and status = 'SUCCESS';";
    private static String SELECT_USER_STATEMENT = "select * from lazypay.statements where uuid in (select uuid from lazypay.users where mobile =$) order by stmtCreateDt desc limit 1;";
    private static String SELECT_TRANSACTIONS_STATEMENT = "select id,statementId,type,status from lazypay.transactions where credit  in (select uuid from lazypay.users where mobile in ($)) or debit in (select uuid from lazypay.users where mobile in ($)) order by statementId desc;";
    private static String SELECT_AUTOREPAY_STATEMENT="select * from lazypay.autoRepayStatement where stmtId ='$';";
    private static String SELECT_STATEMENT_SCHEDULER="select * from lazypay.stmtScheduler where stmtId ='$';";
    private static String SELECT_FIRST_STATEMENT="select * from lazypay.statements where uuid in (select uuid from lazypay.users where mobile =$) and status = 'PENDING' order by stmtCreateDt limit 1;";

    private static String DELETE_STATEMENT_SCHEDULER="delete from lazypay.stmtScheduler where uuid in (select uuid from lazypay.users where mobile in ($));";
    private static String DELETE_STATEMENT="delete from lazypay.statements where uuid in (select uuid from lazypay.users where mobile in ($));";
    private static String DELETE_TRANSACTION="delete from lazypay.transactions where debit in (select uuid from lazypay.users where mobile in ($)) or credit  in (select uuid from lazypay.users where mobile in ($));";
    private static String DELETE_USER="delete from lazypay.users where mobile in ($);";

    private static String UPDATE_TRANSACTION="update lazypay.transactions set dateCreated = '#', dateUpdated = '#' where debit in (select uuid from lazypay.users where mobile in ($)) or credit  in (select uuid from lazypay.users where mobile in ($));";

    public static final String REGISTERED_USER1 = "6000000301";
    public static final String REGISTERED_USER2 = "6000000302";
    public static final String REGISTERED_USER3 = "6000000303";
    public static final String REGISTERED_USER4 = "6000000304";
    public static final String REGISTERED_USER5 = "6000000305";
    public static final String REGISTERED_USER6 = "6000000306";
    public static final String REGISTERED_USER7 = "6000000307";
    public static final String REGISTERED_USER8 = "6000000308";
    public static final String REGISTERED_USER9 = "6000000309";
    public static final String REGISTERED_USER10 = "6000000310";

    public static final String ListOfMobiles = "'"+REGISTERED_USER1+"','"+REGISTERED_USER2+"','"+REGISTERED_USER3+"','"+REGISTERED_USER4+"','"+REGISTERED_USER5+"','"+REGISTERED_USER6+"','"+REGISTERED_USER7+"','"+REGISTERED_USER8+"','"+REGISTERED_USER9+"','"+REGISTERED_USER10+"'";
    public static final String ListOfMobilesToUpdate = "'"+REGISTERED_USER1+"','"+REGISTERED_USER2+"','"+REGISTERED_USER3+"','"+REGISTERED_USER4+"','"+REGISTERED_USER5+"','"+REGISTERED_USER6+"','"+REGISTERED_USER9+"','"+REGISTERED_USER10+"'";
    public static final String DumpFilePath = "src/test/resources/lazypay/statementGenCronFlow/LP_StmtAutomation_UsersTxnsStmts_v4.sql";

    @SneakyThrows
    @DataProvider(name = "user-mobile")
        public final static Object[][] getUserMobile()
        {
            return new Object[][] {{REGISTERED_USER1}, {REGISTERED_USER2}, {REGISTERED_USER3}, {REGISTERED_USER4},
                {REGISTERED_USER5}, {REGISTERED_USER6}, {REGISTERED_USER7}, {REGISTERED_USER8}, {REGISTERED_USER9}
                , {REGISTERED_USER10}};
    }


    @SneakyThrows
    public static ResultSet getUserDetail(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_USER_DETAILS.replace("$", mobile));
    }

    @SneakyThrows
    public static ResultSet getCreditAmount(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_CREDIT_AMOUNT.replace("$",mobile));
    }

    @SneakyThrows
    public static ResultSet getDebitAmount(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_DEBIT_AMOUNT.replace("$",mobile));
    }

    @SneakyThrows
    public static ResultSet getTransactionsStatement(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_TRANSACTIONS_STATEMENT.replace("$", mobile));
    }

    @SneakyThrows
    public static ResultSet getUserStatement(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_USER_STATEMENT.replace("$",mobile));
    }

    @SneakyThrows
    public static ResultSet getAutoRepayStatement(String stmtId)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_AUTOREPAY_STATEMENT.replace("$",stmtId));
    }

    @SneakyThrows
    public static ResultSet getStatementScheduler(String stmtId)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_STATEMENT_SCHEDULER.replace("$",stmtId));
    }

    @SneakyThrows
    public static ResultSet getUserFirstStatement(String mobile)
    {
        return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_FIRST_STATEMENT.replace("$",mobile));
    }


    @SneakyThrows
    public static void deleteStatementScheduler(String ListOfMobiles)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_STATEMENT_SCHEDULER.replace("$", ListOfMobiles));
    }

    @SneakyThrows
    public static void deleteStatement(String mobile)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_STATEMENT.replace("$", mobile));
    }

    @SneakyThrows
    public static void deleteTransaction(String mobile)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_TRANSACTION.replace("$", mobile));
    }

    @SneakyThrows
    public static void deleteUser(String mobile)
    {
        Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_USER.replace("$", mobile));
    }

    @SneakyThrows
    public static void loadLPDataDump(String filePath)
    {
        Lazypay_MySQL_DBAccessObject.loadDataDump(filePath);

    }

    @SneakyThrows
    public static void updateTransaction(String mobile, String newDate)
    {
        Lazypay_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_TRANSACTION.replace("#",newDate).replace("$", mobile));

    }

}
