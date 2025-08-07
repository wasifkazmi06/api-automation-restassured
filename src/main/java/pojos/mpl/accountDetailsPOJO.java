package pojos.mpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class accountDetailsPOJO {

    public String accountStatus;
    public Double availableLimit;
    public Double creditLimit;
    public Double totalDue;
    public Double totalOutstanding;
    public Double blockedAmount;
    public Double penalty;
    public Double lateFees;

    public Long timestamp;
    public int status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}
