package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestData {

    private String lpg_id;
    private String tel_no;
    private String city;
    private String electricity_consumer_id;
    private String png_consumer_id;
    private String bp_no;

}
