package pojos.chatbot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartSessionPojo{
    public String requestId;
    public String statusCode;
    public Data data;
}