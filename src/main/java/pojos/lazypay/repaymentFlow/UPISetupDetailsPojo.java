package pojos.lazypay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UPISetupDetailsPojo {

        public String paymentMode;
        public String upiHandle;
        public boolean revokableByCustomer;
        public String pgName;

}
