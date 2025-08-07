package pojos.platform.getEligibleProducts;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetProductsResponsePojo {

    public String userId;
    public String mobile;
    public ArrayList<EligibleProductsList> eligibleProducts;

}
