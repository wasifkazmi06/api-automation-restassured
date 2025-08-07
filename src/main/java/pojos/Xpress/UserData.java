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
public class UserData {
    public Object age;
    public String pan;
    public String email;
    public Object loans;
    public String phone;
    public Object amount;
    public Object bureau;
    public String gender;
    public Object status;
    public Address address;
    public String platform;
    @JsonProperty("ten_lakh")
    public Object ten_lakh;
    @JsonProperty("bnpl_data")
    public Object bnpl_data;
    @JsonProperty("last_name")
    public String last_name;
    public Employment employment;
    @JsonProperty("first_name")
    public String first_name;
    @JsonProperty("bureau_type")
    public Object bureau_type;
    @JsonProperty("is_upi_user")
    public boolean is_upi_user;
    @JsonProperty("middle_name")
    public Object middle_name;
    @JsonProperty("pan_details")
    public Object pan_details;
    @JsonProperty("source_name")
    public String source_name;
    @JsonProperty("bank_account")
    public Object bank_account;
    @JsonProperty("cbp_consents")
    public ArrayList<Object> cbp_consents;
    @JsonProperty("channel_type")
    public String channel_type;
    @JsonProperty("save_consent")
    public Object save_consent;
    @JsonProperty("step_up_data")
    public Object step_up_data;
    @JsonProperty("date_of_birth")
    public String date_of_birth;
    @JsonProperty("is_subvention")
    public Object is_subvention;
    @JsonProperty("merchant_data")
    public Object merchant_data;
    @JsonProperty("top_up_intent")
    public Object top_up_intent;
    @JsonProperty("has_past_fraud")
    public boolean has_past_fraud;
    @JsonProperty("master_user_id")
    public int master_user_id;
    @JsonProperty("proximity_data")
    public Object proximity_data;
    @JsonProperty("is_no_bnpl_user")
    public Object is_no_bnpl_user;
    @JsonProperty("is_perfios_done")
    public boolean is_perfios_done;
    @JsonProperty("is_HT_enabled_dsa")
    public Object is_HT_enabled_dsa;
    @JsonProperty("origin_of_request")
    public Object origin_of_request;
    @JsonProperty("permanent_address")
    public PermanentAddress permanent_address;
    @JsonProperty("pre_approved_data")
    public Object pre_approved_data;
    @JsonProperty("raw_bureau_string")
    public Object raw_bureau_string;
    @JsonProperty("application_status")
    public Object application_status;
    @JsonProperty("is_bulk_assessment")
    public Object is_bulk_assessment;
    @JsonProperty("owned_house_address")
    public Object owned_house_address;
    @JsonProperty("xpress_loan_details")
    public Object xpress_loan_details;
    @JsonProperty("perfios_attempt_count")
    public int perfios_attempt_count;
    @JsonProperty("statement_dedupe_data")
    public Object statement_dedupe_data;
    @JsonProperty("credit_risk_parameters")
    public Object credit_risk_parameters;
    @JsonProperty("installation_user_data")
    public InstallationUserData installation_user_data;
    @JsonProperty("rate_cut_interest_rate")
    public Object rate_cut_interest_rate;
    @JsonProperty("co_lender_rejected_flag")
    public Object co_lender_rejected_flag;
    @JsonProperty("is_lazypay_organic_user")
    public Object is_lazypay_organic_user;
    @JsonProperty("is_perfios_fcu_detected")
    public Object is_perfios_fcu_detected;
    @JsonProperty("existing_disbursed_loans")
    public ArrayList<ExistingDisbursedLoan> existing_disbursed_loans;
    @JsonProperty("is_payufin_kyc_completed")
    public Object is_payufin_kyc_completed;
    @JsonProperty("shared_device_user_count")
    public Object shared_device_user_count;
    @JsonProperty("is_salary_detected_by_ops")
    public Object is_salary_detected_by_ops;
    @JsonProperty("income_verification_method_type")
    public Object income_verification_method_type;
    @JsonProperty("perfios_statement_verification_status")
    public String perfios_statement_verification_status;
}
