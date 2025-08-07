package kyc;

import dbUtils.Bifrost_MySQL_DBAccessObject;
import dbUtils.KYCPlatform_MySQL_DBAccessObject;
import dbUtils.KYC_MySQLSsh_DBAccessObject;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@Slf4j
public class DBValidations {
    private static final String GET_CKYC_REPORT_ENTRY = "select count(#) reports from ckyc_reporting where um_uuid = '$'";
    private static final String GET_PAN_DELINK_ENTRY = "select # from kycsbox.pan_delink pd where uuid = '$'";
    private static final String GET_PAN_DELINK_STATUS = "select status from kycsbox.pan_delink pd where old_um_uuid = '$'";
    private static final String GET_KYCCASE_DEATAILS_ID = "select # from kycsbox.kyc_case_detail where uuid = '$' and active = 1";
    private static final String GET_CKYC_NUMBER = "select # from kycsbox.ckyc_download where uuid = '$'";
    private static final String GET_KYC_CASE_ID = "select * from kycsbox.kyc_case where uuid='$' and product='[#]'";

    //Bifrost Queries
    private static final String GET_USER_ELIGIBILITY_ENTRY = "select count(#) bbpsProduct from bifrost.user_eligibility where user_id ='$' and product ='%'";

    //KYCPlatform DB_DigiLocker_DeclinedStatusUser
    private static final String Insert_DigiLocker_Auto_Declined_User ="INSERT INTO kyc_platform.digilocker_start_details\n" +
            "(id, created_at, updated_at, principal_id, processed, source, start_url, status, transaction_id)\n" +
            "VALUES(20001, 1724656395549, 1724659510486, 'test-auto-declined-2888', 0, 'LP', 'https://link-kyc.idv.hyperverge.co/?identifier=69jbf1c0-7024-454c-8888-280d190d6421_test-auto-declined-1787-1724656395356', 'AUTO_DECLINED', 'test-auto-declined-2888-1724656395356');";

    //KYCPlatform Queries
    private static final String GET_DIGILOCKER_START_DETAILS = "select * from digilocker_start_details dsd where principal_id  = '$'";

    private static final String DELETE_AUTODECLINED_ENTRY = "delete from digilocker_start_details where principal_id = '$'";
    @SneakyThrows
    public boolean validateCKYCReportEntry(String userID, int value) {
        String s = "";
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_CKYC_REPORT_ENTRY.replace("#", "*").replace("$", userID));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        Allure.addAttachment(" user id " + userID + " value is ", s);
        while (rs.next())
            s = rs.getString("reports");
        Allure.addAttachment(" user id " + userID + " value is ", s);
        return s.equals(value) ? true : false;
    }

    @SneakyThrows
    public boolean validatePanDelinkEntry(String firstUser, String secondUser) {
        String old_user = "";
        String new_user = "";
        ResultSet rs1 = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_PAN_DELINK_ENTRY.replace("#", "*").replace("$", secondUser));
        Allure.addAttachment("Data result is ", String.valueOf(rs1));
        while (rs1.next())
            old_user = rs1.getString("old_um_uuid");

        ResultSet rs2 = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_PAN_DELINK_ENTRY.replace("#", "*").replace("$", secondUser));
        Allure.addAttachment("Data result is ", String.valueOf(rs2));
        while (rs2.next())
            new_user = rs2.getString("uuid");

        Allure.addAttachment(" old user id " + old_user + " & New user is ", new_user);
        log.info(" old user id " + old_user + " & New user is ", new_user);
        if (old_user.equals(firstUser) & new_user.contains(secondUser))
            return true;
        return false;
    }

    @SneakyThrows
    public boolean fetchPanDilinkEntryStatus(String oldUser, String expectedStatus) {
        String status = "";
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_PAN_DELINK_STATUS.replace("#", "*").replace("$", oldUser));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        while (rs.next())
            status = rs.getString("status");
        Allure.addAttachment(" Pan Delink initiated for old_user ", oldUser);
        log.info("Pan Delink initiated for old_user " + oldUser);
        return status.equals(expectedStatus) ? true : false;
    }

    @SneakyThrows
    public int validateKYCCaseDetailId(String userID, String field) {
        int caseId = 0;
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_KYCCASE_DEATAILS_ID.replace("#", field).replace("$", userID));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        Allure.addAttachment(" user id " + userID + " value is ", String.valueOf(caseId));
        while (rs.next())
            caseId = Integer.parseInt(rs.getString(field));
        Allure.addAttachment(" user id " + userID + " value is ", String.valueOf(caseId));
        return caseId;
    }

    @SneakyThrows
    public String validateCKYCNumber(String userID, String field) {
        String ckycNo = "";
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_CKYC_NUMBER.replace("#", field).replace("$", userID));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        Allure.addAttachment(" user id " + userID + " value is ", ckycNo);
        while (rs.next())
            ckycNo = rs.getString(field);
        Allure.addAttachment(" user id " + userID + " value is ", ckycNo);
        return ckycNo;
    }

    // This method will return the kyc_case_Id from kyc_case table.
    @SneakyThrows
    public String getKycCaseId(String uuid, String product) {
        String kycCaseId = "";
        String sqlQuery = GET_KYC_CASE_ID.replace("$", uuid).
                replace("#", "\"" + product + "\"");
        log.info("Logging sql query :: " + sqlQuery);
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(sqlQuery);
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        while (rs.next())
            kycCaseId = rs.getString("id");
        Allure.addAttachment("kycCaseId from kyc_case table ::", kycCaseId);
        return kycCaseId;
    }

    // This method will return the kyc_status from kyc_case table.
    @SneakyThrows
    public String getKycStatus(String uuid, String product) {
        String kycStatus = "";
        String sqlQuery = GET_KYC_CASE_ID.replace("$", uuid).
                replace("#", "\"" + product + "\"");
        log.info("Logging sql query :: " + sqlQuery);
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDb(sqlQuery);
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        while (rs.next())
            kycStatus = rs.getString("status");
        Allure.addAttachment("Kyc status from kyc_case table ::", kycStatus);
        return kycStatus;
    }

    @SneakyThrows
    public boolean validateUserEligibilityEntry(String userId,String product){

        String s = "";
        ResultSet rs = Bifrost_MySQL_DBAccessObject.selectFromMySqlDb(GET_USER_ELIGIBILITY_ENTRY.replace("#", "*").replace("$", userId).replace("%",product));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        Allure.addAttachment(" user id " + userId + " value is ", s);
        while (rs.next())
            s = rs.getString("bbpsProduct");
        Allure.addAttachment(" user id " + userId + " value is ", s);
        return s.equals(1) ? true : false;
    }
    @SneakyThrows
    public String validateStartLinkEntry(String principal_id){

        String status = "";
        ResultSet rs = KYCPlatform_MySQL_DBAccessObject.selectFromMySqlDb(GET_DIGILOCKER_START_DETAILS.replace("$", principal_id));
        Allure.addAttachment("Data result is ", String.valueOf(rs));
        while (rs.next())
            status = rs.getString("status");
        Allure.addAttachment(" principal_id " + principal_id," digilocker status " + status);
        return status;
    }

    @SneakyThrows
    public void InsertDigiLockerAutoDeclinedUser(){
        KYCPlatform_MySQL_DBAccessObject.insertIntoMySqlDb(Insert_DigiLocker_Auto_Declined_User);

    }

    public void DeleteDigiLockerAutoDeclinedUser(String param) throws SQLException, ClassNotFoundException {

        KYCPlatform_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_AUTODECLINED_ENTRY.replace("$",param));

    }
}

