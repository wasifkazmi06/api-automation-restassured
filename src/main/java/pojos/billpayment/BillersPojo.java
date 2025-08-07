package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class BillersPojo {

    public Billers billers;
    public String recentBillers;
    public int status;
    public String error;
    public String errorCode;
    public String message;
    public String path;
    public String source;

}
