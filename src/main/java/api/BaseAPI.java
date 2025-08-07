package api;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import lombok.extern.slf4j.Slf4j;
import io.qameta.allure.Allure;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import util.ReadProperties;
import util.RestAssuredAPIClientSetup;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Getter
@Setter
public abstract class BaseAPI<T> {
    protected String beUrl;
    protected String uri;
    private Class<T> responseClass;
    protected String apiUrl;
    protected Map<String, Object> pathParam;
    protected String component;
    RestAssuredConfig config;
    private Response response;

    protected BaseAPI(String apiUri, Class<T> responseClass) throws Exception {

        this.beUrl = ReadProperties.extractBeUrl(apiUri);
        //this.component = ReadProperties.selectComponent(apiUri);
        this.uri = ReadProperties.extractUri(apiUri);
        //this.pathParam = pathParam;
        this.responseClass = responseClass;
        this.apiUrl = beUrl + uri;
        this.config = CurlLoggingRestAssuredConfigFactory.createConfig().httpClient(HttpClientConfig.httpClientConfig().dontReuseHttpClientInstance());
    }

    /**
     * Get API
     *
     * @param queryParamDetails
     * @param headerDetails
     * @return
     */
    protected T get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        return response.as(responseClass);


    }

    /**
     * GET API WITH Dynamic Path Variable
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param pathParam
     * @return
     */
    protected T get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        log.info("response is " + response.body().asString());
        return response.as(responseClass);
    }

    /**
     * Get API with 2 path params
     *
     * @param headerDetails
     * @return
     */
    public Response getWithPathPrams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam1, String pathParam2) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam1).replace("#", pathParam2), headerDetails, component)
                .queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        return response;

        //.pathParam(pathParams[0], pathParamValues[0])
        //                .pathParams(pathParams[1], pathParamValues[1])
    }

    /**
     * Get API with 1 path param
     *
     * @param headerDetails
     * @return
     */
    public Response getWithPathPram(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component)
                .queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        return response;
    }

    protected Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(queryParamDetails).config(config).get();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }


    /**
     * Post
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).post();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    /**
     * Post
     *
     * @param headerDetails
     * @return
     */
    protected T post(HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).config(config).post();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }


    /**
     * @param pathParam
     * @param headerDetails
     * @param file
     * @return
     */
    protected T post(Map<String, Object> pathParam, HashMap<String, String> headerDetails, File file) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(pathParam).config(config).multiPart("file", file).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    /**
     * @param pathParam
     * @param headerDetails
     * @return
     */
    protected T post(Map<String, Object> pathParam, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(pathParam).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    protected String postWithNoResponseBody(Map<String, Object> pathParam, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(pathParam).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        log.info("response is" + response);
        return String.valueOf(response.statusCode());
    }

    /*  protected String getWithNoResponseBody(Map<String, Object> pathParam, HashMap<String, String> headerDetails) {
          response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(pathParam).config(config).post();
          Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
          log.info("response is"+response);
          return String.valueOf(response.statusCode());
      }*/
    protected String getWithNoResponseBody(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return String.valueOf(response.statusCode());
    }

    /**
     * Post
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).post();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    /**
     * Post
     *
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T post(HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    /**
     * Post with Path Params
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T postWithPathParams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    protected Response postWithPathParamsWithReponse(HashMap<String, String> headerDetails, String jsonRequestBody, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).body(jsonRequestBody).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    /**
     * Post without requestBody
     *
     * @param queryParamDetails
     * @param headerDetails
     * @return
     */
    protected Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(request).queryParams(queryParamDetails).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    /**
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T patch(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).patch();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    /**
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T put(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    /**
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @param path
     * @return
     */
    protected T put(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody, String path) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", path), headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    /**
     * Post
     *
     * @param headerDetails
     * @return
     */
    protected T post(HashMap<String, String> pathParam, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).pathParams(pathParam).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    /**
     * get with multiple path param
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param pathParam
     * @return
     */
    protected T get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, HashMap<String, String> pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component, pathParam).queryParams(queryParamDetails).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        return response.as(responseClass);

    }

    /**
     * Post without requestBody
     *
     * @param queryParamDetails
     * @param headerDetails
     * @return
     */
    protected Response post1(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    /**
     * @param queryParamDetails
     * @param headerDetails
     * @param jsonRequestBody
     * @return
     */
    protected T delete(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).queryParams(queryParamDetails).config(config).delete();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    protected T deleteWithParam(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).queryParams(queryParamDetails).config(config).delete();
        Allure.addAttachment("Response", response.body().asString());
        return response.as(responseClass);
    }

    protected T delete(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(queryParamDetails).config(config).delete();
        Allure.addAttachment("Response", response.body().asString());
        return response.as(responseClass);
    }

    protected Response deleteWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).queryParams(queryParamDetails).config(config).delete();
        log.debug("response is " + response.body().asString());
        log.debug("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    /**
     * Post , header with String and Jsonobject
     *
     * @param queryParamDetails
     * @param headerDetails
     * @param file
     * @return
     */
    protected T postWithJsonHeader(Map<String, Object> queryParamDetails, HashMap<String, Object> headerDetails, File file) {
        response =
                RestAssuredAPIClientSetup.getGenericClientWithObjectType(beUrl, uri, headerDetails, component).multiPart("uploadRequest", file, "application/*+json").queryParams(queryParamDetails).config(config).post();
        ;
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    /**
     * Post
     *
     * @param
     * @return
     */
    protected T postWithFormParam(HashMap<String, Object> headerDetails, HashMap<String, Object> FormparameterDetails) {
        response = RestAssuredAPIClientSetup.getGenericClientWithFormParams(beUrl, uri, headerDetails, FormparameterDetails, component).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    protected T postWithQueryParam(HashMap<String, Object> headerDetails, HashMap<String, Object> queryParamDetails) {
        response = RestAssuredAPIClientSetup.getGenericClientWithQueryParams(beUrl, uri, headerDetails, queryParamDetails, component).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    protected String postWithoutResponseBody(HashMap<String, String> pathParam, HashMap<String, String> headerDetails) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).pathParams(pathParam).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        log.info("response is" + response);
        return String.valueOf(response.statusCode());
    }

    protected T postWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).post();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response.as(responseClass);
    }

    protected Response get(HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        return response;
    }

    protected Response postWithFileUpload(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String filename) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).multiPart("file", new File(filename), "text/csv").queryParams(queryParamDetails).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    protected String postWithNoResponseBody(HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return String.valueOf(response.statusCode());
    }

    protected Response postWithResponse(HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).post();
        log.info("response is " + response.body().asString());
        log.info("response is " + response.statusCode());
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    protected T put(HashMap<String, String> headerDetails, String jsonRequestBody) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    protected Response getWithResponse(HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).config(config).get();
        Allure.addAttachment("Response", response.body().asString());
        return response;
    }

    protected Response postWithResponseBody(String pathParam, HashMap<String, String> headerDetails, String jsonRequestBody) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).body(jsonRequestBody).config(config).post();
        Allure.addAttachment(getClass().getSimpleName(), response.body().asString());
        return response;
    }

    protected T putWithPathParams(HashMap<String, String> headerDetails, String pathParam) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response.as(responseClass);
    }

    protected Response postWithPathParamAndResponse(HashMap<String, String> headerDetails, String pathParam) {
        response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).config(config).post();
        Allure.addAttachment("Response", response.body().asString());
        return response;
    }

    protected Response putWithPathParamAndBody(HashMap<String, String> headerDetails, String pathParam, String jsonRequestBody) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri.replace("$", pathParam), headerDetails, component).body(jsonRequestBody).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response;
    }

    protected Response putWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        Response response = RestAssuredAPIClientSetup.getGenericClient(beUrl, uri, headerDetails, component).body(jsonRequestBody).config(config).put();
        Allure.addAttachment(getClass().getSimpleName(), response.getBody().asString());
        return response;
    }
}
