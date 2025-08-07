package lazypay.transactionFlow;

import api.lazypay.transaction.LazyCashWithSource;
import api.platform.GetUserData;
import io.restassured.path.json.JsonPath;
import lazypay.LazypayConstants;
import io.restassured.response.Response;
import lazypay.MakeTransaction;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.LazyCashWithSourcePojo;
import pojos.platform.getUserData.UserData;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LazycashTransaction {

    static LazyCashWithSourcePojo lazyCashWithSourcePojo = new LazyCashWithSourcePojo();
    static LazyCashWithSource lazyCashWithSource;
    static public GetUserData getUserData;
    static public UserData userData;
    static String lazycashRequest=null;
    static String source = "REWARD_SYSTEM";

    public static ResultSet rs;

//    public static String lazyCashAmount = "25";
//    public static String lpUserMobile = "9970798203";
//    public static String isSourceRewardSystem = "Yes";

    public static String lazyCashAmount = System.getProperty("amount");
    public static String lpUserMobile = System.getProperty("mobile");
    public static String isSourceRewardSystem = System.getProperty("isSourceRewardSystem");

    static {
        try {
            lazyCashWithSource = new LazyCashWithSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public LazycashTransaction()  {
    }

    @BeforeSuite(alwaysRun = true)
    public void setRewardSource(){

        if(isSourceRewardSystem.equalsIgnoreCase("No")) {
            source= "other-reward";
        }

    }

    @Test
    public static void lazyCashWithSourceTransaction() throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("source", source);

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile", MakeTransaction.mobile);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);
        Assert.assertEquals(userData.getPrimaryMobile().getValue(), MakeTransaction.mobile, "User mobile not matching in platform get user response!");
        String lpUserId = userData.getId();


        if(lpUserId == null || lpUserId.isEmpty()){

            rs = TransactionData.getUserDetail(lpUserMobile);
            while (rs.next()) {
                lpUserId = rs.getString("uuid");
            }
        }


        if(lpUserId == null){
            Assert.assertNotNull(lpUserId, "User doesn't have account in Lazypay");
        }else{
            lazycashRequest= new StringTemplate(LazypayConstants.LAZYCASH_SOURCE_REQUEST)
                    .replace("amount", lazyCashAmount)
                    .replace("lpUserId",lpUserId)
                    .replace("rewardId", ReturnRandomTxnId.returnTxnIDMethod("REWARDID"))
                    .toString();


            Response lazycashResponse = lazyCashWithSource.postReturnResponse(queryParamDetails, headerDetails, lazycashRequest);

            String response = lazycashResponse.getBody().asString();
            if(response.equals("SUCCESS")){
                Assert.assertEquals(response, "SUCCESS", "Lazycash Transaction Failed");
            }else{
                JsonPath jsonPathEvaluator = lazycashResponse.jsonPath();
                Integer status = jsonPathEvaluator.get("status");
                String message = jsonPathEvaluator.get("message");
                log.info("response status :"+ status);
                log.info("response message :"+ message);

                Assert.assertNull(status, message);
            }
        }



    }


    @AfterClass
    public void finishUp() throws SQLException {
        if(rs != null) {
            rs.close();
        }
    }



}
