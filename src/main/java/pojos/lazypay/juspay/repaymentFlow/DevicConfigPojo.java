package pojos.lazypay.juspay.repaymentFlow;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevicConfigPojo {

    public String appVersion;
    public String baseURL;
    public String imageBaseURL;
    public String imageBaseURLv2;
    public boolean showAnimation;
    public ObjectNode locationSync;
    public ObjectNode contactSync;
    public ObjectNode notificationWindow;
    public ObjectNode permissionMatrix;
    public boolean showIntro;
    public String templateIntro;
    public boolean mandatoryLoginRequired;
    public String s3BaseUrl;
    public String irsBankListUrl;
    public boolean enableDeviceLock;
    public String upiRegex;
    public String panRegex;
    public boolean continueCLOnboarding;
    public JsonNode preLoginOnboardingSteps;
    public ObjectNode hypervergeInfo;
    public String UPGRADE;

}
