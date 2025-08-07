package pojos.platform.getUserData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryMobile {
    private String value;
    private Boolean verified;
    private String source;
    private String verifiedDate;
}
