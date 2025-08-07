package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LoanDetailsPojo {
    private List<Data> data;
    private List<Integer> loan_ids;
    private Object pending_nach;
    private Map<Integer, String> product_schemes;
    private Map<Integer, String> product_codes;
    private List<LoanDetails> loan_details;
    private ErrorDetail error_detail;

    // Getters and Setters
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private Date processing_date;
        private String adjustment_this_month;
        private String received_settled_this_month;
        private Date due_date;
        private String status;
        private boolean under_process;
        private Integer previous_dpd;
        private Date date;
        private int boost_loan_id;
        private String emi;
        private String adjustment;
        private String payment;
        private String installment;
        private Date status_date;
        private String settled_amount;
        private String processing_payment;

        // Getters and Setters
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoanDetails {
        private int loan_id;
        private Date disbursal_date;
        private String disbursed_amount;
        private Date closed_at;
        private int tenure;

        // Getters and Setters
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorDetail {
        private String code;
        private String detail;
        private String attr;
    }
}