package pojos.kyc.apis;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class DocumentsPendingPojo {
    public Object id;
    public String docTypeId;
    public String status;
    public String url;
    public String message;
    public List<String> mimeType;
    public String displayText;
    public long order;
    public List<FieldPojo> fields;
    public String documentName;
    public boolean disableUpload;
    public Object rejectMessage;
    public Object metadata;
    public boolean reUploadable;
    public String maxSizeInKB;
}
