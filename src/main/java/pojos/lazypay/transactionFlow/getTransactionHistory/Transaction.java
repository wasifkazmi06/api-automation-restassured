package pojos.lazypay.transactionFlow.getTransactionHistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    private String id;
    private Double amount;
    private String type;
    private String status;
    private String paymentMode;
    private Long dateCreated;
    private Long dateUpdated;
    private Integer merchantId;
    private String merchantName;
    private Boolean isDebit;
    private Object loanTxnId;
    private Object merchantLabel;
    private Object subMerchantId;
    private Object subMerchantTxnId;
    private Object isEligibleForEmi;
    private String transactionTrackFlag;
    private String logoPath;

}
