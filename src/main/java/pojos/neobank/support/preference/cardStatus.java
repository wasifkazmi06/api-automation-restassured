package pojos.neobank.support.preference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class cardStatus {

    public String freezeStatus;
    public String contactlessTransactionStatus;
    public String onlineTransactionStatus;
    public String posTransactionStatus;
    public String formFactor;
    public String physicalCardRequested;

}
