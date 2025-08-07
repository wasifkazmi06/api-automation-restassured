package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderPayload {

    public String name;
    public String keyId;
    public String orderId;
    public int amount;
    public String currency;
    public int creditLimit;
}
