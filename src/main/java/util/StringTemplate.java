package util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONObject;
@Slf4j
public class StringTemplate extends JSONObject {
    private String fileRelativePath;
    private HashMap<String, String> substitute;
    private HashMap<String, Double> substituteDouble;

    public StringTemplate() {
        this.fileRelativePath = "";
        this.substitute = new HashMap<>();

    }

    /***
     *
     * @param fileRelativePath
     */
    public StringTemplate(String fileRelativePath) {
        this.fileRelativePath = fileRelativePath;
        this.substitute = new HashMap<>();
        this.substituteDouble = new HashMap<>();
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public StringTemplate replace(String key, String value){
        substitute.put(key, value);
        return this;
    }

    /**
     *
     * @return
     */
    public String toString(){
        String stringContent;

        try {

            if(this.fileRelativePath.isEmpty()) {
                // Construct the JSON String
                ObjectMapper objectMapper = new ObjectMapper();
                stringContent = objectMapper.writeValueAsString(substitute);
            }
            else {
                // Read the File and convert the file content to String
                stringContent = ReadProperties.readFile(fileRelativePath);

                // Override defaults
                StringSubstitutor sub = new  StringSubstitutor(substitute);
                stringContent = sub.replace(stringContent);
            }

        } catch (IOException e) {
            log.error(" FAILED to retrieve the file. PLEASE CHECK THE FILENAME or FILE PATH");
            stringContent = null;
        }

        return stringContent;
    }

}