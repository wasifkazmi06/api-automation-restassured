package mpl;

import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;

public class TestData {

    /**
     * User
     */
    public static final String USER_MOBILE_VALID_1 = "6000000671";
    public static final String USER_EMAIL_VALID_1 = "Test671@gmail.com";

    public static final String USER_MOBILE_VALID_2 = "6000000672";
    public static final String USER_EMAIL_VALID_2 = "Test672@gmail.com";

    public static final String USER_MOBILE_VALID_3 = "6000000673";
    public static final String USER_EMAIL_VALID_3 = "Test673@gmail.com";

    public static final String USER_MOBILE_VALID_4 = "6000000674";
    public static final String USER_EMAIL_VALID_4 = "Test674@gmail.com";

    public static final String USER_MOBILE_NEW = "6000000679";
    public static final String USER_EMAIL_NEW = "Test679@gmail.com";

    public static final String USER_MOBILE_NEW_TXN = "6000000682";
    public static final String USER_EMAIL_NEW_TXN = "Test682@gmail.com";

    public static final String USER_MOBILE_BLOCKED = "6000000675";
    public static final String USER_MOBILE_BLACKLISTED = "6000000676";
    public static final String USER_MOBILE_LOCKED = "6000000677";
    public static final String USER_MOBILE_LOW_CREDIT_LIMIT = "6000000678";
    public static final String USER_MOBILE_MAXED_OUT = "6000000679";
    public static final String USER_MOBILE_INELIGIBLE = "6000000681";

    /**
     * Amount
     */
    public static String TRANSACTION_AMOUNT_DEFAULT = "1.23";
    public static String TRANSACTION_AMOUNT_HIGH = "500.01";
    public static String TRANSACTION_AMOUNT_MAX = "5000.0";

    /**
     * Merchant
     */
    public static String ACCESS_KEY_MPL_DEFAULT = "A1UGI1QJ1LKO2W0DLAE3";
    public static String ACCESS_KEY_MPL_EMAIL_ADDRESS_REQUIRED = "SEME7RBV6XELQE6GC1XV";

    /**
     * User Address Details
     */
    public static final String STREET_1 = "TestStreet1";
    public static final String STREET_2 = "TestStreet2";
    public static final String CITY = "TestCity";
    public static final String STATE = "TestState";
    public static final String COUNTRY = "TestCountry";
    public static final String ZIP = "560037";
    public static final String LANDMARK = "TestLandmark";
    public static final String RESIDENCE_TYPE = "RENTED";

    /**
     * Status, code, message etc
     */
    public static final String TRANSACTION_STATUS_AUTHORISED = "AUTHORISED";
    public static final String TRANSACTION_MESSAGE_AUTHORISED = "Transaction is authorised";

    public static final String REASON_ELIGIBLE = "Hola!! Avail Mpl Credit.";
    public static final String CODE_ELIGIBLE = "MPL_ELIGIBLE";

    /**
     * Miscellaneous
     */
    public static String CURRENCY = "INR";
    public static String TENANT_LAZYPAY = "LAZYPAY";
    public static String TENANT_MEESHO = "MEESHO";
    public static String PRODUCT_BNPL = "BNPL";
    public static String PRODUCT_COF = "COF";
    public static final String LIMIT_DEFAULT = "25000.0";
    public static final String DEVICE_ID = "1234567890";
    public static final String PLATFORM_WEB = "WEB";
    public static final String USER_IP_ADDRESS = "1.1.1.1";
    public static final String EXISTING_MTX = "8a9ff07d-c56f-4dae-9b04-bd11cf021306";

    @SneakyThrows
    @DataProvider(name = "mpl-user")
    public final static Object[][] getMPLUser()
    {
        return new Object[][] {
                {USER_MOBILE_VALID_1},
                {USER_MOBILE_VALID_2},
                {USER_MOBILE_VALID_3},
                {USER_MOBILE_VALID_4},
                {USER_MOBILE_MAXED_OUT},
                {USER_MOBILE_NEW_TXN},
                {USER_MOBILE_LOW_CREDIT_LIMIT}
        };
    }
}
