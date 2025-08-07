package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDocumentByIdPojo {
    public String responseStatus;
    public Object failureCode;
    public Object message;
    public DocumentPojo document;
}
