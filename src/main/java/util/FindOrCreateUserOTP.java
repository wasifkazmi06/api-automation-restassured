package util;

import api.lazypay.transaction.FindUserOTP;
import api.lazypay.transaction.OauthToken;
import api.platform.FOCPlatform;
import api.userRegistration.FetchOTP;
import constants.UtilConstants;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import pojos.lazypay.transactionFlow.FindUserOTPPojo;
import pojos.lazypay.transactionFlow.OAuthTokenPojo;
import pojos.platform.FOCPlatformPojo;
import pojos.userRegistration.FetchOTPPojo;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FindOrCreateUserOTP {

	public static final String OTP_REQUEST="src/test/resources/lazypay/transactionFlow/findUserOTP.txt";

	static FindUserOTP findUserOTP;
	static FOCPlatform focPlatform;
	static OauthToken oAuthToken;
	static FetchOTP fetchOTP;

	static String otp;
	static ResultSet rsm;
	static ResultSet rsmoc;

	static String merchantId;
	static String clientID;
	static String otpFormat = "NUMERIC";
	static int policyId = 3;

	static FetchOTPPojo fetchOTPPojo;



	static String SELECT_MERCHANT = "select * from lazypay.merchants where accessKey = '$';";
	static String SELECT_MERCHANT_OTP_CONFIG = "select * from lazypay.merchant_otp_configuration where merchantId = '$';";

	public FindOrCreateUserOTP() {
	}

	static {
		try {
			findUserOTP = new FindUserOTP();
			focPlatform = new FOCPlatform();
			oAuthToken = new OauthToken();
			fetchOTP = new FetchOTP();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SneakyThrows
	public static ResultSet getMerchantDetails(String accessKey)
	{
		return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_MERCHANT.replace("$", accessKey));
	}

	@SneakyThrows
	public static ResultSet getMerchantOTPConfig(String mid)
	{
		return Lazypay_MySQL_DBAccessObject.selectFromMySqlDb(SELECT_MERCHANT_OTP_CONFIG.replace("$", mid));
	}

	public static String getLatestOTP(String userMobile) throws Exception {

		Map<String, Object> queryParamDetails = new HashMap<>();
		HashMap<String, String> headerDetails = new HashMap<>();

		queryParamDetails.put("mobile", userMobile);
		queryParamDetails.put("tenantId", ReadProperties.testConfigMap.get(UtilConstants.TENANT_ID).toString());
		Thread.sleep(2000);
		fetchOTPPojo = fetchOTP.get(queryParamDetails, headerDetails);

		Assert.assertNotNull(fetchOTPPojo.otpId, "OTP ID returned is null!");
		Assert.assertNotNull(fetchOTPPojo.otpValue, "OTP value returned is null!");

		return fetchOTPPojo.otpValue;
	}


	public static String getTxnOTPV1(String userMobile, String accessKey, String txnId) throws Exception {

		Map<String, Object> queryParamDetails = new HashMap<>();
		HashMap<String, String> headerDetails = new HashMap<>();

		rsm = getMerchantDetails(accessKey);
		while (rsm.next()) {
			merchantId = rsm.getString("merchantId");
			clientID = rsm.getString("clientId");
		}

		rsmoc = getMerchantOTPConfig(merchantId);
		while (rsmoc.next()) {
			if(rsmoc.getString("alphanumeric").equals("1")) {
				otpFormat = "ALPHA_NUMERIC";
				policyId=4;
			}
		}

		queryParamDetails.put("mobile", userMobile);
		queryParamDetails.put("lazypayLogin", false);
		queryParamDetails.put("source", "fetch_otp");
		queryParamDetails.put("clientId", clientID);
		if(txnId!=null){
			queryParamDetails.put("clientRefId", txnId);
		}
		queryParamDetails.put("otpFormat", otpFormat);
		queryParamDetails.put("policyId", policyId);

		FOCPlatformPojo focPlatformPojo = focPlatform.post(queryParamDetails, headerDetails, "");

		Assert.assertNotNull(focPlatformPojo.responseData);

		return focPlatformPojo.responseData.otpValue;

	}


	public static String findOrCreateOTP(String userMobile) {
		Map<String, Object> queryParamDetails = new HashMap<>();
		HashMap<String, String> headerDetails = new HashMap<>();


		if(System.getProperty("env").equals("sbox")||System.getProperty("env").equals("QA")){
			queryParamDetails.put("mobile", userMobile);
			queryParamDetails.put("lazypayLogin", false);
			queryParamDetails.put("source", "fetch_otp");
			queryParamDetails.put("clientId", ReadProperties.testConfigMap.get(UtilConstants.CLIENT_ID).toString());
			queryParamDetails.put("clientSecret", ReadProperties.testConfigMap.get(UtilConstants.CLIENT_SECRET).toString());

			FOCPlatformPojo focPlatformPojo = focPlatform.post(queryParamDetails, headerDetails, "");

			Assert.assertNotNull(focPlatformPojo.responseData);
			otp = focPlatformPojo.responseData.otpValue;

		}else{
			String authToken;
			authToken = generateOAuthToken();
			headerDetails.put("Authorization", "Bearer " +authToken);

			String OTPRequest= new StringTemplate(OTP_REQUEST)
					.replace("mobile", userMobile)
					.toString();

			FindUserOTPPojo findUserOTPPojo = findUserOTP.post(queryParamDetails, headerDetails, OTPRequest);

			Assert.assertNotNull(findUserOTPPojo.responseData);
			otp = findUserOTPPojo.responseData.otpValue;
		}

		return otp;
	}


	public static String generateOAuthToken() {
		Map<String, Object> queryParamDetails = new HashMap<>();
		HashMap<String, String> headerDetails = new HashMap<>();


		queryParamDetails.put("grant_type", "implicit");
		queryParamDetails.put("client_id", ReadProperties.testConfigMap.get(UtilConstants.CLIENT_ID).toString()
		);
		queryParamDetails.put("client_secret",ReadProperties.testConfigMap.get(UtilConstants.CLIENT_SECRET).toString()
		);
		headerDetails.put("Content-Type", "application/x-www-form-urlencoded");

		String oAuthRequest= "";

		OAuthTokenPojo oAuthTokenPojo = oAuthToken.post(queryParamDetails, headerDetails, oAuthRequest);
		log.info("access token: ", oAuthTokenPojo.access_token);
		System.out.println(oAuthTokenPojo.access_token);
		Assert.assertNotNull(oAuthTokenPojo.access_token);
		return oAuthTokenPojo.access_token;
	}

}