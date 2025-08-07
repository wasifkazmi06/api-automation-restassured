package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.CreateOrderPojo;


import java.util.HashMap;

public class CreateOrder extends BaseAPI<CreateOrderPojo> {


        public CreateOrder() throws Exception {
            super(Uri.CREATEORDER_JP, CreateOrderPojo.class);
        }
        @Step
        public CreateOrderPojo post( HashMap<String, String> headerDetails, String jsonRequestBody)
        {
            return super.post(headerDetails, jsonRequestBody);
        }
    }

