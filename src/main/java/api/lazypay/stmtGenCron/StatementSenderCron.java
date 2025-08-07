package api.lazypay.stmtGenCron;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.stmtGenCronFlow.StmtGenCronPojo;

import java.util.HashMap;
import java.util.Map;

public class StatementSenderCron extends BaseAPI<StmtGenCronPojo> {
    public StatementSenderCron() throws Exception {
            super(Uri.STATEMENT_SENDER_CRON, StmtGenCronPojo.class);
    }


    /*@Step
    public Response getWithPathPrams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String param1, String param2) {
        return super.getWithPathPrams(queryParamDetails, headerDetails, param1, param2);
    }*/


    @Step
    public Response getWithPathPram(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathPrams) {
        return super.getWithPathPram(queryParamDetails, headerDetails, pathPrams);
    }
}
