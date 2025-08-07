package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityList {

    private String requestId;
    private String Source;
    private String status;
    private List<String> data;
    private String errorCode;
    private String errorMsg;
}
