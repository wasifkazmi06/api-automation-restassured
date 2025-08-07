package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.PortfolioScrubMockResponsePojo;

import java.util.HashMap;

public class CibilPortfolioMocking extends BaseAPI<PortfolioScrubMockResponsePojo> {

    public CibilPortfolioMocking()throws Exception {
        super(Uri.BUREAU_PORTFOLIO_SCRUB_MOCK, PortfolioScrubMockResponsePojo.class);
    }

    @Step
    public PortfolioScrubMockResponsePojo postWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}
