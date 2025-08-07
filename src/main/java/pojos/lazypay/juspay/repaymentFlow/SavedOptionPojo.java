package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SavedOptionPojo {

    public List<CardDetailsPojo> cards;
    public List<String> vpaList;
    public String id;
    public ObjectNode otherPaymentOptions;
    public ObjectNode userPreferredPaymentMethod;
    public ObjectNode userSavedPaymentMethod;
    public int isClEnabled;
    public int isRevolveEligible;
    public int isPartialRepaymentEligible;
    public int showSavedCCBannerForRevolveUser;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;

    public String paymentMethod;
    public String bankCode;
    public String userIdentifier;
    public CardDetailsPojo card;
    public String vpa;
    public String bankName;


    public String cardName;
    public String cardMode;
    public String cardType;
    public String nameOnCard;
    public String cardNo;
    public String isExpired;
    public String cardBrand;
    public String cardToken;
    public String debitInfo;
    public String cardDisplayName;
    public String userSegment;
    public Object surchargeApplicableMethods;

}
