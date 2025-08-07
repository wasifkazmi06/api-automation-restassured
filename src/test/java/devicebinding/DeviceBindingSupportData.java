package devicebinding;

import org.testng.annotations.DataProvider;
import platform.userplatform.amsTestData;

public class DeviceBindingSupportData {



    @DataProvider(name = "initiateDeviceBindingData")
    public Object[][] initiateDeviceBindingData() {
        return new Object[][]{
                // TestCaseName,
                {"ValidDeviceBinding"},

        };
    }
}
