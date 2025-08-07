package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingFee {
    public Double amount;
    public Double gst;
    public Double total;
}
