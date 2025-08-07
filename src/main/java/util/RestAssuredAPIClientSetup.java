package util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.RestAssuredConfig.config;

@Slf4j
public class RestAssuredAPIClientSetup {

    /**
     *
     * @param defaultBeUrl
     * @param path
     * @param valueMap
     * @param component
     * @return
     */
    public static RequestSpecification getGenericClient(String defaultBeUrl, String path, Map<String, String> valueMap, String component){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri(defaultBeUrl);
        RequestSpecification genericSpec = requestSpecBuilder.build().headers(valueMap);

        return RestAssured.given().spec(genericSpec);
    }

    /**
     * with multiple path param
     * @param defaultBeUrl
     * @param path
     * @param valueMap
     * @param component
     * @return
     */
    public static RequestSpecification getGenericClient(String defaultBeUrl, String path, Map<String, String> valueMap, String component, HashMap<String,String> pathParam){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        RequestSpecification genericSpec;
        requestSpecBuilder.addPathParams(pathParam);
        requestSpecBuilder.setBaseUri(defaultBeUrl);
        genericSpec = requestSpecBuilder.build().headers(valueMap);
        return RestAssured.given().spec(genericSpec);
    }

    /**
     * Headers with Json object
     * @param defaultBeUrl
     * @param path
     * @param valueMap
     * @param component
     * @return
     */
    public static RequestSpecification getGenericClientWithObjectType(String defaultBeUrl, String path, Map<String, Object> valueMap, String component){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        RequestSpecification genericSpec;
        requestSpecBuilder.setBaseUri(defaultBeUrl);
        genericSpec = requestSpecBuilder.build().headers(valueMap);
        return RestAssured.given().spec(genericSpec);
    }

    /**
     * Headers with Json object
     * @param defaultBeUrl
     * @param path
     * @param valueMap
     * @param component
     * @return
     */
    public static RequestSpecification getGenericClientWithFormParams(String defaultBeUrl, String path, Map<String, Object> valueMap, Map<String, Object> formparam ,String component){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        //requestSpecBuilder.setAccept(ContentType.fromContentType("application/x-www-form-urlencoded"));
        requestSpecBuilder.setContentType("application/x-www-form-urlencoded; charset=utf-8");
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.addFormParams(formparam);
        RequestSpecification genericSpec;
        requestSpecBuilder.setBaseUri(defaultBeUrl);
        genericSpec = requestSpecBuilder.build().headers(valueMap);
        return RestAssured.given().spec(genericSpec);
    }
    public static RequestSpecification getGenericClientWithQueryParams(String defaultBeUrl, String path, Map<String, Object> valueMap, Map<String, Object> queryParams ,String component){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.addQueryParams(queryParams);
        RequestSpecification genericSpec;
        requestSpecBuilder.setBaseUri(defaultBeUrl);
        genericSpec = requestSpecBuilder.build().headers(valueMap);
        return RestAssured.given().spec(genericSpec);
    }
}
