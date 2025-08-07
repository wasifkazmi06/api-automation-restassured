package api.lazypay.stmtGenCron;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.stmtGenCronFlow.StmtGenCronPojo;
import java.util.HashMap;
import java.util.Map;

public class StatementGenCron extends BaseAPI<StmtGenCronPojo> {
    public StatementGenCron() throws Exception {
            super(Uri.STATEMENT_GEN_CRON, StmtGenCronPojo.class);
    }

    @Step
    public Response getWithPathPram(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathPrams) {
        return super.getWithPathPram(queryParamDetails, headerDetails, pathPrams);
    }
}
