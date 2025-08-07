package pojos.platform.getEligibleProducts;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EligibleProductsList {
    public String type;
    public boolean decision;
    public Date updatedDate;
    public Object value;
}
