package api.neobank.cardControl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.cardControl.FetchShipmentUpdatesPojo;
import pojos.neobank.cardControl.GetUserTransactionLimitPojo;

import java.util.HashMap;
import java.util.Map;

public class FetchShipmentUpdates extends BaseAPI<FetchShipmentUpdatesPojo> {

    public FetchShipmentUpdates() throws Exception {
        super(Uri.NEO_CARD_FETCH_SHIPMENT_UPDATES, FetchShipmentUpdatesPojo.class);
    }
    @Step
    public FetchShipmentUpdatesPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
