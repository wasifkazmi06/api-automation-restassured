package chatbot.needhelp;


import api.chatbot.needhelp.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import pojos.chatbot.needhelp.*;
import java.util.HashMap;
import java.util.Map;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class NeedHelpTest {

    static GetCategory getCategory;
    static GetCategoryPojo getCategoryPojo;
    static Response responseNeedHelp;

    static GetFaqsByNextLevel getFaqsByNextLevel;
    static GetFaqsByNextLevelPojo getFaqsByNextLevelPojo;
    static GetNodeMapping getNodeMapping;
    static NeedHelpNodeMapping[] needHelpNodeMapping;
    static GetTreeByLevel getTreeByLevel;
    static GetTree1 getTree1;
    static GetTreeByLevelPojo[] getTreeByLevelPojo;
    static GetTreeByNodeId getTreeByNodeId;
    static GetTreeByNodeIdPojo getTreeByNodeIdPojo;

    static {
        try {
            getCategory = new GetCategory();
            getFaqsByNextLevel = new GetFaqsByNextLevel();
            getNodeMapping = new GetNodeMapping();
            getTreeByLevel = new GetTreeByLevel();
            getTreeByNodeId = new GetTreeByNodeId();
            getTree1 = new GetTree1();
           } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("NeedHelp: To validate the API Status code of NeedHelp Get NodeMapping")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetNodeMappingAPIStatusCode() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        responseNeedHelp = getNodeMapping.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Response StatusCode for NodeMapping");

    }

    @Description("NeedHelp: To validate the API Response for NeedHelp Get NodeMapping")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetNodeMappingAPIResponse() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        needHelpNodeMapping = getNodeMapping.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(needHelpNodeMapping[0].getTag());
        Assert.assertNotNull(needHelpNodeMapping[0].getNodeId());
        Assert.assertNotNull(needHelpNodeMapping[0].getId());

    }

    @Description("NeedHelp: To validate the API Status code for a valid Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIStatusCodeWithValidCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.ValidCategory);

        responseNeedHelp = getCategory.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Category");

    }

    @Description("NeedHelp: To validate the API Status code for a Invalid Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIStatusCodeWithInValidCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.InValidCategory);

        responseNeedHelp = getCategory.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 400);

    }

    @Description("NeedHelp: To validate the API Response for a valid Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithValidCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.ValidCategory);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertNotNull(getCategoryPojo.getLevel());
        Assert.assertNotNull(getCategoryPojo.getNeedHelpChildCategoryList());
        Assert.assertTrue(getCategoryPojo.root);
        Assert.assertFalse(getCategoryPojo.leaf);
        Assert.assertNull(getCategoryPojo.getParent());



    }

    @Description("NeedHelp: To validate the API Response for a Nach_Bounce_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithNach_Bounce_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Nach_Bounce_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Nach_Bounce_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Repay_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithRepay_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Repay_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Repay_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Repay_Fail Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithRepay_FailCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Repay_Fail);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Repay_Fail, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Wallet_Withdraw_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithWallet_Withdraw_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Wallet_Withdraw_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Wallet_Withdraw_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Wallet_Pay_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithWallet_Pay_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Wallet_Pay_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Wallet_Pay_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Wallet_Cashback_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithWallet_Cashback_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Wallet_Cashback_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Wallet_Cashback_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Wallet_Merchant_Pay_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithWallet_Merchant_Pay_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Merchant_Pay_Wallet_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_Merchant_Pay_Wallet_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Lazy_pay_Cashback_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithLazy_pay_Cashback_SuccessCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Lazy_pay_Cashback_Success);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expeceted_Lazy_pay_Cashback_Success, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a Lazy_pay_Cashback_Success Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithPaylater_Autopay_Faq_ItemCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.Paylater_Autopay_Faq_Item);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Paylater_Autopay_Faq_Item, "Category not equal!");

    }

    @Description("NeedHelp: To validate the API Response for a LazyPay_Processing_Fee Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithLazyPay_Processing_FeeCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.LazyPay_Processing_Fee);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getCategoryPojo.getCategoryName());
        Assert.assertNotNull(getCategoryPojo.getNodeId());
        Assert.assertEquals(getCategoryPojo.getCategoryName(), NeedHelpData.Expected_LazyPay_Processing_Fee, "Category not equal!");

    }



    @Description("NeedHelp: To validate the API Response for a Invalid Category of NeedHelp Get Category")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetCategoryAPIResponseWithInValidCategory() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("searchTag", NeedHelpData.InValidCategory);

        getCategoryPojo = getCategory.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getCategoryPojo.getStatus(), 400, "Status not 400, check logs!");
        Assert.assertEquals(getCategoryPojo.getMessage(), NeedHelpData.NoCategoryMappingMessage, "Status not 400, check logs!");
        Assert.assertEquals(getCategoryPojo.getErrorCode(), NeedHelpData.NoCategoryMappingErrorCode, "Status not 400, check logs!");


    }

    @Description("NeedHelp: To validate the API Status code for a valid nextNLevel of NeedHelp Get FaqsByNextLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetFaqsByNextLevelAPIStatusCodeWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.ValidLevel);

        responseNeedHelp = getFaqsByNextLevel.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Faqs");

    }

    @Description("NeedHelp: To validate the API Status code for a Invalid nextNLevel of NeedHelp Get FaqsByNextLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetFaqsByNextLevelAPIStatusCodeWithInValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.InValidLevel);

        responseNeedHelp = getFaqsByNextLevel.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 500, "valid Level");

    }

    @Description("NeedHelp: To validate the API Response for a valid nextNLevel of NeedHelp Get FaqsByNextLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetFaqsByNextLevelAPIResponseWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.ValidLevel);

        getFaqsByNextLevelPojo = getFaqsByNextLevel.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getFaqsByNextLevelPojo.getCategoryName(), NeedHelpData.ResponseCategoryNameForFaq);
        Assert.assertNotNull(getFaqsByNextLevelPojo.getNodeId());
        Assert.assertNotNull(getFaqsByNextLevelPojo.getLevel());
        Assert.assertNotNull(getFaqsByNextLevelPojo.getNeedHelpChildCategoryList());
        Assert.assertTrue(getFaqsByNextLevelPojo.root);
        Assert.assertFalse(getFaqsByNextLevelPojo.leaf);
        Assert.assertNull(getFaqsByNextLevelPojo.getParent());
    }

    @Description("NeedHelp: To validate the API Response for a Invalid nextNLevel of NeedHelp Get FaqsByNextLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetFaqsByNextLevelAPIResponseWithInValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.InValidLevel);

        getFaqsByNextLevelPojo = getFaqsByNextLevel.get(queryParamDetails, headerDetails);
        Assert.assertNotNull(getFaqsByNextLevelPojo.getMessage(), "Status not 500, check logs!");
        Assert.assertNotNull(getFaqsByNextLevelPojo.getStatusCode(), "Status not 500, check logs!");

    }

    @Description("NeedHelp: To validate the API Status code for a valid Level of NeedHelp Get Tree1")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTree1APIStatusCodeWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        responseNeedHelp = getTreeByLevel.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Tree");

    }


    @Description("NeedHelp: To validate the API Response for a valid Level of NeedHelp Get Tree1")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTree1APIResponseWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        getTreeByLevelPojo = getTreeByLevel.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getTreeByLevelPojo[0].getLevel(), Integer.parseInt(NeedHelpData.ValidLevel), "Invalid Tree");
        Assert.assertNotNull(getTreeByLevelPojo[0].getNodeId());
        Assert.assertNotNull(getTreeByLevelPojo[0].getLevel());
        Assert.assertNotNull(getTreeByLevelPojo[0].getNeedHelpChildCategoryList());
        Assert.assertNotNull(getTreeByLevelPojo[0].root);
        Assert.assertNotNull(getTreeByLevelPojo[0].leaf);

    }

    @Description("NeedHelp: To validate the API Response for a valid Level of NeedHelp Get TreeByLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByLevelAPIResponseWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("level", NeedHelpData.ValidLevel);

        getTreeByLevelPojo = getTreeByLevel.get(queryParamDetails, headerDetails);
        Assert.assertEquals(getTreeByLevelPojo[0].getLevel(), Integer.parseInt(NeedHelpData.ValidLevel), "Invalid Tree");
        Assert.assertNotNull(getTreeByLevelPojo[0].getNodeId());
        Assert.assertNotNull(getTreeByLevelPojo[0].getLevel());
        Assert.assertNotNull(getTreeByLevelPojo[0].getNeedHelpChildCategoryList());
        Assert.assertNotNull(getTreeByLevelPojo[0].root);
        Assert.assertNotNull(getTreeByLevelPojo[0].leaf);

    }

    @Description("NeedHelp: To validate the API Status code for a valid Level of NeedHelp Get TreeByLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByLevelAPIStatusCodeWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("level", NeedHelpData.ValidLevel);

        responseNeedHelp = getTreeByLevel.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Tree");

    }

    @Description("NeedHelp: To validate the API Status code for a Invalid Level of NeedHelp Get TreeByLevel")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByLevelAPIStatusCodeWithInvalidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("level", NeedHelpData.InValidLevel);

        responseNeedHelp = getTreeByLevel.getWithResponse(queryParamDetails, headerDetails);
        Assert.assertEquals(responseNeedHelp.statusCode(), 500, "Invalid Tree");

    }

    @Description("NeedHelp: To validate the API Status code for a valid NodeId of NeedHelp Get TreeByNodeId")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByNodeIdAPIStatusCodeWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("level", NeedHelpData.ValidLevel);

        responseNeedHelp = getTreeByNodeId.getWithPathPram(queryParamDetails, headerDetails, NeedHelpData.ValidNodeId);
        Assert.assertEquals(responseNeedHelp.statusCode(), 200, "Invalid Tree for provided NodeId");

    }

    @Description("NeedHelp: To validate the API Status code for a Invalid NodeId of NeedHelp Get TreeByNodeId")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByNodeIdAPIStatusCodeWithInValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.InValidLevel);

        responseNeedHelp = getTreeByNodeId.getWithPathPram(queryParamDetails, headerDetails, NeedHelpData.ValidNodeId);
        Assert.assertEquals(responseNeedHelp.statusCode(), 500, "valid Level");


    }

    @Description("NeedHelp: To validate the API Response for a valid NodeId of NeedHelp Get TreeByNodeId")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByNodeIdAPIResponseWithValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("level", NeedHelpData.ValidLevel);

        getTreeByNodeIdPojo = getTreeByNodeId.get(queryParamDetails, headerDetails, NeedHelpData.ValidNodeId);
        Assert.assertEquals(getTreeByNodeIdPojo.getLevel(), Integer.parseInt(NeedHelpData.ValidLevel), "Invalid Tree for provided NodeId");
        Assert.assertEquals(getTreeByNodeIdPojo.getNodeId(), Integer.parseInt(NeedHelpData.ValidNodeId), "Wrong NodeId in response");

        Assert.assertNotNull(getTreeByNodeIdPojo.getNodeId());
        Assert.assertNotNull(getTreeByNodeIdPojo.getLevel());
        Assert.assertNotNull(getTreeByNodeIdPojo.getNeedHelpChildCategoryList());

    }

    @Description("NeedHelp: To validate the API Response for a Invalid NodeId of NeedHelp Get TreeByNodeId")
    @Feature("NeedHelpAPI")
    @Test(priority =1, groups = {"needhelp", "sanity"})
    public static void validateNeedHelpGetTreeByNodeIdAPIResponseWithInValidLevel() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("nextNLevel", NeedHelpData.InValidLevel);

        getTreeByNodeIdPojo = getTreeByNodeId.get(queryParamDetails, headerDetails, NeedHelpData.ValidNodeId);
        Assert.assertNotNull(getTreeByNodeIdPojo.getMessage(), "Status not 500, check logs!");
        Assert.assertNotNull(getTreeByNodeIdPojo.getStatusCode(), "Status not 500, check logs!");

    }
}
