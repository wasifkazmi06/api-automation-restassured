package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JourneyCheckpointGroups {

    public String groupName;
    public ArrayList<JourneyCheckpointSubGroups> journeyCheckpointSubGroups;
    public boolean completed;
    public boolean highlighted;
}
