package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetPayBillViewPojo {

    public String UPGRADE;
    public int clEnabled;
    public boolean ccrepaymentEligible;
    public String mdState;
    public String mdType;
    public boolean partialRepaymentEligible;
    public boolean revolveEligible;
    public String screenBGColor;
    public String screenId;
    public String screenName;
    public String screenS3Url;
    public String screenType;
    public String signature;
    public String staticScreen;
    public String userIdentifier;
    public ObjectNode amountBreakUpWidgetGroup;
    public ObjectNode amountSection;
    public ObjectNode bottomSheetWidget;
    public ObjectNode button;
    public ObjectNode creditInfo;
    public ObjectNode dueBillRadioButton;
    public ObjectNode headerSubtitle;
    public ObjectNode headerTitle;
    public ObjectNode headerWidget;
    public ObjectNode initiatePayload;
    public ObjectNode preferredPaymentOption;
    public ObjectNode preferredPaymentTitle;
    public ObjectNode radioButtonGroupTitle;
    public ObjectNode screenBGImage;
    public ObjectNode sectionTitle;
    public ObjectNode seekWidget;
    public ObjectNode sliderInfo;
    public ObjectNode totalOutstandingRadioButton;
    public String title;
    public String description;
    public String type;
    public String userSegment;
    public String surchargeApplicableMethods;
    public String timestamp;
    public String status;
    public String activeCofLoan;
    public String message;


}
