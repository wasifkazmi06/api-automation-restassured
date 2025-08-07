package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BifrostResolveStepsResponsePojo {
    public int onboardingCaseId;
    public String nextStep;
    public ArrayList<KYCStepsPojo> steps;
    public String status;
    public String product;
    public int creditLimit;
    public boolean limitUpdated;
}
