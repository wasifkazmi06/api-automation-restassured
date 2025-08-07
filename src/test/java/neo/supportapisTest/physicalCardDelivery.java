package neo.supportapisTest;

import api.neobank.supportapis.cardReorder;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.support.CardControlPojo;
import util.StringTemplate;

import java.util.HashMap;

@Slf4j
public class physicalCardDelivery extends SupportApiData {
    public physicalCardDelivery() throws Exception {
        super();
    }

    cardReorder cardreorder = new cardReorder();

    @Description("If a user order a physical card to his/her address then the status in db should be changed to Requested")
    @Feature("PhysicalCardRequest_SupportApi")
    @Test(priority = 1, groups = {"Support", "PhysicalCardReq"})
    public void physilCardDeliveryWithKYC() {
        log.info("Updating physical card request to NOTRequested");
        Assert.assertTrue(issueNewCard().equalsIgnoreCase("success"), "Failed to issue new card");
        updateStatus();
        deletePhysicalCardOrder();
        log.info("physical card order data is deleted");
        HashMap<String, String> headerDetails = new HashMap<>();
        String request = new StringTemplate(NeoConstants.PHYSICAL_CARD_REQUEST_KYC).toString();
        CardControlPojo cardControlPojo = cardreorder.post(headerDetails, request);
        Assert.assertEquals(cardControlPojo.result, "true", String.format("Failed to order Physical Card;  Status code: %s; Error Message is %s;", cardControlPojo.status, cardControlPojo.error));
        String getPhysicalCardOrderStatus = getPhysicalCardStatus("physicalCardRequested");
        Assert.assertEquals(getPhysicalCardOrderStatus, "REQUESTED", "Failed to update physicalCardRequested in lazycards table");
    }

    @Description("If a user order a physical card to his/her Nonkyc address then the status in db should be changed to Requested and kycstatus should be 0")
    @Feature("PhysicalCardRequest_SupportApi")
   // @Test(priority = 2,groups = { "Support","PhysicalCardReq"})
    public void physilCardDeliveryWithNonKYC(){
        log.info("Updating physical card request to NOTRequested");
        updateStatus();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("encryptResponse","false");
        String request= new StringTemplate(NeoConstants.PHYSICAl_CARD_REQUEST_NONKYC).toString();
        CardControlPojo cardControlPojo = cardreorder.post(headerDetails, request);
        Assert.assertTrue(cardControlPojo.result.equalsIgnoreCase("true"));
        int getKycStatus= verifyIsKycAddress("isKycAddress");
        Assert.assertEquals(getKycStatus,1);
        String getPhysicalCardOrderStatus=getPhysicalCardStatus("physicalCardRequested");
        Assert.assertEquals(getPhysicalCardOrderStatus,"REQUESTED");
        deletePhysicalCardOrder();
        log.info("Updating physical card request to NOTRequested");
        deleteAddressMapping();
        log.info("Deleting address from address table");
        deleteAddress();
    }

}
