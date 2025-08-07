package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserbillsPojo {
    public int pageNum;
    public int pageSize;
    public String status;
    public int totalNumberOfElements;
    public int totalPages;
    public ArrayList<Elements> elements;
    public String errorCode;
}
