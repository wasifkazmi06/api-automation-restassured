package neo.supportapisTest;

import api.neobank.supportapis.cardActions;
import api.neobank.supportapis.cardReplacement;
import api.neobank.supportapis.cardReplacementFailed;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.support.CardControlPojo;
import pojos.neobank.support.cardControlFailedPojo;
import pojos.neobank.support.preference.lazyCardActions;
import util.StringTemplate;


import java.util.HashMap;
import java.util.Map;

public class cardReplacementTest extends SupportApiData {


    public cardReplacementTest() throws Exception {
        super();
    }
    cardReplacement cardreplacement=new cardReplacement();
    cardReplacementFailed cardreplacementfailed=new cardReplacementFailed();
    cardActions card_actions = new cardActions();


    @Description("If a cardReplacement api is hit with card is not freezed then the api will throw an error saying Block old card first")
    @Feature("CardReplacement_SupportApi")
    @Test(priority = 1,groups = { "Support","cardReplacement"})
    public void cardReplacementWithCardFreezeStatusAsUnBlock(){

        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",NEO_CARD_REPLACEMENT_UUID);
        cardControlFailedPojo cardControlfailedPojo = cardreplacementfailed.post(pathparam,headerDetails);
        Assert.assertTrue(cardControlfailedPojo.message.equalsIgnoreCase("Block old card"));

    }

    @Description("If a cardReplacement api is hit with card freezed then the api will place order for cardReplacement")
    @Feature("CardReplacement_SupportApi")
    @Test(priority = 2,groups = { "Support","cardReplacement"})
    public void cardReplacementWithCardFreezeStatusAsBlock() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String request= new StringTemplate(NeoConstants.CARD_ACTIONS_REQUEST).replace("uuid",NEO_CARD_REPLACEMENT_UUID).replace("flag",BLOCK_CARD_FLAG).toString();
        lazyCardActions cardAction = card_actions.post(queryParamDetails, headerDetails, request);
        HashMap<String, String> pathparam = new HashMap<>();
        pathparam.put("uuid",SupportApiData.NEO_CARD_REPLACEMENT_UUID);
        CardControlPojo cardControlPojo = cardreplacement.post(pathparam,headerDetails);
        Assert.assertTrue(cardControlPojo.result.equalsIgnoreCase("success"));

    }

}
