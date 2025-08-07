package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstallationUserData {
    public ArrayList<Object> devices;
    @JsonProperty("shared_users_details")
    public Object shared_users_details;
    @JsonProperty("device_shared_by_users")
    public Object device_shared_by_users;
    @JsonProperty("shared_device_user_count")
    public Object shared_device_user_count;
    @JsonProperty("devices_count_in_last_190days")
    public Object devices_count_in_last_190days;
    @JsonProperty("shared_device_disbursed_user_count")
    public Object shared_device_disbursed_user_count;
}
