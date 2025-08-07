package heimdall.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import neo.NeoConstants;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Utility {


    public JsonObject getResponse(String testcase) throws FileNotFoundException {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(new FileReader(NeoConstants.APV_HEIMDALL_ERROR_JSON));
        JsonObject obj = jsonObject.getAsJsonObject(testcase);
        return obj;
    }





}
