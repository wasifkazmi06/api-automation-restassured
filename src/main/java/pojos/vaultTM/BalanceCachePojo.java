package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceCachePojo {

    public String balanceEventTimestamp;
    public BucketDetailsPojo dpdCount;
    public BucketDetailsPojo defaultCommitted;
    public BucketDetailsPojo defaultPendingOutgoing;
    public BucketDetailsPojo defaultPendingIncoming;
    public BucketDetailsPojo revolveLoanEmiPrincipalCharged;
    public BucketDetailsPojo revolveLoanEmiPrincipalBilled;
    public BucketDetailsPojo revolveLoanEmiInterestBilled;
    public BucketDetailsPojo revolveLoanEmiInterestCharged;
    public BucketDetailsPojo revolveLoanEmiPrincipalUnpaid;
    public BucketDetailsPojo revolveLoanEmiInterestUnpaid;
    public BucketDetailsPojo monthlyLimit;

}

