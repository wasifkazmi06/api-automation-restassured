package api.upi.registration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.registration.RegistrationStatusPojo;
import java.util.HashMap;
import java.util.Map;

public class RegistrationStatus extends BaseAPI<RegistrationStatusPojo>{

        public RegistrationStatus() throws Exception {
            super(Uri.REGISTRATION_STATUS, RegistrationStatusPojo.class);
        }

        @Step
        public RegistrationStatusPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
            return super.get(queryParamDetails, headerDetails);
        }
}
