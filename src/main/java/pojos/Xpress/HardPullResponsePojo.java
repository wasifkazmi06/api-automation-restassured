package pojos.Xpress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HardPullResponsePojo {
    public Long timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;
}
