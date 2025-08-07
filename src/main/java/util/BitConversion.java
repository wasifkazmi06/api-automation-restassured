package util;

import java.io.IOException;
import java.util.Base64;
//import groovy.util.logging.Slf4j;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitConversion {

    public static String bitEncoder(String strToEncode){
      try{
        // Encoding string
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(strToEncode.getBytes());
        return encodedString;
    } catch (Exception e) {
        log.error("Unable to encode String", e);
        return null;
    }
    }

    public static String bitDecoder(String strToDecode) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            // Decoding string
            String decodedString = new String(decoder.decode(strToDecode));
            return decodedString;
        } catch (Exception e) {
            log.error("Unable to encode String", e);
            return null;

        }
    }
}
