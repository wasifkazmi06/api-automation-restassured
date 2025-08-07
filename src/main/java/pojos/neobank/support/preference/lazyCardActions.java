package pojos.neobank.support.preference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class lazyCardActions {


    public Object exception;
    public Object pagination;
    public String result;

    public String timestamp;
    public int status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}
