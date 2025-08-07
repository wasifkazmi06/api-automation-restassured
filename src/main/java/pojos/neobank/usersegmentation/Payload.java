package pojos.neobank.usersegmentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    public String tenantId;
    public String tenantName;

    public String clientId;
    public String clientName;

    public String userSegmentId;
    public String userSegmentName;

    public String uploadHistoryId;
    public String uploadStatus;
    public String uploadType;
    public String fileLink;
    public String createdBy;
    public String enableCTEvents;

}
