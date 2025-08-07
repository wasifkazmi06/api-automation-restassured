package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FdVerifyUpi {

        public String status;
        public String message;


        public String upiId;
        public String umUuid;
        public String lpUuid;
        public String upiId2;
        public String mobile;

}
