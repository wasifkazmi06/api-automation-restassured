package pojos.kyc.apis;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class FieldPojo {
    public String key;
    public String value;
    public String displayText;
    public String format;
    public String hint;
    public String inputType;
    public int order;
    public int inputMaxLength;
    public int inputMinLength;
    public boolean editable;
    public String detailsType;
    public boolean optional;
    public boolean displayTick;
    public List<String> options;
}
