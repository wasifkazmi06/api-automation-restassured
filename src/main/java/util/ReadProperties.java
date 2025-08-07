package util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import constants.UtilConstants;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ReadProperties {
    public static Map<String, Object> testConfigMap = new HashMap<String, Object>();

    /**
     * read pron config file store in map
     * @param env
     * @param propertyFileLocation
     * @return
     */
    private static Map<String, Object> readPropertyFile(String env, String propertyFileLocation){
        InputStream propertyFileReader = null;
        Properties environmentProperties = new Properties();
        try {
            InputStream is = ReadProperties.class.getResourceAsStream("/" + propertyFileLocation);
            environmentProperties.load(is);
        } catch (IOException e) {
            log.error("Unable to read the property file", e);
        }
        Map<String, Object> map = (Map)environmentProperties;
        return map;
    }

    /***
     * create URL
     * @param apiUrl
     * @return
     * @throws Exception
     */
    public static String extractBeUrl(String apiUrl) throws Exception {
        ReadProperties.intializeTestConfig(System.getProperty("env"));

    String whichUrl = apiUrl.substring(0, apiUrl.indexOf("/"));
        log.info("TRYING BE URL : " + whichUrl);
        switch(whichUrl){
            case "LAZYCARD_SANDBOX":
                return testConfigMap.get(UtilConstants.LAZYCARD_SANDBOX).toString();
            case "WEBAPP":
                return testConfigMap.get(UtilConstants.WEBAPP).toString();
            case "SECUREAPP":
                return testConfigMap.get(UtilConstants.SECUREAPP).toString();
            case "LAZYPAY_BASEURL_PLATFORM":
                return testConfigMap.get(UtilConstants.LAZYPAY_BASEURL_PLATFORM).toString();
            case "OTP_BASEURL":
                return testConfigMap.get(UtilConstants.OTP_BASEURL).toString();
            case "LAZYPAY_BASEURL_KYC":
                return testConfigMap.get(UtilConstants.LAZYPAY_BASEURL_KYC).toString();
            case "CREDIT_LINE":
                return testConfigMap.get(UtilConstants.CREDIT_LINE).toString();
            case "UPI":
                return testConfigMap.get(UtilConstants.UPI).toString();
            case "LAZYPAY_BASEURL_CRON":
                return testConfigMap.get(UtilConstants.LAZYPAY_BASEURL_CRON).toString();
            case "FD_SERVICE":
                return testConfigMap.get(UtilConstants.FD_SERVICE).toString();
            case "PLATFORM_SERVICE":
                return testConfigMap.get(UtilConstants.PLATFORM_SERVICE).toString();
            case "USER_REGISTRATION_SERVICE":
                return testConfigMap.get(UtilConstants.USER_REGISTRATION_SERVICE).toString();
            case "MBE":
                return testConfigMap.get(UtilConstants.MBE).toString();
            case "DCS_SERVICE":
                return testConfigMap.get(UtilConstants.DCS_SERVICE).toString();
            case "USER_SEGMENTATION_SERVICE":
                return testConfigMap.get(UtilConstants.USER_SEGMENTATION_SERVICE).toString();
            case "DOC_STORE_SERVICE":
                return testConfigMap.get(UtilConstants.DOC_STORE_SERVICE).toString();
            case "STG_WEBAPP":
                return testConfigMap.get(UtilConstants.STG_WEBAPP).toString();
            case "HEIMDALL_SERVICE":
                return testConfigMap.get(UtilConstants.HEIMDALL_SERVICE).toString();
            case "VAULT":
                return testConfigMap.get(UtilConstants.VAULT).toString();
            case "GRINGOTTS":
                return testConfigMap.get(UtilConstants.GRINGOTTS).toString();
            case "USER_REGISTRATION":
                return testConfigMap.get(UtilConstants.USER_REGISTRATION).toString();
            case "OTP_SERVICE_BASE_URL":
                return testConfigMap.get(UtilConstants.OTP_SERVICE_BASE_URL).toString();

            case "BILLPAY_URI":
                return testConfigMap.get(UtilConstants.BILLPAY_URI).toString();

            case "BIFROST_SERVICE_BASE_URL":
                return testConfigMap.get(UtilConstants.BIFROST_SBOX).toString();
            case "CIBIL_CBP_MOCKING":
                return testConfigMap.get(UtilConstants.CIBIL_CBP_MOCKING).toString();
            case "HARD_PULL_MOCKING_URL":
                return testConfigMap.get(UtilConstants.HARD_PULL_MOCKING_URL).toString();
            case "AMS_BASE_URL":
                return testConfigMap.get(UtilConstants.AMS_BASE_URL).toString();
            case "CREDIT_EXPIRE_BASE_URL":
                return testConfigMap.get(UtilConstants.CREDIT_EXPIRE_BASE_URL).toString();
            case "PAYSENSE_BASE_URL":
                return testConfigMap.get(UtilConstants.PAYSENSE_BASEURL).toString();
            case "SHYLOCK":
                return testConfigMap.get(UtilConstants.REGISTER_USER_SHYLOCK).toString();
            case "CHAT_BOT":
                return testConfigMap.get(UtilConstants.CHAT_BOT).toString();
            case "DEVICE_BINDING":
                return testConfigMap.get(UtilConstants.DEVICE_BINDING_V2).toString();
            case "RISK_UPDATE_CREDIT_LIMIT":
                return testConfigMap.get(UtilConstants.RISK_UPDATE_CREDIT_LIMIT).toString();
            case "PAYMENTS_BASE_URL":
                return testConfigMap.get(UtilConstants.PAYMENTS_BASE_URL).toString();
            case "COLLECTION":
                return testConfigMap.get(UtilConstants.COLLECTION).toString();
            case "KYC_PLATFORM_SERVICE_BASE_URL":
                return testConfigMap.get(UtilConstants.KYCPlatform_URI).toString();
            case "BNPL_RISK":
                return testConfigMap.get(UtilConstants.BNPL_RISK).toString();
            case "BNPL_RISK_PL":
                return testConfigMap.get(UtilConstants.BNPL_RISK_PL).toString();
            case "BNPL_FRAUD":
                return testConfigMap.get(UtilConstants.BNPL_FRAUD).toString();
            case "ARTHAMATICS_BASE_URL":
                return testConfigMap.get(UtilConstants.ARTHAMATICS_BASE_URL).toString();
            case "ASSESSMENT_ENGINE_BASE_URL":
                return testConfigMap.get(UtilConstants.ASSESSMENT_ENGINE_BASE_URL).toString();
            case "PORTFOLIO_SCRUB_MOCKING":
                return testConfigMap.get(UtilConstants.PORTFOLIO_SCRUB_MOCKING).toString();
                default :

                log.error(" BE URL : " + whichUrl + " - Not Found!");
                throw new Exception(" BE URL : " + whichUrl + " - Not Found!");
        }
    }

    public static String extractUri(String apiUrl){
        return apiUrl.substring(apiUrl.indexOf("/"), apiUrl.length() );
    }

    /**
     * initialise config
     * @param env
     */
    public static void intializeTestConfig (String env)  {
        final String PROP_FILE = "config_"+env+".properties";
        log.info(PROP_FILE);
        testConfigMap = readPropertyFile(env, PROP_FILE);
        testConfigMap.put("test.env", env);
    }

    /**
     * read file
     * @param inputFile
     * @return
     * @throws IOException
     */
    public static String readFile(String inputFile) throws IOException {
        return FileUtils.readFileToString(new File(inputFile), "UTF-8");
    }

    /**
     * read property
     * @param inputPropertyFile
     * @return
     * @throws IOException
     */
    public static Properties readPropertyFile(String inputPropertyFile) throws IOException {
        Properties propReader = new Properties();

        try(FileReader fr = new FileReader(inputPropertyFile)) {
            propReader.load(fr);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        return  propReader;
    }
    @SneakyThrows
    public static String selectComponent(String apiUrl){
        String module = apiUrl.substring(0, apiUrl.indexOf("_")).toLowerCase();
        switch(module){
            case UtilConstants.LAZYCARD_COMPONENT:
                return UtilConstants.LAZYCARD_COMPONENT;
            case UtilConstants.WEBAPP_COMPONENT:
                return UtilConstants.WEBAPP_COMPONENT;
            case UtilConstants.SECUREAPP_COMPONENT:
                return UtilConstants.SECUREAPP_COMPONENT;
            default:
                log.error(" Module : " + module + " - Not Found! returning Default component");
                return "LAZYPAY";
        }
    }
}