package util;

import java.nio.charset.Charset;
import java.util.Base64;

public class ReturnBase64 {
	
	public static String base64Encode(String val) {
	    byte[] encodedBytes = Base64.getEncoder().encode(val.getBytes());
	    return new String(encodedBytes, Charset.forName("UTF-8"));
	}
	public static String base64Decode(String val) {
	    try {
	        byte[] decodedBytes = Base64.getDecoder().decode(val.getBytes());
	        return new String(decodedBytes, Charset.forName("UTF-8"));
	    } catch (Exception e) {
	       // log.error("Error while decoding string: {}" + val, e);
	        return null;
	    }
	}

}
