package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class UserLimitFetchResponse {

    public Map<String, Map<String, String>> result;
    public String requestId;
}
