package pojos.neobank.cardControl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserTransactionLimitPojo {

    public int offlineMinLimit;
    public int onlineMinLimit;
    public int offlineMaxLimit;
    public int onlineMaxLimit;
    public int currentOnlineLimit;
    public int currentOfflineLimit;
}
