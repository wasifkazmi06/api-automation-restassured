package pojos.neobank.support.transcation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    public Transaction transaction;
    public Object balance;
}
