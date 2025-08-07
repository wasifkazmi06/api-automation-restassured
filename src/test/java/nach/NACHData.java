package nach;

import dbUtils.Collection_MySQL_DBAccessObject;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class NACHData {

    public static final String userAgent = "LazyPay-Sandbox/136 Dalvik/2.1.0 (Linux; U; Android 9 OnePlus ONEPLUS A5000 Build/7.5.4)";
    public static final String requestId = "J14M7PC6D2NRCNQ7YKDRK4I5Q6CAZ9XJ";
    public static final String osversion = "11";
    public static final String manufacturer = "OnePlus";
    public static final String devicename = "GM1911";
    public static final String deviceTypeAndroid = "android";
    public static final String deviceId = "8240653207c9d1c2";
    public static final String location = "12.945827,77.70274";
    public static final String deviceIP = "52.66.101.106";

    public static final String bnplProduct = "BNPL";


    public static final String digioNBUser = "6000000352";
    public static final String razorpayNBUser = "6000000353";

    public static final String digioNBMADUser = "6000000354";
    public static final String razorpayNBMADUser = "6000000355";
    public static final String nachGenricUser = "6000000356";
    public static final String flowType = "repaymentSetup";
    public static final String repaymentAmountTypeFull = "FULL";
    public static final String repaymentAmountTypeMAD = "MAD";
    public static final String paymentModeTypeNB = "NET_BANKING";

    public static final String accountNoDigio = "12345678909876";
    public static final String bankNameDigio = "HDFC BANK LTD.";
    public static final String accountHolderNameDigio = "Test Test";
    public static final String ifscDigio = "HDFC0000794";

    public static final String accountNoDigioMAD = "12345678909877";
    public static final String bankNameDigioMAD = "STATE BANK OF INDIA";
    public static final String accountHolderNameDigioMAD = "Test Test";
    public static final String ifscDigioMAD = "SBIN0006249";

    public static final String accountNoGenric = "12345678909878";
    public static final String bankNameGenric = "STATE BANK OF INDIA";
    public static final String accountHolderNameGenric = "Test Test";
    public static final String ifscGenric = "SBIN0006249";

    public static final String accountNoRazorpay = "12345678909878";
    public static final String bankNameRazorpay = "AXIS BANK";
    public static final String accountHolderNameRazorpay = "Test Test";
    public static final String ifscRazorpay = "UTIB0000388";

    public static final String accountNoRazorpayMAD = "12345678909879";
    public static final String bankNameRazorpayMAD = "ICICI BANK LTD.";
    public static final String accountHolderNameRazorpayMAD = "Test Test";
    public static final String ifscRazorpayMAD = "ICIC0000666";


    private static String UPDATE_COLLECTION_SETUP = "UPDATE collection_setup SET setup_signed_date = now(), status = 'ACCEPTED' WHERE id = $;";
    private static String UPDATE_METHOD_VENDOR_DIGIO = "UPDATE digio_nb_nach_setup SET authenticate_at = now(), bank_detail_state = 'register_success'," +
            " state = 'REGISTER_SUCCESS', status = 'ACCEPTED', umrn = 'UMRN2659441587365619', message_id = 'MMI221221173022188RSNWQHAWWQWROK'," +
            " npci_transaction_id = '8cff752e65c549f2b4e0906cd5e35d3a' " +
            "WHERE id in (SELECT method_vendor_id from collection_setup where id = $); ";
    private static String UPDATE_SETUP_PRODUCT_MAPPING = "UPDATE setup_product_mapping SET collection_enabled = '1' WHERE setup_id = $;";
    private static String UPDATE_METHOD_VENDOR_RAZORPAY = "UPDATE set1_collectionDb.razorpay_nb_nach_setup SET payment_id = '#', payment_status = 'captured'," +
            " reject_code = NULL, status = 'ACCEPTED', token_id = '@', `token_status` = 'confirmed'" +
            " WHERE id in  (SELECT method_vendor_id from set1_collectionDb.collection_setup where id = $);";
    private static String UPDATE_RAZORPAY_ORDER = "UPDATE set1_collectionDb.razorpay_order SET status = 'paid' WHERE id in " +
            "(select order_id FROM set1_collectionDb.razorpay_nb_nach_setup where id in " +
            "(SELECT method_vendor_id from set1_collectionDb.collection_setup where id = $));";


    @SneakyThrows
    public static void acceptNACHSetupDigio(String id) {
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_COLLECTION_SETUP.replace("$", id));
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_METHOD_VENDOR_DIGIO.replace("$", id));
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_SETUP_PRODUCT_MAPPING.replace("$", id));
    }

    @SneakyThrows
    public static void acceptNACHSetupRazorpay(String id, String razorpayPaymentId, String razorpayTokenId) {
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_COLLECTION_SETUP.replace("$", id));
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_METHOD_VENDOR_RAZORPAY
                .replace("$", id)
                .replace("#", razorpayPaymentId)
                .replace("@", razorpayTokenId));
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_RAZORPAY_ORDER.replace("$", id));
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_SETUP_PRODUCT_MAPPING.replace("$", id));
    }

}