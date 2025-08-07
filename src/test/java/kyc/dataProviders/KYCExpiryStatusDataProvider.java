package kyc.dataProviders;

import org.testng.annotations.DataProvider;

public class KYCExpiryStatusDataProvider {
    @DataProvider
    public Object[][] KYCExpiryStatusDataProvider() {
        return new Object[][]{
                {"KYCExpired","5396701987804809311",200,"EXPIRED"},
                {"KYCNotExpired","5189699679495393484",200,"NOT_EXPIRED"},
                {"KYCAboveToExpire","5149114340397614029",200,"ABOUT_TO_EXPIRE"},
                {"KYCUserNotFound","539670198",400,"400"},
                {"KYCRequestWithoutPathParam","",404,"NOT_EXPIRED"},

        };
    }
}
