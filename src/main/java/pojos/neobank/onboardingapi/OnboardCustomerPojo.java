package pojos.neobank.onboardingapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnboardCustomerPojo {
   public String timestamp;
   public Integer status;
   public String error;
   public String message;
   public String path;
   public String errorCode;
   public boolean success;
}
