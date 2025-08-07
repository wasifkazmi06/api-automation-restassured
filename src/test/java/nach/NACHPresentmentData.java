package nach;

import dbUtils.Collection_MySQL_DBAccessObject;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class NACHPresentmentData {


    public static final String bnplProduct="BNPL";
    public static final String cash_loanProduct="CASH_LOAN";
    public static final String amount = "700.53";
    public static final String eventRazorpaySuccess = "payment.captured";
    public static final String eventRazorpayFailure = "payment.failed";
    public static final String error_code = "BAD_REQUEST_ERROR";
    public static String error_description = "Your payment could not be completed due to insufficient account balance. Try again with another account.";
    public static final String error_reason = "insufficient_funds";
    public static final String error_source = "customer";
    public static final String error_step = "payment_authorization";
    public static final String status_captured = "captured";
    public static final String status_failed = "failed";



    public static final String razorpayNBUser_BNPL_SUCCESS="6000000551";
    public static final String razorpayNBUser_BNPL_FAIL="6000000552";
    public static final String razorpayNBUser_CL_SUCCESS="6000000553";
    public static final String razorpayNBUser_CL_FAIL="6000000554";
    public static final String digioNBUser_BNPL_SUCCSESS="6000000555";
    public static final String digioNBUser_BNPL_FAIL="6000000556";
    public static final String digioNBUser_CL_SUCCSESS="6000000557";
    public static final String digioNBUser_CL_FAIL="6000000558";




    private static String SELECT_COLLECTION="select * from set1_collectionDb.collection where um_uuid = '$' order by id desc limit 1;";
    private static String SELECT_RAZORPAY_NB_NACH_COLLECTION="SELECT * FROM set1_collectionDb.razorpay_nb_nach_collection where id = '$';";
    private static String SELECT_RAZORPAY_ORDER="SELECT * FROM set1_collectionDb.razorpay_order where id = '$';";

    private static String UPDATE_NB_NACH_COLLECTION="update set1_collectionDb.razorpay_nb_nach_collection set " +
            "date_created = '$', date_updated = '#' where id = '?';";
    private static String DELETE_COLLECTION_FAILURE_STATS="delete from set1_collectionDb.collection_failure_stats where setup_id = '$';";



    @SneakyThrows
    public static ResultSet nachCollection(String id)
    {
        return Collection_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_COLLECTION.replace("$", id));

    }

    @SneakyThrows
    public static ResultSet razorpayNBNachCollection(String id)
    {
        return Collection_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_RAZORPAY_NB_NACH_COLLECTION.replace("$", id));
    }

    @SneakyThrows
    public static ResultSet razorpayOrderCollection(String id)
    {
        return Collection_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_RAZORPAY_ORDER.replace("$", id));
    }

    @SneakyThrows
    public static void updateRazorpayNBnachCollection(String dateCreated, String dateUpdated, String methodVendorId)
    {
        Collection_MySQL_DBAccessObject.updateOnMySqlDb(UPDATE_NB_NACH_COLLECTION
                .replace("$", dateCreated)
                .replace("#", dateUpdated)
                .replace("?", methodVendorId));

    }

    @SneakyThrows
    public static void deleteCollectionFailureStats(String repaySetupId)
    {
        Collection_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_COLLECTION_FAILURE_STATS.replace("$",repaySetupId));
    }

}