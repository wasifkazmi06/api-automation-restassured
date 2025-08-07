package pojos.bnplRisk.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class RiskPLSetWhitelistLimitV1RequestPojo {

    private String mobile;
    private String reason;
    private String panHash;
    private ProductLimits productLimits;

    @Getter
    @Setter
    @JsonIgnoreProperties
    public static class ProductLimits {
        private Limit limit;
        private String reason;
        private Boolean decision;
        private InterestRate interestRates;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties
    public static class Limit {
        private Double limitRisk1;
        private Double limitRisk2;
        private Double limitRisk3;
        private Double limit;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties
    public static class InterestRate {
        private Integer tenure;
        private Double interestRate;
    }
}