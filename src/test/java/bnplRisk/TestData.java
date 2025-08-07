package bnplRisk;

import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;

@Slf4j
public class TestData {

     public static String columnData= "";
     public static String USER_MOBILE_VALID1 = "6000000112";
     public static String USER_MOBILE_VALID2 = "6000000112";
     public static String TRANSACTION_AMOUNT = "123.45";
     public static String ACCESS_KEY_COF="EW24V1VDP4J9054E3P65";
     public static String SUBMERCHANT_ID_COF="PayuCOFTest";
     public static String PRODUCT_COF = "COF";
     public static String PRODUCT_BNPL = "BNPL";
     public static final String RISK_GET_DECISION_V1 = "V1";
     public static final String RISK_GET_DECISION_V2 = "V2";
     public static final String DEFAULT_INTEREST_12M = "30.0";
     public static final String DEFAULT_INTEREST_9M = "28.0";
     public static final String DEFAULT_INTEREST_6M = "26.0";
     public static final String DEFAULT_INTEREST_3M = "24.0";
     public static final String TENURE_3M = "3";
     public static final String TENURE_6M = "6";
     public static final String TENURE_9M = "9";
     public static final String TENURE_12M = "12";
     public static final String PRE_APPROVE_LIMIT_RISK_LEVEL_1 = "25000.0";
     public static final String PRE_APPROVE_LIMIT_RISK_LEVEL_2 = "50000.0";
     public static final String PRE_APPROVE_LIMIT_RISK_LEVEL_3 = "100000.0";
     public static final String RISK_CATEGORY_1 = "1";
     public static final String RISK_CATEGORY_2 = "2";
     public static final String RISK_CATEGORY_3 = "3";
     public static final String TEST_PAN_HASH = "cc1f7919b069a23d989a95a1f7b24141916e66baabc81df36054926860cfcd12";
     public static final String MITC_YEAR_VALIDITY_DEFAULT = "5";

     private static String DELETE_PRE_APPROVE_HISTORY ="delete from riskpl_upi.pre_approve_limit_history where mobile = '$';";
     private static String SELECT_PRE_APPROVE_HISTORY="select * from riskpl_upi.pre_approve_limit_history where mobile = '$' order by id desc limit 1;";
     private static String SELECT_MERCHANT ="select * from lazypay.merchants where accessKey = '$';";
     private static String SELECT_SUB_MERCHANT ="select * from lazypay.sub_merchants where subMerchantId = '$' and accessKey = '#';";

     @SneakyThrows
     public static void deletePreApproveHistory(String mobile) {
          log.info("Pre approve delete query is: " + DELETE_PRE_APPROVE_HISTORY.replace("$", mobile));
          Lazypay_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_PRE_APPROVE_HISTORY.replace("$", mobile));
     }

     @SneakyThrows
     public static ResultSet getPreApproveHistory(String mobile){
          log.info("Get pre approve limit query is: " + SELECT_PRE_APPROVE_HISTORY.replace("$", mobile));
          return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_PRE_APPROVE_HISTORY.replace("$", mobile));
     }

     @SneakyThrows
     public static String getMerchant(String accessKey, String columnName) {

          log.info("Get merchant query is: " + SELECT_MERCHANT.replace("$", accessKey));
          ResultSet merchant = Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_MERCHANT.replace("$", accessKey));
          while (merchant.next()){
               columnData = merchant.getString(columnName);
          }
          return columnData;
     }

     @SneakyThrows
     public static String getSubMerchant(String accessKey, String subMerchantId, String columnName) {
          log.info("Get merchant query is: " + SELECT_SUB_MERCHANT.replace("$", subMerchantId).replace("#", accessKey));
          ResultSet subMerchant = Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_SUB_MERCHANT.replace("$",
                  subMerchantId).replace("#", accessKey));
          while (subMerchant.next()){
               columnData = subMerchant.getString(columnName);
          }
          return columnData;
     }
}
