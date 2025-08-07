package snailMailChanges;

import io.qameta.allure.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;

public class snailmailTestData {

    String requestId = "12323";
    String source1="LP";
    String source2 = "PS";
    String source3 = "SMB";
    String source4 = "API_LENDING";
    String lp_auth_key = "c129393471de3bd1";
    String ps_auth_key = "3a5cb67f70e9fab1";
    String lpuuid_WithoutApvDoc = "8276618040365738668";
    String lpuuid = "8050122515879853438";
    String xpressuuid="4983900123129576841";
    String tesMobile1 = "9986611041";
    String testMobile2 = "7760703178";
    String testAddress = "1997, South extension, Delhi";
    String testUserName = "Test1";
    LocalDate currentDate = LocalDate.now();


    @Step
    public JSONArray userDataInRequestPayload(String product){
        JSONArray userData = new JSONArray();
        userData.put(new JSONObject().put("userName", testUserName).put("userAddress", testAddress).put("agreementSignDate", currentDate.minusDays(1)).
                put("product", product).put("mobile",tesMobile1));
        userData.put(new JSONObject().put("userName", testUserName+"2").put("userAddress", testAddress).put("agreementSignDate", currentDate.minusDays(1)).
                put("product", product).put("mobile",testMobile2));
        return userData;
    }
    @DataProvider(name = "pullStatusTestData")
    public Object[][] pullStatusTestData() {
        return new Object[][]{
                {"ValidRequestIdANDSource", requestId, source1, lp_auth_key},
                {"Valid_source_LP", requestId , source1, lp_auth_key},
                {"Valid_source_PS", requestId, source2, ps_auth_key},
                {"Valid_source_SMB", requestId, source3, ps_auth_key},
                {"Valid_source_APILENDING", requestId, source4, ps_auth_key},
                {"Valid_AuthKey_LP", requestId, source1, lp_auth_key},
                {"Valid_AuthKey_PS", requestId, source2, ps_auth_key},
                {"InvalidRequestId", "23e", source1, lp_auth_key},
                {"InvalidSource", requestId, "BNPL", lp_auth_key},
                {"WithEmptyRequestId", "", source1, lp_auth_key},
                {"WithNullRequestId", null, source1, lp_auth_key},
                {"WithEmptySource", requestId, "", lp_auth_key},
                {"WithNullSource", requestId, null, lp_auth_key},
                {"WithDateFilterParam",requestId , source1, lp_auth_key}
        };
    }
    @DataProvider(name = "pushUserDataToRemote")
    public Object[][] pushUserDataToRemote(){
        return new Object[][]{
                {"ValidRequestInput", requestId, source1, lp_auth_key},
                {"Valid_source_LP", requestId , source1, lp_auth_key},
                {"Valid_source_PS", requestId, source2, ps_auth_key},
                {"Valid_source_SMB", requestId , source3, lp_auth_key},
                {"Valid_source_APILENDING", requestId , source4, lp_auth_key},
                {"Valid_AuthKey_LP", requestId, source1, lp_auth_key},
                {"Valid_AuthKey_PS", requestId, source2, ps_auth_key},
                {"InvalidSource", requestId, "BNPL", lp_auth_key},
                {"WithEmptyRequestId", "", source1, lp_auth_key},
                {"WithNullRequestId", null, source1, lp_auth_key},
                {"WithEmptySource", requestId, "", lp_auth_key},
                {"WithNullSource", requestId, null, lp_auth_key},

        };
    }
    @DataProvider(name = "pushRequestParamValidations")
    public Object[][] pushRequestParamValidations(){
        return new Object[][]{
                {"WithEmptyUserName", "", requestId, testAddress,currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithoutUserName", null, requestId, testAddress,currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithAPVId", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithoutAPVId", testUserName,null, testAddress,currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithEmptyUserAddress", testUserName, requestId, "",currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithoutUserAddress", testUserName, requestId, null,currentDate.minusDays(1).toString(),source1,testMobile2},
                {"WithoutAgreementSignedDate", testUserName, requestId, testAddress,null,source1,testMobile2},
                {"WithInvalidProduct", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),"BNPL",testMobile2},
                {"WithEmptyProduct", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),"",testMobile2},
                {"WithoutProductParam", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),null,testMobile2},
                {"WithInvalidMobile", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),source1,testMobile2+"0"},
                {"WithEmptyMobile", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),source1,""},
                {"WithoutMobileParam", testUserName, requestId, testAddress,currentDate.minusDays(1).toString(),source1,null},

        };
    }
    @DataProvider(name = "getAPVStatusTestdata")
    public Object[][] getAPVStatusTestdata(){
        return new Object[][]{
                {"Valid_UUID&SOURCE", lpuuid, "BNPL"},
                {"Valid_SOURCE_BNPL", lpuuid , "BNPL"},
                {"Valid_SOURCE_XPRESS", xpressuuid, "XPRESS"},
                {"Invalid_UUID", requestId, "BNPL"},
                {"WithEmpty/Null_UUID","" ,"BNPL"},
                {"Invalid_SOURCE", lpuuid, source1},
                {"WithEmpty/Null_SOURCE", lpuuid, ""},
                {"WithUserHavingNoAPVDOC",lpuuid_WithoutApvDoc,"BNPL"}
        };
    }
}
