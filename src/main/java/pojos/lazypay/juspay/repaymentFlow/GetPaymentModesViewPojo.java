package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Getter
@Setter
public class GetPaymentModesViewPojo {

    public String screenId;
    public String screenName;
    public String screenType;
    public int savedOptionsToBeDisplayed;
    public boolean clEnabled;
    public boolean revolveEligible;
    public boolean staticScreen;
    public ObjectNode headerTitle;
    public ObjectNode headerSubtitle;
    public JsonNode savedPaymentOptionsCardGroup ;
    public JsonNode otherPaymentOptionsCardGroup;
  //  public ObjectNode preferredPaymentTitle;
    public ObjectNode upiPaymentOptionGroup;
    public ObjectNode savedOptionsGroupTitle;
    public ObjectNode otherOptionsGroupTitle;
    public ObjectNode savedOptionsGroupAssistedText;
    public ObjectNode textWithIconWidget;
    public String userSegment;
    public String title;
    public String surchargeApplicableMethods;

}
