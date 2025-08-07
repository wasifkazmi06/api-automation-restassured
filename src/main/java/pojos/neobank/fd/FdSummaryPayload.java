package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FdSummaryPayload
{
    public double maturedAmount;
    public int investedAmount;
    public long earliestMaturity;
    public double interestEarned;
}
