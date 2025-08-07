package pojos.platform.getUserData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Getter
@Slf4j
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserTnC {
    private Boolean signed;
    private String signedDate;
    private Integer version;
    private String scope;
    private String source;
    private List<Document> documents = new ArrayList<Document>();
    private String type;
    private String updatedDate;
}
