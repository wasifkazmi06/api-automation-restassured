package util;

import java.util.HashMap;
import java.util.Map;

public class ReturnMappedValues {


    public static Map<String, Object> ReturnMappedValuesMethod(String[] key, String[] value) {
        int i=0;
        int MapLength = key.length;
        Map<String, Object> map = new HashMap<>();
        for(; i<MapLength; i++)
        {
            map.put(key[i], value[i]);
        }
        return map;
    }
}



