package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepeatAssessmentRequestPojo {
    public RepeatAssessmentRequestPojo(){

    }
    public Data data;
    @JsonProperty("assessment_lead_id")
    public String assessment_lead_id;
}
