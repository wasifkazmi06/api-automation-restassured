package pojos.Xpress;

import api.Xpress.ChildData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootData {
    public Object createdAt;
    public String assessmentRequestId;
    public String assessmentLeadId;
    public String product;
    @JsonProperty("data")
    public ChildData data;
    public String assessmentType;
    public ApplicationStatusChange applicationStatusChange;
}
