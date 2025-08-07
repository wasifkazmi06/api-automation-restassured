package vaultTM;

import api.vaultTM.BalanceCache;
import api.vaultTM.UserAccount;
import com.opencsv.CSVReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojos.vaultTM.BalanceCachePojo;
import pojos.vaultTM.UserAccountPojo;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccountTest {

    UserAccount userAccount = new UserAccount();
    UserAccountPojo userAccountPojo = new UserAccountPojo();
    public BalanceCache balanceCache = new BalanceCache();
    public BalanceCachePojo balanceCachePojo = new BalanceCachePojo();
    static String csvFile = "/Users/wasif_kazmi/Documents/Dostana33_AccountTest_3.csv";
    String availableLimit= "0.00";
    DecimalFormat df = new DecimalFormat("#.##");
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    String accountId="LP-cf6cb1c5abfc4ccab73e1ac42ffb891f";
    String lpOutStanding="-2276";
    String revolveOs="7765";
    String creditLimit="19100";

    public UserAccountTest() throws Exception {
    }

    @DataProvider(name = "testData")
    private static Object[][] readCsv() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile), ',');
        List<String[]> csvData = csvReader.readAll();
        Object[][] csvDataObject = new Object[csvData.size()][2];
        for (int i = 0; i < csvData.size(); i++) {
            csvDataObject[i] = csvData.get(i);
        }
        return csvDataObject;
    }


    @Test(dataProvider = "testData")
    public void validateUserMigratedBalances(String accountId, String lpOutStanding, String revolveOs, String creditLimit) throws NullPointerException {
   /* @Test
    public void validateUserMigrated() throws NullPointerException {*/

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        userAccountPojo = userAccount.get(queryParamDetails, headerDetails, accountId);

        if(Double.valueOf(lpOutStanding)<0)
        {
            Assert.assertEquals(userAccountPojo.lpChargedBalances.amount, Double.valueOf("0.0"), "LP outstanding amount mismatch!");

        }else {
            Assert.assertEquals(userAccountPojo.lpChargedBalances.amount, Double.valueOf(lpOutStanding), "LP outstanding amount mismatch!");
        }

        if(userAccountPojo.salesCharged!=null && (userAccountPojo.r2ePrincipalCharged!=null || userAccountPojo.r2eInterestCharged!=null))
        {
            Double totalExpectedOutstanding = Double.valueOf(userAccountPojo.salesCharged.amount) +
                    Double.valueOf(userAccountPojo.r2ePrincipalCharged.amount) +
                    Double.valueOf(userAccountPojo.r2eInterestCharged.amount);
            Assert.assertEquals(totalExpectedOutstanding, Double.valueOf(lpOutStanding), "Revolve principal outstanding amount mismatch!");
        }else if(userAccountPojo.salesCharged!=null && userAccountPojo.r2ePrincipalCharged==null && userAccountPojo.r2eInterestCharged==null)
        {
            Assert.assertEquals(userAccountPojo.salesCharged.amount, Double.valueOf(lpOutStanding), "Revolve principal outstanding amount mismatch!");
        }else if(userAccountPojo.salesCharged==null && userAccountPojo.r2ePrincipalCharged==null && userAccountPojo.r2eInterestCharged==null)
        {
            if(userAccountPojo.deposit!=null) {
                Assert.assertEquals(userAccountPojo.deposit.amount, Double.valueOf(lpOutStanding), "Deposit amount mismatch!");
            }else {
                Assert.assertEquals(lpOutStanding, "0", "Deposit amount mismatch!");
            }
        }


        if(userAccountPojo.revolvePrincipalAuth!=null)
        {        Assert.assertEquals(userAccountPojo.revolvePrincipalAuth.amount, Double.valueOf(revolveOs), "Revolve principal outstanding amount mismatch!");
        }else
            {
                Assert.assertNull(userAccountPojo.revolvePrincipalAuth, "Revolve principal outstanding amount mismatch!");
            }

        availableLimit = decfor.format((Double.parseDouble(creditLimit) - Double.parseDouble(lpOutStanding) - Double.parseDouble(revolveOs))*-1);

        Assert.assertEquals(userAccountPojo.defaultCommitted.amount, Double.valueOf(availableLimit) , "Available balance mismatch!");
       // Assert.assertTrue( availableLimit==userAccountPojo.defaultCommitted.amount,"incorrect");

        if(userAccountPojo.deposit!=null){
            Assert.assertEquals(userAccountPojo.deposit.amount, Double.valueOf(lpOutStanding), "Revolve principal outstanding amount mismatch!");
        }

        Assert.assertNull(userAccountPojo.overdueInterestFeePenaltyUnpaid, "The overdueInterestFeePenaltyUnpaid bucket is not 0!");
        Assert.assertNull(userAccountPojo.lateFeePenaltyUnpaid, "The lateFeePenaltyUnpaid bucket is not 0!");
        Assert.assertNull(userAccountPojo.dpdCount, "The dpdCount bucket is not 0!");
        Assert.assertNull(userAccountPojo.salesBilled, "The salesBilled bucket is not 0!");
        Assert.assertNull(userAccountPojo.salesUnpaid, "The salesUnpaid bucket is not 0!");

        if(userAccountPojo.lpBilledBalances!=null){
            Assert.assertEquals(userAccountPojo.lpBilledBalances.amount, Double.valueOf("0.0"), "Revolve principal outstanding amount mismatch!");
        }

        if(userAccountPojo.lpUnpaidBalances!=null){
            Assert.assertEquals(userAccountPojo.lpUnpaidBalances.amount, Double.valueOf("0.0"), "Revolve principal outstanding amount mismatch!");
        }

        Assert.assertNull(userAccountPojo.r2ePrincipalBilled, "The r2ePrincipalBilled bucket is not 0!");
        Assert.assertNull(userAccountPojo.r2ePrincipalUnpaid, "The r2ePrincipalUnpaid bucket is not 0!");
        Assert.assertNull(userAccountPojo.r2eInterestBilled, "The r2eInterestBilled bucket is not 0!");
        Assert.assertNull(userAccountPojo.r2eInterestUnpaid, "The r2eInterestUnpaid bucket is not 0!");
        Assert.assertNull(userAccountPojo.loanBlocked, "The loanBlocked bucket is not 0!");

    }


//    @Test(dataProvider = "testData")
//    public void validateUserMigratedCache(String accountId, String lpOutStanding, String revolveOs, String creditLimit) {
//
    @Test
    public void validateUserMigratedCache() throws NullPointerException {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        balanceCachePojo = balanceCache.get(queryParamDetails, headerDetails, accountId);
        //balanceCachePojo = balanceCache.get(queryParamDetails, headerDetails, "LP-004d4b15fe7a4459bd2f1ba329f45fd0");

        availableLimit = decfor.format((Double.parseDouble(creditLimit) - Double.parseDouble(lpOutStanding) - Double.parseDouble(revolveOs))*-1);

        Assert.assertEquals(balanceCachePojo.defaultCommitted.amount, Double.valueOf(availableLimit), "Credit limit mismatch!");
        Assert.assertEquals(balanceCachePojo.defaultPendingOutgoing.amount, Double.valueOf("0.0"), "Credit limit mismatch!");
        Assert.assertEquals(balanceCachePojo.defaultPendingIncoming.amount, Double.valueOf("0.0"), "Credit limit mismatch!");

        Assert.assertNull(balanceCachePojo.dpdCount, "The dpdCount bucket is not 0!");
        //Assert.assertNull(balanceCachePojo.revolveLoanEmiPrincipalCharged, "The revolveLoanEmiPrincipalCharged bucket is not 0!");
        Assert.assertNull(balanceCachePojo.revolveLoanEmiPrincipalBilled, "The revolveLoanEmiPrincipalBilled bucket is not 0!");
        Assert.assertNull(balanceCachePojo.revolveLoanEmiInterestBilled, "The revolveLoanEmiInterestBilled bucket is not 0!");
        //Assert.assertNull(balanceCachePojo.revolveLoanEmiInterestCharged, "The revolveLoanEmiInterestCharged bucket is not 0!");
        Assert.assertNull(balanceCachePojo.revolveLoanEmiPrincipalUnpaid, "The revolveLoanEmiInterestCharged bucket is not 0!");
        Assert.assertNull(balanceCachePojo.revolveLoanEmiInterestUnpaid, "The revolveLoanEmiInterestCharged bucket is not 0!");

    }

    @Test(dataProvider = "testData")
    public void validateRaceCondition(String accountId) throws NullPointerException {
   /* @Test
    public void validateUserMigrated() throws NullPointerException {*/

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        userAccountPojo = userAccount.get(queryParamDetails, headerDetails, accountId);
        if (userAccountPojo.dpdCount != null) {
            Assert.assertTrue(userAccountPojo.dpdCount.amount == 0, "");
        }
        if (userAccountPojo.salesCharged != null) {
            Assert.assertTrue(userAccountPojo.salesCharged.amount >= 0, "");
        }
        if (userAccountPojo.salesBilled != null) {
            Assert.assertTrue(userAccountPojo.salesBilled.amount >= 0, "");
        }
        if (userAccountPojo.salesUnpaid != null) {
            Assert.assertTrue(userAccountPojo.salesUnpaid.amount >= 0, "");
        }
        if (userAccountPojo.r2ePrincipalCharged != null) {
            Assert.assertTrue(userAccountPojo.r2ePrincipalCharged.amount >= 0, "");
        }
        if (userAccountPojo.r2ePrincipalBilled != null) {
            Assert.assertTrue(userAccountPojo.r2ePrincipalBilled.amount >= 0, "");
        }
        if (userAccountPojo.r2ePrincipalUnpaid != null) {
            Assert.assertTrue(userAccountPojo.r2ePrincipalUnpaid.amount >= 0, "");
        }
        if (userAccountPojo.r2eInterestCharged != null) {
            Assert.assertTrue(userAccountPojo.r2eInterestCharged.amount >= 0, "");
        }
        if (userAccountPojo.r2eInterestBilled != null) {
            Assert.assertTrue(userAccountPojo.r2eInterestBilled.amount >= 0, "");
        }
        if (userAccountPojo.r2eInterestUnpaid != null) {
            Assert.assertTrue(userAccountPojo.r2eInterestUnpaid.amount >= 0, "");
        }
        if (userAccountPojo.lpBilledBalances != null) {
            Assert.assertTrue(userAccountPojo.lpBilledBalances.amount >= 0, "");
        }
        if (userAccountPojo.lpChargedBalances != null) {
            Assert.assertTrue(userAccountPojo.lpChargedBalances.amount >= 0, "");
        }
        if (userAccountPojo.lpUnpaidBalances != null) {
            Assert.assertTrue(userAccountPojo.lpUnpaidBalances.amount >= 0, "");
        }
    }

        @Test(dataProvider = "testData")
        public void dostana33BalanceCheck(String accountId, String creditlimit) throws NullPointerException {
     /* @Test
        public void validateUserMigrated() throws NullPointerException {*/

            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();

            balanceCachePojo = balanceCache.get(queryParamDetails, headerDetails, accountId);
            Assert.assertEquals(balanceCachePojo.defaultCommitted.amount, Double.valueOf(creditlimit), "Credit limit mismatch!");
        }
}
