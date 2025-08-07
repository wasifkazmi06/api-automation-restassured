package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class BilldetailCustomerparam {

    public String dataType;
    public boolean optional;
    public String paramName;
    public int minLength;
    public int maxLength;
    public Object regex;
    public Object values;
    public Object requiredIn;
}
