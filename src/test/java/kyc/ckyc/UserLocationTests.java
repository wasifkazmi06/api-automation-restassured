package kyc.ckyc;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

@Slf4j
public class UserLocationTests extends CommonMethodsImpl {
    public UserLocationTests() throws Exception {
    }

    @Feature("api/kycEngine/user-lat-long tests")
    @Description("To verify user location capturing with /user-lat-long API")
    @Test(priority = 1, dataProvider = "PAN-DOC Testdata", dataProviderClass = BaseTestData.class)
    public void uploadDocumentsV3PanDoc(String panCase, String panData) throws Exception{}
}
