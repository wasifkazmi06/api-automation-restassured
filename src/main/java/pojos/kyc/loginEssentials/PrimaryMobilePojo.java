package pojos.kyc.loginEssentials;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter


public class PrimaryMobilePojo {
        public String value;
        public boolean verified;
        public String source;
        public Date verifiedDate;

}
