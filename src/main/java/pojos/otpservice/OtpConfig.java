package pojos.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpConfig {
    public String otpType;
    public Integer otpLength;
    public Integer otpExpiryInSeconds;
    public Integer minimumNumberOfDigits;
    public Integer minimumNumberOfAlphabets;
    public String excludeDigits;
    public String excludeAlphabets;
}
