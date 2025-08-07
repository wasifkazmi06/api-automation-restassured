package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDocumentPojo {

    public String responseStatus;
    public String failureCode;
    public String message;
    public String s3_url;
    public Integer documentStoreId;

}
