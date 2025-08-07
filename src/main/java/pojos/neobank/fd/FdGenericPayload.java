package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class FdGenericPayload {

    // for fd queue
    public int queueValue;
    public String userStatus;
    public boolean minKycBlockEnabled;
    public String sbmKycStatus;
    public int cibilScore;
    public String landingScreenName;
    public int fdInterest;

    public int investedAmount;
    public double currentAmount;
    public String fdStatus;
    public double interestEarned;
    public double interestRate;
    public long createdOn;
    public String remainingTenure;

    public NomineeDetailsPayload nomineeDetailsResponse;

    // for fd create order
    public String keyId;
    public String orderId;
    public int amount;
    public String currency;
    public int creditLimit;

    //for fd summary details
    public double maturedAmount;
    public long earliestMaturity;

    //for interest details
    public String creditLimitRatio;
    public String compoundInterestFormulae;
    public int minFdAmount;
    public boolean popUpEnabled;
    public boolean unsecuredCardRestricted;
    public double penaltyInterestRate;
    public long maturityDate;

    //for nominee details
    public String name;
    public long dateOfBirth;
    public String relationship;

    //for fd progress
    public String cardStatus;
    public int maxCreditLimitForTopUp;
    public int maxCreditLimitForHomeBanner;
    public int totalFdBooked;

    // for verified UPI List
    public ArrayList<String> upiList;

    //for removing from redis
    public String payload;

    public String status;
}
