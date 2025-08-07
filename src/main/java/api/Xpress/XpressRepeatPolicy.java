package api.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XpressRepeatPolicy {
    public String reason;
    public int creditLine;
    public boolean isApproved;
    public ArrayList<LenderPlan> lenderPlans;
    public double maxTenureIr;
}
