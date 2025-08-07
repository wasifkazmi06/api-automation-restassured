package billpay;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;

import java.sql.ResultSet;
public class BillpayTestData {
    public static final String electricity = "ELECTRICITY";
    public static final String mobile_Prepaid = "MOBILE+PREPAID";
    public static final String mobile_Postpaid = "MOBILE+POSTPAID";
    public static final String dth = "DTH";
    public static final String insurance = "INSURANCE";
    public static final String water = "WATER";
    public static final String education = "EDUCATION";
    public static final String fasTag = "FASTAG";
    public static final String lpgGas="LPG+GAS";
    public static final String municiplTaxes="MUNICIPAL+TAXES";

    public static String UserId11 = "67a45bca04eb746b753bc002";
    public  static String mobile="9392377360";
    //9392377360
    public static String Anitha_UserId= "673dc1be233ec14184eea2d8";
    public static String TestUser1="673dc1be233ec14184eea2d8";
    //6000000272
    public static String TestUser2="66728ea6229bf37a41023e4e";
    //6000000273
    public static String TestUser3="65b8759566894636f3e607dc";
    //6000000274
    public static String TestUser4="65b9e7348fcf8874fc8b3bc5";
    //6000000275
    public static String TestUser5="646c9b6cb2bfa700016c7cee";
    //6000000362
    public static String TestUser6="6645eebd73b1da23be62e8fc";


    public static String permanentDeactivated_UserId ="66668cefd2050413d2118003";
    public static String BBPS_PRODUCT_NOT_ELIGIBLE="636ce6e602f2bc0001068c25";
    public static String UserId1= "659cf93a996cc6290157cd4a";



    public static final String prepaidJioBillerid="JioPREPAID";
    public static final String prepaidBsnlBillerid="BSNLPREPAID";
    public static final String prepaidIdeaBillerid="IDEAPREPAID";
    public static final String prepaidMtnlBillerid="MTNLPREPAID";
    public static final String prepaidAiretelBillerid="AirtelPREPAID";

    public static final String electricityBillerId = "BSESRAJPLDEL01";
    public static final String dthBillerid="TATASKY00NAT01";
    public static final String billerId1 = "DHBVN0000HAR01";
    public static final String fastagBillerID = "TOLL00000NAT72";
    public static final String waterBillerID="MCC000000KAR01";
    public static final String gasBillerID="HPCL00000NAT01";
    public static final String mobilePostpaidBillerId="JIO000000NAT02";
    public static final String municalTaxesBillerid="VASA00000THAUC";
    public static final String educationBillerid="EDU004171GUJ01";
    public static final String gasBillerid="VGL000000GUJ01";
    public static final String insuranceBillerid="ZUNO00000NATEN";

    public static final String CA_Number = "111111111";
    public static final String Mobile_Number="9999999991";
    public static final String postpaidMobile_Number="9807654300";
    public static final String Account_Number="B16LB210880";
    public static final String Vehicle_Registration_Number = "AT39QT1878";
    public static final String Water_Customer_ID="1111111";

    public static final String electricitySearchString="hot";
    public static final String waterSearchString="water1";
    public static final String insuranceSearchString="Care+Health+Insurance";
    public static final String mPrepaidSearchString="Vi";
    public static final String mPostpaidSearchString="Airtel";
    public static final String dthSearchString="TATA+SKY+DTH";
    public static final String educationSearchString="Zenith+School";
    public static final String fastagSearchString="IDBI+Bank+FASTag";
    public static final String gasSearchString="Torrent+Gas";
    public static final String municipalTaxesSearchString="Valpoi+Municipal+council";
    public static final int dthBillid=4152864;
    public static final int electricityBillid=4148398;
    public static final int fastagBillid=4153510;
    public static final int mPrepaidBillid=4148533;
    public static final int mPostpaidBillid=4153644;
    protected static String PMobile_Number="9392377360";
    protected static String AMobile_Number="7070707070";
    protected static String CircleRefID="10";
    protected static String bsnlCircleRefID="Andaman";
    protected static String OperatorCode="JR";
    protected static String AOperatorCode="AT";
    protected static String planamount="10";
    protected static String App="Android";
    protected static String Imei="55fcc8dc25ab9062";
    protected static String InitiatingChannel= "MOB";
    protected static String Ip= "0.0.0.0";
    protected static String Os="android";
    protected static String SourceSystem="MBE";
    protected static String customerPhoneNumber="9392377360";
    protected static String customerinvalidPhoneNumber="939237736034";

    protected static String airteloperatorCode="AT";
    protected static String jiooperatorCode="JR";
    protected static String vodaphoneoperatorCode="VF";
    protected static String mtnloperatorCode="MT";
    protected static String bsnloperatorCode="BL";
    protected static String ideaoperatorCode="IC";

    protected static String circleRefId="25";
    protected static String MTNLCircleRefId="21";
    protected static String payAgainConsent="true";
    protected static String payAgainConsent1="false";




    @SneakyThrows
    @DataProvider(name = "user-id")
    public  Object[][] getUserIdsForTransactions()
    {
        return new Object[][] {{UserId11}, {TestUser1}, {TestUser2},
                {TestUser2}, {TestUser3}, {TestUser4}, {TestUser5}, {TestUser6}};
    }

    @SneakyThrows
    @DataProvider(name = "PrepaidBillerIds")
    public  Object[][] getPrepaidBillerIdsforGenerateRefId() {

        return new Object[][]
                {{prepaidJioBillerid},{prepaidBsnlBillerid},{prepaidAiretelBillerid},{prepaidMtnlBillerid},{prepaidIdeaBillerid}};

    }
    @SneakyThrows
    @DataProvider(name = "Categories")
    public  Object[][] getCategories() {

        return new Object[][]
                {{electricity},{mobile_Prepaid},{mobile_Postpaid},{education},{insurance},{water},{lpgGas},{dth},{fasTag},{municiplTaxes}};

    }
    @SneakyThrows
    @DataProvider(name = "SearchBillers")
    public  Object[][] getSearchBillers() {

        return new Object[][]
                {{electricitySearchString},{mPostpaidSearchString},{mPrepaidSearchString},{educationSearchString},{insuranceSearchString},{waterSearchString},{gasSearchString},{dthSearchString},{fastagSearchString},{municipalTaxesSearchString}};

    }

    @SneakyThrows
    @DataProvider(name = "BillerDetails")
    public  Object[][] getBillerDetails() {

        return new Object[][]
                {{electricityBillerId},{mobilePostpaidBillerId},{prepaidMtnlBillerid},{educationBillerid},{gasBillerid},{waterBillerID},{dthBillerid},{insuranceBillerid},{fastagBillerID},{municalTaxesBillerid}};
    }
    @SneakyThrows
    @DataProvider(name = "PrepaidOperators")
    public  Object[][] getPrepaidOperators() {

        return new Object[][]
                {{airteloperatorCode},{vodaphoneoperatorCode},{mtnloperatorCode},{jiooperatorCode}};
    }

}