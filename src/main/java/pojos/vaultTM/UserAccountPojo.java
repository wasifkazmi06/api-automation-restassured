package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountPojo {

    public String effectiveTimestamp;
    public String postingBatchId;
    public BucketDetailsPojo dpdCount;
    public BucketDetailsPojo monthlyLimit;
    public BucketDetailsPojo loanBlocked;
    public BucketDetailsPojo mad;
    public BucketDetailsPojo internalContra;
    public BucketDetailsPojo deposit;
    public BucketDetailsPojo defaultCommitted;
    public BucketDetailsPojo defaultPendingOutgoing;
    public BucketDetailsPojo defaultPendingIncoming;
    public BucketDetailsPojo salesCharged;
    public BucketDetailsPojo salesBilled;
    public BucketDetailsPojo salesUnpaid;
    public BucketDetailsPojo adjustSalesPrincipalCurrCycle;
    public BucketDetailsPojo adjustSalesPrincipalPrevCycle;
    public BucketDetailsPojo convenienceFeeCharged;
    public BucketDetailsPojo convenienceFeeBilled;
    public BucketDetailsPojo convenienceFeeUnpaid;
    public BucketDetailsPojo adjustConvenienceFeePrevCycle;
    public BucketDetailsPojo adjustConvenienceFeeCurrCycle;
    public BucketDetailsPojo lateFeePenaltyUnpaid;
    public BucketDetailsPojo lateFeePenaltyAccrue;
    public BucketDetailsPojo overdueInterestFeePenaltyUnpaid;
    public BucketDetailsPojo overdueInterestFeePenaltyAccrue;
    public BucketDetailsPojo nachBounceFeePenaltyUnpaid;
    public BucketDetailsPojo revolvePrincipalAuth;
    public BucketDetailsPojo r2ePrincipalCharged;
    public BucketDetailsPojo r2ePrincipalBilled;
    public BucketDetailsPojo r2ePrincipalUnpaid;
    public BucketDetailsPojo r2eInterestCharged;
    public BucketDetailsPojo r2eInterestBilled;
    public BucketDetailsPojo r2eInterestUnpaid;
    public BucketDetailsPojo adjustR2eInterestPrevCycle;
    public BucketDetailsPojo adjustR2eInterestCurrCycle;
    public BucketDetailsPojo txn2ePrincipalAuth;
    public BucketDetailsPojo txn2eInterestCharged;
    public BucketDetailsPojo txn2eInterestBilled;
    public BucketDetailsPojo txn2eInterestUnpaid;
    public BucketDetailsPojo txn2ePrincipalCharged;
    public BucketDetailsPojo txn2ePrincipalBilled;
    public BucketDetailsPojo txn2ePrincipalUnpaid;
    public BucketDetailsPojo adjustTxn2eInterestPrevCycle;
    public BucketDetailsPojo adjustTxn2eInterestCurrCycle;
    public BucketDetailsPojo lpBilledBalances;
    public BucketDetailsPojo lpChargedBalances;
    public BucketDetailsPojo lpUnpaidBalances;



}

