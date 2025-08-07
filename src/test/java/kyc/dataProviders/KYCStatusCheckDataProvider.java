package kyc.dataProviders;

import kyc.BaseTestData;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

public class KYCStatusCheckDataProvider {
    @DataProvider
    public Object[][] KycStatusDataProvider() {
        return new Object[][]{
                {"ValidQueryParamter", BaseTestData.TEST_USER_UUID, BaseTestData.VALID_PRODUCT, "DOCUMENTS_PENDING"},
                {"InvalidQueryParamters", generateRandomString(), generateRandomString(), "USER_NOT_FOUND"},
                {"InvalidUuid", generateRandomString(), BaseTestData.VALID_PRODUCT, "USER_NOT_FOUND"},
                {"InvalidProduct", BaseTestData.TEST_USER_UUID, generateRandomString(), "INVALID_PRODUCT_TYPE"},
                {"NullProduct", BaseTestData.TEST_USER_UUID, "null", "INVALID_PRODUCT_TYPE"},
                {"NullUuid", "null", BaseTestData.VALID_PRODUCT, "USER_NOT_FOUND"},
                {"NullQueryParameters", "null", "null", "USER_NOT_FOUND"}

        };
    }
public String generateRandomString(){
        return RandomStringUtils.randomAlphanumeric(10);
}
}
