package pojos.Xpress;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyOverviewResponsePojo {


    public ArrayList<JourneyCheckpointGroups> journeyCheckpointGroups;
    public Loan loan;
    public String loanApplicationStatus;
    public String nextCheckpoint;

    public String timestamp;
    public Double status;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
}
