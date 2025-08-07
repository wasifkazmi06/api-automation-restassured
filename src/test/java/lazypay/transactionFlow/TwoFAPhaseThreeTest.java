package lazypay.transactionFlow;

import deviceInfo.DeviceInfo;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.userplatform.UserPlatformSupportData;

import java.util.HashMap;

import static deviceInfo.DeviceInfo.checkExistenceAPIResponsePojo;
import static deviceInfo.DeviceInfoData.POLICYID1;
import static deviceInfo.DeviceInfoData.POLICYID2;
import static lazypay.MakeTransaction.*;
import static lazypay.transactionFlow.GetOTPID.getAppOTPPojo;
import static lazypay.transactionFlow.TransactionData.*;

public class TwoFAPhaseThreeTest extends UserPlatformSupportData {

    public TwoFAPhaseThreeTest() throws Exception {
        super();
    }


}