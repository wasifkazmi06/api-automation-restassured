package neo.fd;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import dbUtils.FD_MySQLSsh_DBAccessObject;
import dbUtils.FD_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.sql.ResultSet;
import java.util.Formatter;
import java.util.HashMap;

@Slf4j

public class FdDBData {

    private static final String HMAC_SHA1_ALGORITHM = "HMACSHA256";

        private static final String SET_CARD_PREF_ONLINE_NOTALLOWED="UPDATE lazycards SET contactLessTransactionEnabled = 'NOTALLOWED', onlineTransactionEnabled= 'NOTALLOWED',posTransactionEnabled='NOTALLOWED' WHERE lazypayUuid = 5837318 AND status ='ACTIVE';";
        private  static final String GET_CARD_PREFERENCE="SELECT $ FROM lazycards l WHERE lazypayUuid = 5837318 AND status ='ACTIVE';";
        private  static final String GET_BLOCKED_CARD_PREFERENCE="SELECT $ FROM lazycards l WHERE lazypayUuid = 5837318 AND status ='BLOCKED';";
        private  static final String GET_SECURED_CARD_QUEUE="select $ from sbox_fd_service.user_queue where lp_uuid = #;";
        private  static final String GET_SECURED_CARD_VerifiedUPIId="select $ from sbox_fd_service.user_upi_details where upi_id = '#';";
        private  static final String GET_SECURED_CARD_QUEUECreationData="select $ from sbox_fd_service.user_queue where lp_uuid = # ;";
        private  static final String GET_FD_PAYMENT_STATUS="select * from sbox_fd_service.fd_payment where order_id = '#';";
        private static final String GET_FD_CREATED_DETAILS="select * from sbox_fd_service.user_fd_details where fd_payment_id = '$';";
        private static final String GET_FD_RETRIAL_DETAILS="select * from sbox_fd_service.fd_retrial where fd_id = '$';";
        private  static final String GET_FD_STATUS="select * from sbox_fd_service.user_fd_details where id = '#';";
        private  static final String GET_LIEN_MODIFIED_FD="select * from sbox_fd_service.lien_modified_fd where fd_id = '#';";


    @SneakyThrows
    @Step
    private static String printHexBinary(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    @SneakyThrows
    @Step
    public static String calculateRFC2104HMAC(String data, String secret) throws Exception {
        String result = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = printHexBinary(rawHmac).toLowerCase();
        } catch (Exception e) {
            log.info("Failed to generate HMAC " + e);
            e.printStackTrace();
        }
        return result;
    }
        @SneakyThrows
        @Step
        public  static int getSecuredCardQueueCount(String preference, String lpUuid)
        {

            int s = 0;
            ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_QUEUE.replace("$",preference).replace("#",lpUuid));
            while (rs.next())
                s= rs.getInt(preference);
            return s;
        }
        @SneakyThrows
        @Step
        public  static int getVerifiedUPIId(String preference, String upiId)
        {

            int nameScore = 0;
            ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_VerifiedUPIId.replace("$",preference).replace("#",upiId));
            while (rs.next())
                nameScore= rs.getInt(preference);
            return nameScore;
        }

        @SneakyThrows
        @Step
        public  static int getUserQueueStatus(String preference, String lpUuid)
        {

            int userStatus = 0;
            ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_SECURED_CARD_QUEUECreationData.replace("$",preference).replace("#",lpUuid));
            while (rs.next())
                userStatus= rs.getInt(preference);
            return userStatus;
        }

    @SneakyThrows
    @Step
    public  static HashMap<String, Integer> getfdPaymentStatus(String orderId)
    {
        HashMap<String ,Integer> paymentdetails=new HashMap();
        int payment_Status_id = 0;
        ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_FD_PAYMENT_STATUS.replace("#",orderId));
        while (rs.next()) {
            payment_Status_id = rs.getInt("payment_status");
            paymentdetails.put("payment_status", payment_Status_id);
            payment_Status_id = rs.getInt("id");
            paymentdetails.put("id", payment_Status_id);
        }
        return paymentdetails;
    }

    @SneakyThrows
    @Step
    public  static HashMap<String, Object> getFdStatus(String fdId) {
        HashMap<String, Object> fddetails=new HashMap();
        ResultSet rs = FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_FD_STATUS.replace("#", fdId));
        while (rs.next()) {
            fddetails.put("fd_status",rs.getInt("fd_status"));
            fddetails.put("account_number", rs.getString("account_number"));
        }
        return fddetails;
    }

    @SneakyThrows
    @Step
    public  static HashMap<String, Object> getfdDetailsPostCreation(String paymentId)
    {
        HashMap<String, Object> fddetails=new HashMap();
        ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_FD_CREATED_DETAILS.replace("$",paymentId));
        while (rs.next()) {
            fddetails.put("lp_uuid", rs.getInt("lp_uuid"));
            fddetails.put("fdId", rs.getInt("id"));
            fddetails.put("entity_id", rs.getString("entity_id"));
            fddetails.put("verified_upi_id", rs.getInt("verified_upi_id"));
            fddetails.put("nominee_id", rs.getInt("nominee_id"));
            fddetails.put("account_number", rs.getString("account_number"));
            fddetails.put("amount", rs.getInt("amount"));
            fddetails.put("maturity_amount", rs.getFloat("maturity_amount"));
            fddetails.put("fd_status", rs.getInt("fd_status"));
            fddetails.put("fd_created_at", rs.getLong("fd_created_at"));
            fddetails.put("interest_rate", rs.getFloat("interest_rate"));
            fddetails.put("fd_type", rs.getInt("fd_type"));
            fddetails.put("maturity_date", rs.getInt("maturity_date"));
        }
        return fddetails;
    }

    @SneakyThrows
    @Step
    public  static HashMap<String, String> getfdRetrialData(String fdId)
    {
        HashMap<String ,String> fdRetrialdetails=new HashMap();
        ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_FD_RETRIAL_DETAILS.replace("$",fdId));
        while (rs.next()) {
            String errorMsg = rs.getString("error_message");
            fdRetrialdetails.put("error_message", errorMsg);
        }
        return fdRetrialdetails;
    }

    @SneakyThrows
    @Step
    public  static HashMap<String, Object> getLienModifiedFdDetails(String fdId)
    {
        HashMap<String ,Object> lienModifiedDetails=new HashMap();
        String lienModifiedFdStatus ;
        ResultSet rs= FD_MySQLSsh_DBAccessObject.selectFromMySqlDb(GET_LIEN_MODIFIED_FD.replace("#",fdId));
        if(((ResultSetImpl) rs).getRows().size() > 0) {
            rs.next();
                lienModifiedFdStatus = rs.getString("status");
                lienModifiedDetails.put("status", lienModifiedFdStatus);
                lienModifiedDetails.put("leinModifiedCount",((ResultSetImpl) rs).getRow());
        }else {
            lienModifiedDetails.put("leinModifiedCount",0);
        }
        return lienModifiedDetails;
    }
    }
