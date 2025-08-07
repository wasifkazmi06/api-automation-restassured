package pojos.chatbot;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Map;


@Getter
@Setter
public class VariablesPojo {
    public String variableName;
    public ArrayList<String> variableValues;
    public Map<String, Object> metaData;
}
