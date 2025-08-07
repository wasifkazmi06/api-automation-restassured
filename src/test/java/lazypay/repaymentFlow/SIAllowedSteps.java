package lazypay.repaymentFlow;

import api.lazypay.repayment.IsSIAllowed;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.IsSIAllowedPojo;
import java.util.HashMap;
import java.util.Map;
import io.restassured.response.Response;


public class SIAllowedSteps {

    IsSIAllowedPojo isSIAllowedPojo = new IsSIAllowedPojo();
    IsSIAllowed isSIAllowed = new IsSIAllowed();
    SavedOptionsSteps savedOptionsTests = new SavedOptionsSteps();
    PreferredMethodSteps preferredMethodTests = new PreferredMethodSteps();

    public SIAllowedSteps() throws Exception {
    }

    @Step
    public void verifyIfSIIsAllowedCreditCard() {

        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new  HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("userIdentifier", preferredMethodTests.preferredMethodPojo.userIdentifier);
        pathParams.put("bin", savedOptionsTests.savedOptionPojo.cards.get(0).cardNo.substring(0,6));

        String result = isSIAllowed.getWithPathPrams(queryParamDetails, headerDetails, preferredMethodTests.preferredMethodPojo.userIdentifier,
                savedOptionsTests.savedOptionPojo.cards.get(0).cardNo.substring(0,6)).getBody().asString();

        //isSIAllowedPojo = isSIAllowed.get(queryParamDetails, headerDetails, pathParams);

        Assert.assertEquals(result,"false","verify if the CC user is allowed to setup SI failed as Result was not false");

    }

    @Step
    public void verifyIfSIIsAllowedDebitCard() {

        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new  HashMap<>();

        String result = isSIAllowed.getWithPathPrams(queryParamDetails, headerDetails, preferredMethodTests.preferredMethodPojo.userIdentifier,
                savedOptionsTests.savedOptionPojo.cards.get(0).cardNo.substring(0,6)).getBody().asString();
        Assert.assertEquals(result,"false","verify if the DC user is allowed to setup SI failed as Result was not false");

    }

    @Step
    public void verifyIfSIIsAllowedInvalidUser() {

        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new  HashMap<>();
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("userIdentifier", "12ads3ds");
        pathParams.put("bin", "401704");

        //isSIAllowedPojo = isSIAllowed.get(queryParamDetails, headerDetails, pathParams);

        Response result  = isSIAllowed.getWithPathPrams(queryParamDetails, headerDetails, "12ads3ds",
                "401704");
        String errorCode = result.jsonPath().getString("errorCode");
        String message = result.jsonPath().getString("message");

        /*Assert.assertEquals(isSIAllowedPojo.errorCode , "LP_INVALID_USER_DETAIL","verify If SI Is Allowed Invalid User as error code did not match");
        Assert.assertEquals(isSIAllowedPojo.message, "Oops!! User associated with corresponding transaction does not seem to exist in LazyPay","verify If SI Is Allowed Invalid User as error message did not match");
*/
        Assert.assertEquals(errorCode , "LP_INVALID_USER_DETAIL","verify If SI Is Allowed Invalid User as error code did not match");
        Assert.assertEquals(message, "Oops!! User associated with corresponding transaction does not seem to exist in LazyPay","verify If SI Is Allowed Invalid User as error message did not match");

    }

}
