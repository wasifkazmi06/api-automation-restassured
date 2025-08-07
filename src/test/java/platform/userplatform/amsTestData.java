package platform.userplatform;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class amsTestData {
    Faker faker = new Faker();
    public static final String LAZYPAY_PLATFORM="http://sboxplatform.lazypay.net";
    public static final String EXPIRY_TOKEN_PATH="/api/lazypay/platform/cron/expireOauthTokens";
    public static final String GET_USER_INFO_PATH="/api/lazypay/platform/um/getUserInfo";
    public static final String mobile = "6100000169";
    public static final String clientId = "n5uxkxcm1q-signin-new";
    public static final String clientSecret = "8cjyxu6xgiit0459c5js75yj76m5g2mh";
    public static final String clientId_One="xl90z39ch4-js-consumer";
    public static final String clientSecret_one="e1210c0ba6283bdecea1a9fe912142f1";
    public static final String clientId_Two="d97qoilpoy-signin";
    public static final String clientSecret_Two="efbcfdcf3c3fc0c2b8310f64555d8179";
    public static final String merchant_access_key_one="CCWHRSH9UULECPLQCFD2";
    public static final String merchant_access_key_two="LV0XS2E1ST1WXR7JL831";
    public static final String email = "";
    public static final String grantType = "";
    public static final String UUID = "8916897765326695958";
    public static final String accessKey = "LV0XS2E1ST1WXR7JL831";
    public static final String source = "CLINK_APP";
    public String mobileNumber = faker.number().digits(10);
    public String wrongMobileNumber = faker.phoneNumber().phoneNumber();
    public String wrongSource = "WRONG_APP";
    public String otp= faker.number().digits(6);
    public static String randomuuid= "9ad09cb0-a3e6-47f5-bbaf-8ce671150071";
    public static String CRON_PATH= LAZYPAY_PLATFORM+EXPIRY_TOKEN_PATH;
    public static String USER_INFO_PATH=LAZYPAY_PLATFORM+GET_USER_INFO_PATH;
    public static final String PLATFORM_LOGIN_PAYLOAD_FILE_PATH = "src/test/resources/ams/platformLoginPayload.txt";
    public static final String LAZYPAY_PLATFORM_ENDPOINT="api/lazypay/platform/users";
    public static boolean isOtpAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
    public static boolean isOtpOfFourDigits(String s){
        int count=0;
        char[] charArray=s.toCharArray();
        for(char ch:charArray){
            count++;
        }
        if(count==4)
            return true;
        else
            return false;
    }
    public static boolean isOtpContainsillegalCharacter(String s){
        for(int i=0; i<s.length(); i++) {
            if (s.charAt(i) == 'I' || s.charAt(i) == 'i' ||
                    s.charAt(i) == 'O' || s.charAt(i) == 'o') {
                System.out.println("OTP contains illegal charcter");
                break;
            }
        }
        return true;
    }
    public static boolean isOtpContainsSpecialCharacter(String s){
        Pattern p = Pattern.compile(
                "[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        boolean res = m.find();
        if(res)
            return true;
        else
            return false;
    }

}
