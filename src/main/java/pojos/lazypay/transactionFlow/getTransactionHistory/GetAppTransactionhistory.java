package pojos.lazypay.transactionFlow.getTransactionHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@Setter
public class GetAppTransactionhistory {
    private Integer totalTransactions;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private List<Transaction> transactions = new ArrayList<Transaction>();
}
