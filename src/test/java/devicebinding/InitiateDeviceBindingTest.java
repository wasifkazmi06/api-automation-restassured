package devicebinding;

import devicebinding.DeviceBindingSupportData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;

import org.testng.annotations.Test;


public class InitiateDeviceBindingTest extends DeviceBindingSupportData {

    @SneakyThrows
    @Test(priority = 1, enabled = true, groups = {"Device_Binding_Sanity"}, dataProvider = "initiateDeviceBindingData")
    @Feature("DeviceBinding")
    @Description("Initiate device binding API test cases ")
    public void InitiateDeviceBindingTests(String testcaseName) {

    }
}