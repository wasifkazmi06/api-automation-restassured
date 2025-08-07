package lazypay.statementGenCronFlow;

import api.lazypay.stmtGenCron.StatementGenCron;
import api.lazypay.stmtGenCron.StatementSenderCron;
import com.google.common.collect.ImmutableSet;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import static lazypay.statementGenCronFlow.StatementData.*;
import util.DateTimeConverter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;

@Slf4j
public class StatementGenerationCronTest {

    private static final Set<String> MOBILE_NOS = ImmutableSet.of(REGISTERED_USER2, REGISTERED_USER7, REGISTERED_USER8, REGISTERED_USER9);
    public static StatementGenCron statementGenCron;
    public static StatementSenderCron statementSenderCron;
    public static ResultSet rs;
    public static LocalDate currentDate = LocalDate.now();
    public static LocalDate stmtGenerationDate;
    public static HashMap<String, Map<String, String>> statementCronDataMap = new LinkedHashMap<>();
    public static LocalDate newDate;
    //public static LocalDate nxtStmtDateExpected;


    static {
        try {
            statementGenCron = new StatementGenCron();
            statementSenderCron = new StatementSenderCron();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass()
    public void setupData() {
        //Delete old data for the test users
        log.info("Deleting user's stmtSchedulers");
        StatementData.deleteStatementScheduler(ListOfMobiles);
        log.info("Deleting user's statements");
        StatementData.deleteStatement(ListOfMobiles);
        log.info("Deleting user's transactions");
        StatementData.deleteTransaction(ListOfMobiles);
        log.info("Deleting users");
        StatementData.deleteUser(ListOfMobiles);
        //Dump test data
        log.info("Uploading statement data");
        StatementData.loadLPDataDump(DumpFilePath);
        //Update test data as per current date
        if(currentDate.getDayOfMonth() < 16)
        {
            newDate = DateTimeConverter.getLastDateOfMonth("yyyy-MM-dd", currentDate.minusMonths(1)).minusDays(5);
        }
        else
        {
            newDate = DateTimeConverter.getFirstDateOfMonth("yyyy-MM-dd", currentDate).plusDays(5);
        }
        log.info("Updating statement data");
        StatementData.updateTransaction(ListOfMobilesToUpdate, newDate.toString().concat(" 12:35:37"));
    }

    @AfterClass
    public void finishUp() throws SQLException, ClassNotFoundException {
        Lazypay_MySQL_DBAccessObject.closeMySqlDbConnection();
    }

    @SneakyThrows
    public static void insertInStatementCronDataMap(String mobileNo, String field, String value){
        HashMap<String, String> tempMap = new LinkedHashMap<>();
        HashMap<String, String> internalMap = new LinkedHashMap<>();

        if(statementCronDataMap.get(mobileNo) != null)
            tempMap = (HashMap<String, String>) statementCronDataMap.get(mobileNo);

        internalMap.put(field, value);
        internalMap.putAll(tempMap);
        statementCronDataMap.put(mobileNo, internalMap);
    }

    public static String retrieveFromStatementCronDataMap(String mobileNo, String field){
       return statementCronDataMap.get(mobileNo).get(field);
    }

    @SneakyThrows
    @Feature("getInitialLateFeeAmount")
    @Description("To get late fee amount before statement generation for each user.")
    @Test(priority=1, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void getInitialLateFeeAmount(String mobileNo)  {
        double initialLateFeeAmt = 0;

        rs = StatementData.getUserDetail(mobileNo);
        while (rs.next()) {
            initialLateFeeAmt = rs.getDouble("lateFeeAmt") + rs.getDouble("dueLateFeeAmt");
        }

        insertInStatementCronDataMap(mobileNo, "initialLateFeeAmt", Double.toString(initialLateFeeAmt));
        Allure.addAttachment("initialLateFeeAmt", "Late fee amount before statement generation: " +  retrieveFromStatementCronDataMap(mobileNo, "initialLateFeeAmt")  );

    }

    @SneakyThrows
    @Feature("initiateStatementGenerationCron")
    @Description("To run the statement generation cron for all users.")
    @Test(priority=2)
    public void initiateStatementGenerationCron()  {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        LocalDate currentDate = DateTimeConverter.getCurrentDate();

        if (currentDate.getDayOfMonth() < 16)
            stmtGenerationDate = currentDate.withDayOfMonth(1);
        else
            stmtGenerationDate = currentDate.plusDays(16 - currentDate.getDayOfMonth());

        queryParamDetails.put("stmtDate", URLEncoder.encode(stmtGenerationDate.toString().concat(" 00:00:01"), "UTF-8"));
        Response stmtGenCronResponse = statementGenCron.getWithPathPram(queryParamDetails, headerDetails, "0");
        Assert.assertEquals(stmtGenCronResponse.getStatusCode(), 200, "Check the date-time sent in request or if service is down, status code");
    }

    @SneakyThrows
    @Feature("verifyStatementCreatedForCurrentCycle")
    @Description("To verify that statements have been created for current cycle for all eligible users.")
    @Test(priority = 3, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyStatementCreatedForCurrentCycle(String mobileNo)  {
        String stmtId = null;
        double cFwdDue = Double.MIN_VALUE;
        String cycleEndDt = null;
        String statementStatus = null;
        double totalDue = Double.MIN_VALUE;
        String stmtDueDate = null;
        int count = 0;

            while ((stmtId == null) && count < 30) {
                rs = StatementData.getUserStatement(mobileNo);
                if( mobileNo.equals(REGISTERED_USER9) && count > 4) {
                    break;
                }
                while (rs.next()) {
                    stmtId = rs.getString("stmtId");
                    cFwdDue = rs.getDouble("cFwdDue");
                    cycleEndDt = rs.getString("cycleEndDt");
                    statementStatus = rs.getString("status");
                    totalDue = rs.getDouble("totalDue");
                    stmtDueDate = rs.getString("dueDate");
                }
                Thread.sleep(100);
                count++;
            }

        insertInStatementCronDataMap(mobileNo, "stmtId", stmtId);
        insertInStatementCronDataMap(mobileNo, "cFwdDue", Double.toString(cFwdDue));
        insertInStatementCronDataMap(mobileNo, "cycleEndDt", cycleEndDt);
        insertInStatementCronDataMap(mobileNo, "statementStatus", statementStatus);
        insertInStatementCronDataMap(mobileNo, "totalDue", Double.toString(totalDue));
        insertInStatementCronDataMap(mobileNo, "stmtDueDate", stmtDueDate);

        if(mobileNo.equals(REGISTERED_USER9)) {
            Assert.assertNull(stmtId, "Check if statement has not been generated for user,");
            Allure.addAttachment("verifyStatementCreatedForCurrentCycle", "This user has not transacted yet, statement model expected to be null");
            return;
        }
        Assert.assertNotNull(stmtId, "Check if statement has been generated for user,");
        Allure.addAttachment("verifyStatementCreatedForCurrentCycle", "Statement ID generated: " + stmtId);

    }

    @SneakyThrows
    @Feature("verifyLastCycleLpOutstanding")
    @Description("To verify LastCycleLpOutstanding is updated as expected after statement generation.")
    @Test(priority=4, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void verifyLastCycleLpOutstanding(String mobileNo) {

        double lpOutstanding = Double.MIN_VALUE;
        double lastCycleLpOutstanding = Double.MIN_VALUE;
        String nxtStmtDate = null;
        double sumOfDebit = Double.MIN_VALUE;
        double sumOfCredit = Double.MIN_VALUE;
        double lateFeeAmt = Double.MIN_VALUE;
        double dueLateFeeAmt = Double.MIN_VALUE;
        String userDueDate = null;
        String userDueSince = null;
        int count =0;

        while(lastCycleLpOutstanding == Double.MIN_VALUE && count <200) {
            rs = StatementData.getUserDetail(mobileNo);
            while (rs.next()) {
                lpOutstanding = rs.getDouble("lpOutstanding");
                lastCycleLpOutstanding = rs.getDouble("lastCycleLpOutstanding");
                nxtStmtDate = rs.getString("nxtStmtDate");
                lateFeeAmt = rs.getDouble("lateFeeAmt");
                dueLateFeeAmt = rs.getDouble("dueLateFeeAmt");
                userDueDate = rs.getString("dueDate");
                userDueSince = rs.getString("dueSince");
            }
            Thread.sleep(100);
            count++;
        }
        rs = StatementData.getDebitAmount(mobileNo);
        while (rs.next())
            sumOfDebit = rs.getDouble("Round(sum(amount),2)");

        rs = StatementData.getCreditAmount(mobileNo);
        while (rs.next())
            sumOfCredit = rs.getDouble("Round(sum(amount),2)");

        insertInStatementCronDataMap(mobileNo, "sumOfDebit", Double.toString(sumOfDebit));
        insertInStatementCronDataMap(mobileNo, "sumOfCredit", Double.toString(sumOfCredit));
        insertInStatementCronDataMap(mobileNo, "lpOutstanding", Double.toString(lpOutstanding));
        insertInStatementCronDataMap(mobileNo, "lastCycleLpOutstanding", Double.toString(lastCycleLpOutstanding));
        insertInStatementCronDataMap(mobileNo, "nxtStmtDate", nxtStmtDate);
        insertInStatementCronDataMap(mobileNo, "lateFeeAmt", Double.toString(lateFeeAmt));
        insertInStatementCronDataMap(mobileNo, "dueLateFeeAmt", Double.toString(dueLateFeeAmt));
        insertInStatementCronDataMap(mobileNo, "dueDate", userDueDate);
        insertInStatementCronDataMap(mobileNo, "dueSince", userDueSince);

        final DecimalFormat df = new DecimalFormat("0.00");

        Assert.assertEquals(df.format(sumOfDebit - sumOfCredit), df.format(lastCycleLpOutstanding), "1. Check if the statement has been generated\n 2. Check the ledger for user,");
        Allure.addDescription("Sum of all success debit transactions: " + sumOfDebit + "\nSum of all success credit transactions: "+ sumOfCredit + "\nThe lastCycleLpOutstanding generated: " + lastCycleLpOutstanding);

    }

    @SneakyThrows
    @Feature("verifyDueLateFeeAndLateFeeAmount")
    @Description("To verify due late fee and late fee amounts after statement generation.")
    @Test(priority = 5, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyDueLateFeeAndLateFeeAmount(String mobileNo) {

        Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "dueLateFeeAmt"), retrieveFromStatementCronDataMap(mobileNo, "initialLateFeeAmt"), "1. Check if the statement has been generated\n 2. Check the LATE_FEE transactions of the user,");
        Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "lateFeeAmt"), "0.0", "1. Check if the statement has been generated\n 2. Check the LATE_FEE transactions of the user,");
        Allure.addAttachment("verifyDueLateFeeAndLateFeeAmount", "Due late fee after statement generation: " + retrieveFromStatementCronDataMap(mobileNo, "dueLateFeeAmt") + "\nLate fee amount before statement generation: " +  retrieveFromStatementCronDataMap(mobileNo, "initialLateFeeAmt") + "\nLate fee after statement expected: 0, actual: "+ retrieveFromStatementCronDataMap(mobileNo, "lateFeeAmt"));
    }

    @SneakyThrows
    @Feature("verifyUserDueDate")
    @Description("To verify user's due date is updated correctly after statement generation.")
    @Test(priority = 6, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyUserDueDate(String mobileNo) {

        if(mobileNo.equals(REGISTERED_USER8) || mobileNo.equals(REGISTERED_USER9)) {
            Assert.assertNull(retrieveFromStatementCronDataMap(mobileNo, "dueDate"), "Check if statement has not been generated for user,");
            Allure.addAttachment("verifyUserDueDate ", "Since this user has already paid or has not made any transaction yet the due date is null.");
            return;
        }
        else
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "dueDate"), stmtGenerationDate.plusDays(2).toString().concat(" 18:30:00"), "Check if statement has been generated for user,");
            Allure.addAttachment("verifyUserDueDate","Actual due date: " + retrieveFromStatementCronDataMap(mobileNo, "dueDate") + ".\nExpected taken as +3 days from latest statement's cycle end date: " + stmtGenerationDate.plusDays(2).toString().concat(" 18:30:00"));
    }

    @SneakyThrows
    @Feature("verifyUserDueSince")
    @Description("To verify user's due since is updated correctly after statement generation.")
    @Test(priority = 7, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyUserDueSince(String mobileNo) {

        String firstStmtId = null;
        String cycleEndDtFirstStmt = null;
        int count = 0;

        while (firstStmtId == null && count < 200) {
            rs = StatementData.getUserFirstStatement(mobileNo);
            while (rs.next()) {
                firstStmtId = rs.getString("stmtId");
                cycleEndDtFirstStmt = rs.getString("cycleEndDt");
            }
            if((mobileNo.equals(REGISTERED_USER9)|| mobileNo.equals(REGISTERED_USER8)) && count > 4) {
                break;
            }
            Thread.sleep(100);
            count++;
        }

        insertInStatementCronDataMap(mobileNo, "firstStmtId", firstStmtId);
        insertInStatementCronDataMap(mobileNo, "cycleEndDtFirstStmt", cycleEndDtFirstStmt);

        if(mobileNo.equals(REGISTERED_USER8) || mobileNo.equals(REGISTERED_USER9)) {
            Assert.assertNull(retrieveFromStatementCronDataMap(mobileNo, "dueSince"), "Check if statement has been generated for user,");
            Allure.addAttachment("verifyUserDueSince ", "Since this user has already paid or has not made any transaction yet the due since is null.");
            return;
        }
        else
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "dueSince"), retrieveFromStatementCronDataMap(mobileNo, "cycleEndDtFirstStmt"), "Check if statement has been generated for user,");
            Allure.addAttachment("verifyUserDueSince","Actual due since: " + retrieveFromStatementCronDataMap(mobileNo, "dueSince") + ".\nExpected taken from earliest pending statement's cycle end date : " + retrieveFromStatementCronDataMap(mobileNo, "cycleEndDtFirstStmt"));
    }

    /* Commenting this as next statement date is not being used as of now
    @SneakyThrows
    @Feature("verifyNextStmtDate")
    @Description("Test case for verifying next statement date")
    @Test(priority = 5, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyNextStmtDate(String mobileNo) throws Exception {
        nxtStmtDateExpected = getNextStatementDateExpected();
        Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "nxtStmtDate"), nxtStmtDateExpected.toString().concat(" 18:30:00"), "check if statement is generated for user");
    }
    */

    @SneakyThrows
    @Feature("verifyStatementIDMappingWithTransaction")
    @Description("To verify statement ID is mapped with all eligible transactions.")
    @Test(priority=8, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void verifyStatementIDMappingWithTransaction(String mobileNo) {
        if(mobileNo.equals(REGISTERED_USER7)|| mobileNo.equals(REGISTERED_USER8)
                || mobileNo.equals(REGISTERED_USER9)) {
            Allure.addAttachment("verifyStatementIDMappingWithTransaction", "No mapping between stmtId and transactions since this user has no transaction in current cycle.");
            return;
        }
        String statementId;
        String type;
        String status;
        String id;

        rs = StatementData.getTransactionsStatement(mobileNo);
        while (rs.next()) {
            statementId = rs.getString("statementId");
            type = rs.getString("type");
            status = rs.getString("status");
            id = rs.getString("id");

            if(!(type.equals("DISPUTE") || type.equals("MAD_CREDIT")
                    || type.equals("REPAY_REFUND")) && status.equals("SUCCESS")) {
                Assert.assertEquals(statementId, retrieveFromStatementCronDataMap(mobileNo, "stmtId"), "Check if statement has been generated for user,");
                Allure.addAttachment("verifyStatementIDMappingWithTransaction", "Actual statement ID mapped to the transaction: " + id + " is: " + statementId + "\nExpected statement ID to be mapped with transaction: " + retrieveFromStatementCronDataMap(mobileNo, "stmtId"));
            }
        }
    }

    @SneakyThrows
    @Feature("verifyStatementStatusForCurrentCycle")
    @Description("To verify status of statement generated.")
    @Test(priority = 9, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyStatementStatusForCurrentCycle(String mobileNo)  {

        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyStatementStatusForCurrentCycle", "Statement not generated in current cycle for this user.");
        }

        else if (mobileNo.equals(REGISTERED_USER8)){
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "statementStatus"), "CLEARED", "Check if statement has been generated for user, status");
            Allure.addAttachment("verifyStatementStatusForCurrentCycle","Actual statement status: " + retrieveFromStatementCronDataMap(mobileNo, "statementStatus") + "\nExpected: CLEARED");
        }
        else {
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "statementStatus"), "PENDING", "Check if statement has been generated for user, status");
            Allure.addAttachment("verifyStatementStatusForCurrentCycle","Actual statement status: " + retrieveFromStatementCronDataMap(mobileNo, "statementStatus") + "\nExpected: PENDING");
        }
    }


    @SneakyThrows
    @Feature("verifyTotalDueAmountFromStatement")
    @Description("To verify total due of the statement generated.")
    @Test(priority = 10, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyTotalDueAmountFromStatement(String mobileNo) {
        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyTotalDueAmountFromStatement", "Statement not generated in current cycle for this user.");
            return;
        }
        Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "totalDue") ,retrieveFromStatementCronDataMap(mobileNo, "lastCycleLpOutstanding") , "Check if statement has been generated for user, totalDue");
        Allure.addAttachment("verifyTotalDueAmountFromStatement","Actual totalDue: " + retrieveFromStatementCronDataMap(mobileNo, "totalDue") + "\nExpected totalDue to be same as lastCycleLpOutstanding: "+ retrieveFromStatementCronDataMap(mobileNo, "lastCycleLpOutstanding") );

    }

    @SneakyThrows
    @Feature("verifyCycleEndDateFromStatement")
    @Description("To verify cycle end date of the statement generated.")
    @Test(priority = 11, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyCycleEndDateFromStatement(String mobileNo) {
        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyCycleEndDateFromStatement", "Statement not generated in current cycle for this user.");
            return;
        }
        Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "cycleEndDt"), stmtGenerationDate.plusDays(-1).toString().concat(" 18:30:00"), "Check if statement has been generated for user, cycleEndDt");
        Allure.addAttachment("verifyCycleEndDateFromStatement", "Actual cycleEndDt: " + retrieveFromStatementCronDataMap(mobileNo, "cycleEndDt") + "\nExpected cycleEndDt: "+ stmtGenerationDate.plusDays(-1).toString().concat(" 18:30:00"));
    }


    @SneakyThrows
    @Feature("verifyCarryFwdDueFromStatement")
    @Description("To verify the carry forward due of the statement generated.")
    @Test(priority = 12, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyCarryFwdDueFromStatement(String mobileNo) {

        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyCarryFwdDueFromStatement", "Statement not generated in current cycle for this user.");
        }
        else if (mobileNo.equals(REGISTERED_USER7)) {
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "cFwdDue"), retrieveFromStatementCronDataMap(mobileNo, "lpOutstanding"), "Check if statement has been generated for the correct cycle, cFwdDue");
            Allure.addAttachment("verifyCarryFwdDueFromStatement","Actual cFwdDue: " + retrieveFromStatementCronDataMap(mobileNo, "cFwdDue") + "\nExpected cFwdDue: " + retrieveFromStatementCronDataMap(mobileNo, "lpOutstanding"));
        }
        else {
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "cFwdDue"), retrieveFromStatementCronDataMap(mobileNo, "dueLateFeeAmt"), "Check if statement has been generated for the correct cycle, cFwdDue");
            Allure.addAttachment("verifyCarryFwdDueFromStatement","Actual cFwdDue: " + retrieveFromStatementCronDataMap(mobileNo, "cFwdDue") + "\nExpected cFwdDue: " + retrieveFromStatementCronDataMap(mobileNo, "dueLateFeeAmt"));
        }
    }

    @SneakyThrows
    @Feature("verifyDueDateOfStatement")
    @Description("To verify the statement due date is updated correctly after statement generation.")
    @Test(priority = 13, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public void verifyDueDateOfStatement(String mobileNo) {
        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyDueDateOfStatement", "Statement not generated in current cycle for this user.");
            return;
        }
        else if(mobileNo.equals(REGISTERED_USER8)) {
            Assert.assertNull(retrieveFromStatementCronDataMap(mobileNo, "stmtDueDate"), "Check if statement has been generated for the correct cycle, dueDate");
            Allure.addAttachment("verifyDueDateOfStatement", "Actual statement due date: " + retrieveFromStatementCronDataMap(mobileNo, "stmtDueDate") + "\nExpected: null as user has not outstanding");
        }
        else
            Assert.assertEquals(retrieveFromStatementCronDataMap(mobileNo, "stmtDueDate"), stmtGenerationDate.plusDays(2).toString().concat(" 18:30:00"), "Check if statement has been generated for the correct cycle, dueDate");
            Allure.addAttachment("verifyDueDateOfStatement", "Actual statement due date: " + retrieveFromStatementCronDataMap(mobileNo, "stmtDueDate") + ".\nExpected taken as +3 days from latest statement's cycle end date: " + stmtGenerationDate.plusDays(2).toString().concat(" 18:30:00"));

    }

    @SneakyThrows
    @Feature("verifyAutoRepayStatementCreated")
    @Description("To verify auto repay statement is created for user with NACH setup.")
    @Test(priority = 14, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void verifyAutoRepayStatementCreated(String mobileNo) {

        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyAutoRepayStatementCreated", "Statement not generated in current cycle for this user.");
            return;
        }
        String autoRepayStatus = null;
        int count = 0;

        while(autoRepayStatus == null && count <200) {
        rs = StatementData.getAutoRepayStatement(retrieveFromStatementCronDataMap(mobileNo, "stmtId"));
            if(MOBILE_NOS.contains(mobileNo)&& count > 4){
            break;
            }
            while (rs.next()) {
                autoRepayStatus = rs.getString("status");
            }
            Thread.sleep(100);
            count++;
        }

        if(MOBILE_NOS.contains(mobileNo)) {
            Assert.assertNull(autoRepayStatus,  "Check if the NACH has been setup the user by mistake,");
            Allure.addAttachment("verifyAutoRepayStatementCreated", "Expected the autoRepayStatus to be null since these user don't have NACH setup");
            return;
        }
        Assert.assertEquals(autoRepayStatus, "CREATED", "1. Check if statement has been generated for the user.\n2. Check the NACH setup status for the user.\n3. Check if the platform service is up.\n4. Check if the collection service is up.\n5. Check if process NACH cron has been executed by mistake.\n");
        Allure.addAttachment("verifyAutoRepayStatementCreated", "Actual autoRepayStatus: " + autoRepayStatus + "\nExpected autoRepayStatus: CREATED");

    }

    @SneakyThrows
    @Feature("verifyUserStatementCommunicationScheduled")
    @Description("To verify if the communication is scheduled for PENDING statements.")
    @Test(priority = 15, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void verifyUserStatementCommunicationScheduled(String mobileNo) {
        int isDelivered = Integer.MIN_VALUE;

        if (mobileNo.equals(REGISTERED_USER9)){
            Allure.addAttachment("verifyUserStatementCommunicationScheduled", "Statement not generated in current cycle for this user.");
            return;
        }

        rs = StatementData.getStatementScheduler(retrieveFromStatementCronDataMap(mobileNo, "stmtId"));
        while (rs.next()) {
            isDelivered = rs.getInt("isDelivered");
        }
        if( mobileNo.equals(REGISTERED_USER8)) {
            Assert.assertEquals(isDelivered, Integer.MIN_VALUE,"Check if statement has been generated for the user, isDelivered");
            Allure.addAttachment("verifyUserStatementCommunicationScheduled",  "Actual value of the isDelivered flag: "+ isDelivered + "\nExpected value of the isDelivered flag: " +Integer.MIN_VALUE);
        }
        else {
            Assert.assertEquals(isDelivered, 0, "check if statement is generated for user and sender cron is not initiated");
            Allure.addAttachment("verifyUserStatementCommunicationScheduled", "Actual value of the isDelivered flag: " + isDelivered + "\nExpected value of the isDelivered flag: 0");
        }
    }

    @SneakyThrows
    @Feature("sendStatementCronTest")
    @Description("To verify that the send statement cron is executed.")
    @Test(priority=16)
    public static void sendStatementCronTest() {
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        Response stmtSenderCronResponse = statementSenderCron.getWithPathPram(queryParamDetails, headerDetails, "");
        Assert.assertEquals(stmtSenderCronResponse.getStatusCode(), 200, "Check if the server is up and running.");
        Allure.addAttachment("sendStatementCronTest", "Statement sender cron executed: /api/lazypay/cron/sendStatementsOptimised");
    }


    @SneakyThrows
    @Feature("verifySendStatementIsDelivered")
    @Description("Test case for verifying User Statement Communication Delivered")
    @Test(priority = 17, dataProvider = "user-mobile", dataProviderClass = StatementData.class)
    public static void verifySendStatementIsDelivered(String mobileNo) {
        if(mobileNo.equals(REGISTERED_USER9)|| mobileNo.equals(REGISTERED_USER8)){
            Allure.addAttachment("verifyUserStatementCommunicationDelivered", "Statement either not generated in current cycle or statement already CLEARED due to no outstanding for this user.");
            return;
        }

        int isDelivered = Integer.MIN_VALUE;
        int count=0;

        while((isDelivered == 0 || isDelivered ==Integer.MIN_VALUE) && count <200) {

            rs = StatementData.getStatementScheduler(retrieveFromStatementCronDataMap(mobileNo, "stmtId"));
            while (rs.next()) {
            isDelivered = rs.getInt("isDelivered");
            }
        Thread.sleep(100);
        count++;
        }
        Assert.assertEquals(isDelivered, 1, "Check if the secure app service is up and running.");
        Allure.addAttachment("verifyUserStatementCommunicationDelivered","Actual value of the isDelivered flag: " + isDelivered + "\nExpected value of the isDelivered flag: 1");
    }
}


