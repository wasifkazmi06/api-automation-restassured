package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckRefRequiredCountResponsePojo {
    public ArrayList<Object> references;
    public int requiredReferenceCount;
    public int requiredVerifiedReferenceCount;
}
