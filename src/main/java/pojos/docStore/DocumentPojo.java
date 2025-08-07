package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentPojo {
    public int id;
    public String uuid;
    public String userId;
    public String documentType;
    public String extension;
    public int source;
    public int size;
    public String s3Url;
    public long dateCreated;
    public String metadata;
    public String dateUpdated;
}
