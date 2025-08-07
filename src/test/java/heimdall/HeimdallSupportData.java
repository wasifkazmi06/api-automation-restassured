package heimdall;

import dbUtils.Heimdall_MySQL_DBAccessObject;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class HeimdallSupportData {

    private static final String GET_CUSTOMER_ENTRY = "SELECT # FROM heimdall_db.customer WHERE source_system_code='$'";
    private static final String GET_CUSTOMER_FAILURE_VALIDATION = "SELECT # FROM heimdall_db.validation_failures WHERE request_id='$'";
    private static final String GET_CUSTOMER_REQUEST = "SELECT # FROM heimdall_db.tm_customer WHERE user_id='$'";
    private static final String GET_CUSTOMER_REQUEST_ISNULL = "SELECT # FROM heimdall_db.tm_customer WHERE user_id is null";
    private static final String DELETE_CUSTOMER_REQUEST = "DELETE from heimdall_db.tm_customer where pan_number='$'";
    private static final String DELETE_CUSTOMER_ENTRY = "DELETE from heimdall_db.customer where pan_number='$'";
    private static final String DELETE_CUSTOMER_REQUEST_VIA_USERID = "DELETE from heimdall_db.tm_customer where source_system_code='$'";
    private static final String DELETE_TRANSACTION_REQUEST ="DELETE from heimdall_db.tm_transaction where user_id='$'";
    private static final String DELETE_PRODUCT_ENTRY ="DELETE from heimdall_db.product WHERE product_acc_number='LP_$'";
    private static final String DELETE_FINANCIAL_TRANSACTION_ENTRY ="DELETE from heimdall_db.financial_transaction where transaction_id='$'";
    private static final String GET_PRODUCT_ENTRY_LP = "SELECT count(*) product_acc_number FROM heimdall_db.product WHERE product_acc_number='LP_$'";
    private static final String GET_PRODUCT_ENTRY_PS = "SELECT count(*) product_acc_number FROM heimdall_db.product WHERE product_acc_number='PS_$'";
    private static final String GET_FINANCIAL_TXN_ENTRY = "SELECT # FROM heimdall_db.financial_transaction WHERE transaction_id ='$'";
    private static final String GET_FINANCIAL_TXN_REQUEST = "SELECT # FROM heimdall_db.tm_transaction WHERE transaction_id='$'";
    private static final String GET_FINANCIAL_TXN_REQUEST_ISNULL = "SELECT # FROM heimdall_db.tm_transaction WHERE transaction_id is null";
    private static final String GET_FINANCIAL_TXN_FAILURE_VALIDATION = "SELECT # FROM heimdall_db.validation_failures WHERE request_id='$'";

    @DataProvider(name = "testForUUIDData")
    public Object[][] testForUUIDData() {
        return new Object[][]{
                {"ValidUUID_Lazypay"},
                {"ValidUUID_PS"},
                {"WithEmptyUUID"},
                {"WithNullUUID"}
        };
    }

    @DataProvider(name = "CustomerEffectiveStatusDateData")
    public Object[][] CustomerEffectiveStatusDateData() {
        return new Object[][]{
                {"WithValidCustomerEffectiveStatusDate"},
                {"WithNullCustomerEffectiveStatusDate"},
                {"WithEmptyCustomerEffectiveStatusDate"},
        };
    }

    @DataProvider(name = "testForPanNumberData")
    public Object[][] testForPanNumberData() {
        return new Object[][]{
                {"WithValidPanIDRefKey"},
                {"WithValidPanIDMaskedKey"},
                {"WithInValidPanIDRefKey"},
                {"WithInValidPanIDMaskedKey"},
                {"WithEmptyPanId"},
                {"WithNullPanId"}
        };
    }

    @DataProvider(name = "DOBData")
    public Object[][] DOBData() {
        return new Object[][]{
                {"WithValidDOB"},
                {"WithFutureDOB"},
                {"WithEmptyDOB"},
                {"WithNullDOB"}

        };
    }

    @DataProvider(name = "pincodeData")
    public Object[][] pincodeData() {
        return new Object[][]{
                {"withValidPermanentPincode"},
                {"withInValidPermanentPincode"},
                {"withCharLimitPermanentPincode"},
                {"withEmptyPermanentPincode"},
                {"withNullPermanentPincode"}

        };
    }

    @DataProvider(name = "addressData")
    public Object[][] addressData() {
        return new Object[][]{
                {"withValidPermanentAddress01"},
                {"withValidPermanentAddress02"},
                {"withValidPermanentAddress03"},
                {"withEmptyPermanentAddress"},
                {"withNullPermanentAddress"}

        };
    }

    @DataProvider(name = "panNameData")
    public Object[][] panNameData() {
        return new Object[][]{
                {"WithValidPanName"},
                {"WithInvalidPanName"},
                {"WithSpecialCharactersPanName"},
                {"WithNullPanName"},
                {"WithEmptyPanName"}
        };
    }


    @DataProvider(name = "segmentData")
    public Object[][] segmentData() {
        return new Object[][]{
                {"WithValidSegment"},
                {"WithInvalidSegment"},
                {"WithEmptySegment"},
                {"WithNullSegment"}
        };
    }

    @DataProvider(name = "productSegmentData")
    public Object[][] productSegmentData() {
        return new Object[][]{
                {"WithValidProductSegments_BNPL"},
                {"WithValidProductSegments_PersonalLoan"},
                {"WithInvalidProductSegments"},
                {"WithEmptyProductSegments"},
                {"WithNullProductSegments"}
        };
    }

    @DataProvider(name = "customerTypeData")
    public Object[][] customerTypeData() {
        return new Object[][]{
                {"WithValidCustomerType"},
                {"WithInvalidCustomerType"},
                {"WithEmptyCustomerType"},
                {"WithNullCustomerType"}
        };
    }

    @DataProvider(name = "genderTypeData")
    public Object[][] genderTypeData() {
        return new Object[][]{
                {"WithValidGender_M"},
                {"WithValidGender_F"},
                {"WithValidGender_T"},
                {"WithInvalidGender"},
                {"WithEmptyGender"},
                {"WithNullGender"}
        };
    }

    @DataProvider(name = "occupationTypeData")
    public Object[][] occupationTypeData() {
        return new Object[][]{
                {"WithValidOccupationType_01"},
                {"WithValidOccupationType_02"},
                {"WithValidOccupationType_03"},
                {"WithValidOccupationType_04"},
                {"WithValidOccupationType_05"},
                {"WithValidOccupationType_06"},
                {"WithValidOccupationType_07"},
                {"WithValidOccupationType_08"},
                {"WithValidOccupationType_09"},
                {"WithValidOccupationType_10"},
                {"WithValidOccupationType_11"},
                {"WithValidOccupationType_12"},
                {"WithValidOccupationType_13"},
                {"WithValidOccupationType_14"},
                {"WithValidOccupationType_15"},
                {"WithInvalidOccupationType"},
                {"WithEmptyOccupationType"},
                {"WithNullOccupationType"}
        };
    }

    @DataProvider(name = "ModuleApplicableData")
    public Object[][] ModuleApplicableData() {
        return new Object[][]{
                {"WithValidModuleApplicable_1"},
                {"WithValidModuleApplicable_2"},
                {"WithValidModuleApplicable_3"},
                {"WithEmptyModuleApplicable"},
                {"WithNullModuleApplicable"}
        };
    }

    @DataProvider(name = "RegulatoryAMLRiskData")
    public Object[][] RegulatoryAMLRiskData() {
        return new Object[][]{
                {"WithValidRegulatoryAMLRisk_VeryHigh"},
                {"WithValidRegulatoryAMLRisk_High"},
                {"WithValidRegulatoryAMLRisk_Medium"},
                {"WithValidRegulatoryAMLRisk_Low"},
                {"WithInvalidRegulatoryAMLRisk"},
                {"WithEmptyRegulatoryAMLRisk"},
                {"WithNullRegulatoryAMLRisk"}
        };
    }

    @DataProvider(name = "lastRiskReviewDateData")
    public Object[][] lastRiskReviewDateData() {
        return new Object[][]{
                {"WithValidLastRiskReviewDate"},
                {"WithNullLastRiskReviewDate"},
                {"WithEmptyLastRiskReviewDate"}
        };
    }

    @DataProvider(name = "nextRiskReviewDateData")
    public Object[][] nextRiskReviewDateData() {
        return new Object[][]{
                {"WithValidLastRiskReviewDate"},
                {"WithNullLastRiskReviewDate"},
                {"WithEmptyLastRiskReviewDate"}
        };
    }

    @DataProvider(name = "sourceData")
    public Object[][] sourceData() {
        return new Object[][]{
                {"WithValidSource"},
                {"WithValidSource_PS"},
                {"WithInValidSource"},
                {"WithNullSource"},
                {"WithEmptySource"}
        };
    }



    public Map<String, Object> customerRequestParamData() {
        Map<String, Object> queryParamDetails = new HashMap<>();
        queryParamDetails.put("userId", "d8802677-0ed0-4072-b13f-74122da8dcf6");
        queryParamDetails.put("customerStatusEffectiveDate", "2023-05-23");
        queryParamDetails.put("name", "TxnTMtest100100");
        queryParamDetails.put("dob", "1992-05-23");
        queryParamDetails.put("permanentPincode", "560102");
        queryParamDetails.put("permanentAddress", "testHouseno53ApoorvaNagarHsrLayout");
        queryParamDetails.put("correspondencePincode", "560103");
        queryParamDetails.put("correspondenceAddress", "testHouseno53ApoorvaNagarHsrLayout");
        queryParamDetails.put("panNumber", "60875a0f-272f-4892-862d-baaa2560c438");
        queryParamDetails.put("segments", "01");
        queryParamDetails.put("productSegments", "BNPL");
        queryParamDetails.put("customerType", "1");
        queryParamDetails.put("gender", "M");
        queryParamDetails.put("occupationType", "01");
        queryParamDetails.put("moduleApplicable", "TM%2CScreening");
        queryParamDetails.put("regulatoryAMLRisk", "HIGH");
        queryParamDetails.put("lastRiskReviewDate", "2023-05-23");
        queryParamDetails.put("nextRiskReviewDate", "2024-05-23");
        queryParamDetails.put("active", "true");
        queryParamDetails.put("source", "LazyPay");
        return queryParamDetails;
    }

    public HashMap<String, String> headerDetails() {
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        return headerDetails;
    }
    @DataProvider(name = "userIdDataForTxnRequest")
    public Object[][] userIdDataForTxnRequest() {
        return new Object[][]{
                {"ValidUserId_Lazypay"},
                {"ValidUserId_PS"},
                {"InvalidUserId_Lazypay"},
                {"UserId_With_NOCustomerEntry"},
                {"WithEmptyUserId"},
                {"WithNullUserId"}
        };
    }
    @DataProvider(name = "lpUuidData")
    public Object[][] lpUuidData() {
        return new Object[][]{
                {"ValidlpUuid_Lazypay"},
                {"DuplicatelpUuid_ForDifferentTxnId"},
                {"WithEmptylpUuid"},
                {"WithNulllpUuid"}
        };
    }

    @DataProvider(name = "loanIdData")
    public Object[][] loanIdData() {
        return new Object[][]{
                {"ValidloanId_Lazypay"},
                {"DuplicateloanId__ForDifferentTxnId"},
                {"WithEmptyloanId"},
                {"WithNullloanId"}
        };
    }
    @DataProvider(name = "transactionId")
    public Object[][] transactionId() {
        return new Object[][]{
                {"ValidTransactionId_Lazypay"},
                {"DuplicateTransactionId_Lazypay"},
                {"WithEmptyTransactionId"},
                {"WithNullTransactionId"}
        };
    }
    @DataProvider(name = "sanctionedAmount")
    public Object[][] sanctionedAmount() {
        return new Object[][]{
                {"ValidsanctionedAmount_Lazypay"},
                {"InvalidsanctionedAmount_Lazypay"},
                {"lessThanTxnAmount"},
                {"WithEmptySanctionedAmount"},
                {"WithNullSanctionedAmount"}
        };
    }
    @DataProvider(name = "txnAmount")
    public Object[][] txnAmount() {
        return new Object[][]{
                {"ValidtxnAmount_Lazypay"},
                {"InvalidtxnAmount_Lazypay"},
                {"GreaterThanSecuredAmount"},
                {"WithEmptytxnAmount"},
                {"WithNulltxnAmount"}
        };
    }
    @DataProvider(name = "productSegments")
    public Object[][] productSegments() {
        return new Object[][]{
                {"ValidProductSegments_LP_Value1"},
                {"ValidProductSegments_LP_Value2"},
                {"ValidProductSegments_PS"},
                {"InvalidProductSegments_Lazypay"},
                {"WithEmptyProductSegments"},
                {"WithNullProductSegments"}
        };
    }
    @DataProvider(name = "voucherType")
    public Object[][] voucherType() {
        return new Object[][]{
                {"ValidVoucherType_type1"},
                {"ValidVoucherType_type2"},
                {"InvalidVoucherType"},
                {"WithEmptyVoucherType"},
                {"WithNullVoucherType"}
        };
    }
    @DataProvider(name = "txnType")
    public Object[][] txnType() {
        return new Object[][]{
                {"ValidTxnType_type1"},
                {"ValidTxnType_type2"},
                {"ValidTxnType_type3"},
                {"ValidTxnType_type4"},
                {"ValidTxnType_type5"},
                {"ValidTxnType_type6"},
                {"ValidTxnType_type7"},
                {"ValidTxnType_type8"},
                {"ValidTxnType_type9"},
                {"ValidTxnType_type10"},
                {"ValidTxnType_type11"},
                {"ValidTxnType_type12"},
                {"ValidTxnType_type13"},
                {"ValidTxnType_type14"},
                {"ValidTxnType_type15"},
                {"ValidTxnType_type16"},
                {"ValidTxnType_type17"},
                {"ValidTxnType_type18"},
                {"ValidTxnType_type19"},
                {"ValidTxnType_type20"},
                {"ValidTxnType_type21"},
                {"ValidTxnType_type22"},
                {"ValidTxnType_type23"},
                {"ValidTxnType_type24"},
                {"ValidTxnType_type25"},
                {"ValidTxnType_type26"},
                {"ValidTxnType_type27"},
                {"ValidTxnType_type28"},
                {"ValidTxnType_type29"},
                {"ValidTxnType_type30"},
                {"ValidTxnType_type31"},
                {"ValidTxnType_type32"},
                {"ValidTxnType_type33"},
                {"ValidTxnType_type34"},
                {"ValidTxnType_type35"},
                {"ValidTxnType_type36"},
                {"ValidTxnType_type37"},
                {"InvalidTxnType_Lazypay"},
                {"WithEmptyTxnType"},
                {"WithNullTxnType"}
        };
    }
    @DataProvider(name = "instrumentType")
    public Object[][] instrumentType() {
        return new Object[][]{
                {"ValidInstrumentType_type1"},
                {"ValidInstrumentType_type2"},
                {"ValidInstrumentType_type3"},
                {"ValidInstrumentType_type4"},
                {"ValidInstrumentType_type5"},
                {"ValidInstrumentType_type6"},
                {"ValidInstrumentType_type7"},
                {"ValidInstrumentType_type8"},
                {"ValidInstrumentType_type9"},
                {"ValidInstrumentType_type10"},
                {"ValidInstrumentType_type11"},
                {"ValidInstrumentType_type12"},
                {"ValidInstrumentType_type13"},
                {"ValidInstrumentType_type14"},
                {"InvalidInstrumentType_Lazypay"},
                {"WithEmptyInstrumentType"},
                {"WithNullInstrumentType"}
        };
    }
    @DataProvider(name = "sanctionDate")
    public Object[][] sanctionDate() {
        return new Object[][]{
                {"WithValidsanctionDate"},
                {"WithInValidsanctionDate"},
                {"WithNullsanctionDate"}

        };
    }

    @DataProvider(name = "txnCreateDate")
    public Object[][] txnCreateDate() {
        return new Object[][]{
                {"WithValidtxnCreateDate"},
                {"WithInValidtxnCreateDate"},
                {"WithNulltxnCreateDate"}
        };
    }

    @DataProvider(name = "Source")
    public Object[][] Source() {
        return new Object[][]{
                {"ValidSource_Lazypay"},
                {"ValidSource_PS"},
                {"CustomerToTxn_SourceMismatch"},
                {"PSSource_WithLpUuuid"},
                {"LPSource_WithLoanId"},
                {"InvalidSource"},
                {"WithEmptySource"},
                {"WithNullSource"}
        };
    }
    public Map<String, Object> txnRequestParamData(){
        Map<String, Object> queryParamDetails = new LinkedHashMap<>();
        queryParamDetails.put("userId","8179250614667824840");
        queryParamDetails.put("lpUuid","12740159");
        queryParamDetails.put("loanId",null);
        queryParamDetails.put("transactionId","TXN1684788799300");
        queryParamDetails.put("sanctionedAmount","30000");
        queryParamDetails.put("sanctionDate","2023-06-01");
        queryParamDetails.put("txnAmount","900");
        queryParamDetails.put("txnCreateDate","2023-06-13");
        queryParamDetails.put("productSegments","BNPL");
        queryParamDetails.put("voucherType","Receipt");
        queryParamDetails.put("txnType","13");
        queryParamDetails.put("instrumentType","Net%20Banking");
        queryParamDetails.put("source","LazyPay");
        queryParamDetails.put("active","true");
        return queryParamDetails;
    }

    @SneakyThrows
    public boolean validateCustomerEntry(String userID, String field, String value) {
        String s = "";
        ResultSet result = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_ENTRY.replace("#", "*").replace("$", userID));
        Allure.addAttachment("Data result is ", String.valueOf(result));
        Allure.addAttachment(" user id "+ userID+" "+field+" value is ", s);
        ResultSet rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_ENTRY.replace("#", field).replace("$", userID));
        while (rs.next())
            s = rs.getString(field);
        Allure.addAttachment(" user id "+ userID+" "+field+" value is ", s);
        return s.equals(value) ? true : false;
    }

    @SneakyThrows
    public boolean validateCustomerRequest(String userID, String field, String value) {
        String s = "";
        ResultSet rs;
        if (userID.equals("is null")) {
            rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_REQUEST_ISNULL.replace("#", field));
        } else {
            rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_REQUEST.replace("#", field).replace("$", userID));
        }
        while (rs.next())
            s = rs.getString(field);
        log.info("field " + field + " value is " + s);
        Allure.addAttachment(" user id "+ userID+" "+field+" value is ", s);
        return s.equals(value) ? true : false;
    }

    @SneakyThrows
    public boolean validateCustomerFailureValidation(String userID, String errorCode, String errorDesc) {

        String id = "";
        ResultSet id_rs;
        if (userID.equals("is null")) {
            id_rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_REQUEST_ISNULL.replace("#", "id"));
        } else {
            id_rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_REQUEST.replace("#", "id").replace("$", userID));
        }

        while (id_rs.next())
            id = id_rs.getString("id");

        String error_code = "", error_desc = "";
        ResultSet rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_FAILURE_VALIDATION.replace("#", "error_code").replace("$", id));
        while (rs.next())
            error_code = rs.getString("error_code");

        ResultSet rs2 = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_CUSTOMER_FAILURE_VALIDATION.replace("#", "error_desc").replace("$", id));
        while (rs2.next())
            error_desc = rs2.getString("error_desc");
        Allure.addAttachment(" user id is "+ userID," error_code is " +error_code+ " error_desc is " + error_desc);
        if (error_code.equals(errorCode) & error_desc.contains(errorDesc))
            return true;
        return false;
    }

    @SneakyThrows
    protected void deleteUserData(String panNumber) {
        Heimdall_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_CUSTOMER_REQUEST.replace("$", panNumber));
        Heimdall_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_CUSTOMER_ENTRY.replace("$", panNumber));
    }

   @SneakyThrows
   public boolean validateProductEntry(String source, String id, String field, String value)  {
       String s = "";
       ResultSet rs ;
       if(source.equalsIgnoreCase("LazyPay")) {
           rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_PRODUCT_ENTRY_LP.replace("#", field).replace("$", id));
           Allure.addAttachment("Data result is ", String.valueOf(rs));
           Allure.addAttachment(" lpUuid is " + id + " " + field + " value is ", s);
       }else{
           rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_PRODUCT_ENTRY_PS.replace("#", field).replace("$", id));
           Allure.addAttachment("Data result is ", String.valueOf(rs));
           Allure.addAttachment(" loanId is " + id + " " + field + " value is ", s);
       }
       while (rs.next())
           s = rs.getString(field);
       Allure.addAttachment(" id for "+source+" user is " + id+" "+field+" value is ", s);

       return s.equals(value) ? true : false;
   }

    @SneakyThrows
    public boolean validateTransactionEntry(String txnId, String field,String value )  {
        String s = "";
        ResultSet result = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_ENTRY.replace("#", "*").replace("$", txnId));
        Allure.addAttachment("Data result is ", String.valueOf(result));
        Allure.addAttachment(" user id "+ txnId+" "+field+" value is ", s);
        ResultSet rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_ENTRY.replace("#", field).replace("$", txnId));

        while (rs.next())
            s = rs.getString(field);
        Allure.addAttachment(" user id "+ txnId+" "+field+" value is ", s);
        return s.equals(value) ? true : false;
    }
    @SneakyThrows
    public boolean validateFinancialTxnRequest(String txnId, String field,String value)  {
        String s = "";
        ResultSet rs;
        if (txnId.equals("is null")) {
            rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_REQUEST_ISNULL.replace("#", field));
        } else {
            rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_REQUEST.replace("#", field).replace("$", txnId));
        }
        while (rs.next())
            s = rs.getString(field);
        log.info("field "+field+" value is "+ s);
        return s.equals(value) ? true : false;
    }

    @SneakyThrows
    public boolean validateFinancialTxnFailureValidation(String txnId, String errorCode, String errorDesc){

        String id = "";
        ResultSet id_rs;
        if (txnId.equals("is null")){
            id_rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_REQUEST_ISNULL.replace("#", "id"));
        } else {
            id_rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_REQUEST.replace("#", "id").replace("$", txnId));
        }

        while (id_rs.next())
            id = id_rs.getString("id");

        String error_code = "", error_desc = "";
        ResultSet rs = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_FAILURE_VALIDATION.replace("#", "error_code").replace("$", id));
        while (rs.next())
            error_code = rs.getString("error_code");

        ResultSet rs2 = Heimdall_MySQL_DBAccessObject.selectFromMySqlDb(GET_FINANCIAL_TXN_FAILURE_VALIDATION.replace("#", "error_desc").replace("$", id));
        while (rs2.next())
            error_desc = rs2.getString("error_desc");
        Allure.addAttachment(" user id is "+ txnId," error_code is " +error_code+ " error_desc is " + error_desc);
        if (error_code.equals(errorCode) & error_desc.contains(errorDesc))
            return true;
        return false;
    }

    @SneakyThrows
    protected void deleteTxnRequestData(String userId) {
        Heimdall_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_TRANSACTION_REQUEST.replace("$",userId));
        String lpUuid = txnRequestParamData().get("lpUuid").toString();
        Heimdall_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_PRODUCT_ENTRY.replace("$", lpUuid));
        String financialTxnRequestId = txnRequestParamData().get("transactionId").toString(); //fetchFinancialTxnEntry(userId);
        Heimdall_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_FINANCIAL_TRANSACTION_ENTRY.replace("$", financialTxnRequestId));


    }


}
