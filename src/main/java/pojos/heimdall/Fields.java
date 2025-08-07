package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fields {
    private String name;
    private String code;
    private String format;
    private String inputType;
    private int inputMaxLength;
    private int inputMinLength;
    private boolean optional;


}
