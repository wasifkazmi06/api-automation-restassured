package pojos.Xpress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchUserStatusResponsePojo {
    public String blockReason;
    public String dueDate;
    public String dueSince;
    public Double availableCreditLimit;
    public Double availableCycleLimit;
    public Boolean billOverdue;
    public Double lastBilledAmount;
    public long isTransacted;
    public Double creditLimit;
    public Double cycleLimit;
    public Double totalTermLimit;
    public Double availableTermLimit;
    public String identifier;
    public Boolean optedFor30Days;
    public Double totalOutstanding;
    public Boolean blocked;
    public static Object r2EmiTenure;
    public Double timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
}
