package pojos.billpayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONArray;

import java.util.ArrayList;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchBillerPojo {
    public int pageNum;
    public int pageSize;
    public int totalNumberOfElements;
    public int totalPages;
    public ArrayList<SearchBillerElement> elements;
    public String status;
    public String errorCode;
}
