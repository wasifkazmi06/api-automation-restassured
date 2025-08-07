package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLoanPlansResponsePojo {



    public Double maxAmount;
    public Double minAmount;
    public Double maxCreditLine;
    public Double userModelCreditLimit;
    public Double preApprovedLineCreditLimit;
    public String applicationReopened;
    public boolean applicationCancelled;
    public boolean maskedErrorCode;
    public ArrayList<Plans> plans;
    public ArrayList<Object> pfRateMap;
    public String timestamp;
    public Double status;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
}
