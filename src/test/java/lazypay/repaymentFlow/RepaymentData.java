package lazypay.repaymentFlow;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RepaymentData {

    String paymentMethod = "UPI";

    //NEGATIVE_TEST_NUMBER
    public static final String USER_MOBILE_NEGATIVE="6000000265";
    //UPI
    public static final String USER_MOBILE_UPI="6000000261";
    public static final String USER_TEST_VPA="6000000261@upi";
    public static final String BANK_SHORT_NAME="ICICI";
    public static final String USER_VPA="7003846020.lzp@idfcbank";
    //public static final String VALID_VPA_USER="7003846020";

    //NET_BANKING
    public static final String USER_MOBILE_NB="6000000264";

    //DEBIT_CARD
    public static final String USER_MOBILE_DC="6000000272";

    //CREDIT_CARD
    public static final String USER_MOBILE_CC="6000000262";

}
