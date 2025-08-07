package neo.usersegmentation;

import api.neobank.usersegmentation.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.usersegmentation.Get_All_Active_Segments_Pojo;
import pojos.neobank.usersegmentation.User_Segmentation_Pojo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserSegmentationTests extends UserSegmentationSupportData {


    public UserSegmentationTests() throws Exception {
        super();
    }

    CreateTenant createTenant = new CreateTenant();
    CreateClient createClient = new CreateClient();
    CreateSegment createSegment = new CreateSegment();
    AddUsersToSegment addUsersToSegment = new AddUsersToSegment();
    SubscribeSegment subscribeSegment = new SubscribeSegment();
    GetUserSegmentIdByUserId getUserSegmentIdByUserId = new GetUserSegmentIdByUserId();
    UnSubscribeSegment unSubscribeSegment = new UnSubscribeSegment();
    RemoveUsersFromSegment removeUsersFromSegment = new RemoveUsersFromSegment();
    GetUploadHistoryStatus getUploadHistoryStatus = new GetUploadHistoryStatus();
    GetAllActiveSegments getAllActiveSegments = new GetAllActiveSegments();

    @SneakyThrows
    @Description("To check if the create Tenant is functional and as intended")
    @Feature("User_Segmentation")
    @Test(priority = 1, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "createTenantData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"})
    public void createTenant(String tenantNameData, String aioCaseKey) {
        log.info("Creating Tenant with " + tenantName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        switch (tenantNameData) {
            case "validTenantName":
                queryParamDetails.put("tenant-name", tenantName);
                break;
            case "characterLimit":
                queryParamDetails.put("tenant-name", "InvalidTenantNameCharacterLimitTestCase45Chars");
                break;
            case "emptySpace":
                queryParamDetails.put("tenant-name", "%20");
                break;
            case "emptyState":
                queryParamDetails.put("tenant-name", "");
                break;
            default:
                throw new IllegalStateException("Unexpected tenant data: " + tenantNameData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo createClientResponse = createTenant.post(queryParamDetails, headerDetails, "");
        if (tenantNameData.equalsIgnoreCase("validTenantName")) {
            String registeredTenantName = createClientResponse.getPayload().getTenantName();
            log.info("Tenant Register as: " + registeredTenantName);
            tenantID = createClientResponse.getPayload().getTenantId();
            Assert.assertEquals(registeredTenantName, tenantName, "Failed to register Tenant in UserSegmentation");
            Assert.assertEquals(isTenantCreated(tenantID, tenantName), true, "Failed to create the Tenant in UserSegmentation DB");
        } else if (tenantNameData.equalsIgnoreCase("characterLimit")) {
            Assert.assertEquals(createClientResponse.getStatus(), "Bad Request", "Tenant registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createClientResponse.getMessage(), "BadRequest : Tenant name should contain less than 45 characters", "Tenant registered with the higher character limit in UserSegmentation");
        } else {
            Assert.assertEquals(createClientResponse.getStatus(), "Bad Request", "Tenant registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createClientResponse.getMessage(), "BadRequest : Given tenant name is empty", "Tenant registered with the invalid data in UserSegmentation");
        }
    }

    @SneakyThrows
    @Description("To check if the create Client is functional and as intended")
    @Feature("User_Segmentation")
    @Test(priority = 2, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "createClientData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"createTenant"})
    public void createClient(String clientNameData, String aioCaseKey) {
        log.info("Creating Client with " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        queryParamDetails.put("client-name", clientName);
        switch (clientNameData) {
            case "validClientName":
                queryParamDetails.put("client-name", clientName);
                break;
            case "characterLimit":
                queryParamDetails.put("client-name", "InvalidClientNameCharacterLimitTestCase45Chars");
                break;
            case "emptySpace":
                queryParamDetails.put("client-name", "%20");
                break;
            case "emptyState":
                queryParamDetails.put("client-name", "");
                break;
            default:
                throw new IllegalStateException("Unexpected client data: " + clientNameData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo createClientResponse = createClient.post(queryParamDetails, headerDetails, "");
        if (clientNameData.equalsIgnoreCase("validClientName")) {
            String registeredClientName = createClientResponse.getPayload().getClientName();
            log.info("Client Registered as: " + registeredClientName);
            clientID = createClientResponse.getPayload().getClientId();
            Assert.assertEquals(registeredClientName, clientName, "Failed to register Client in UserSegmentation");
            Assert.assertEquals(isClientCreated(clientID, clientName), true, "Failed to create the client in UserSegmentation DB");
        } else if (clientNameData.equalsIgnoreCase("characterLimit")) {
            Assert.assertEquals(createClientResponse.getStatus(), "Bad Request", "Client registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createClientResponse.getMessage(), "BadRequest : Client name should contain less than 45 characters", "Client registered with the higher character limit in UserSegmentation");
        } else {
            Assert.assertEquals(createClientResponse.getStatus(), "Bad Request", "Client registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createClientResponse.getMessage(), "BadRequest : Given client name is empty", "Client registered with the invalid data in UserSegmentation");
        }
    }

    @SneakyThrows
    @Description("To check if the create segment is functional and as intended")
    @Feature("User_Segmentation")
    @Test(priority = 3, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "createSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"createClient"})
    public void createSegment(String createSegmentData, String aioCaseKey) {
        log.info("Creating Client with " + segmentName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        switch (createSegmentData) {
            case "validSegmentName":
                queryParamDetails.put("user-segment-name", segmentName);
                break;
            case "characterLimit":
                queryParamDetails.put("user-segment-name", "InvalidClientNameCharacterLimitTestCase45Chars");
                break;
            case "emptySpace":
                queryParamDetails.put("user-segment-name", "%20");
                break;
            case "emptyState":
                queryParamDetails.put("user-segment-name", "");
                break;
            default:
                throw new IllegalStateException("Unexpected segment data: " + createSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo createSegmentResponse = createSegment.post(queryParamDetails, headerDetails, "");
        if (createSegmentData.equalsIgnoreCase("validSegmentName")) {
            String registeredSegmentName = createSegmentResponse.getPayload().getUserSegmentName();
            log.info("Segment Registered as: " + registeredSegmentName);
            segmentID = createSegmentResponse.getPayload().getUserSegmentId();
            Assert.assertEquals(registeredSegmentName, segmentName, "Failed to register segment in UserSegmentation");
            Assert.assertEquals(isSegmentCreated(segmentID, segmentName), true, "Failed to create the client in UserSegmentation DB");

        } else if (createSegmentData.equalsIgnoreCase("characterLimit")) {
            Assert.assertEquals(createSegmentResponse.getStatus(), "Bad Request", "Client registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createSegmentResponse.getMessage(), "BadRequest : User segment name should contain less than 45 characters", "Client registered with the higher character limit in UserSegmentation");
        } else {
            Assert.assertEquals(createSegmentResponse.getStatus(), "Bad Request", "Client registered with the invalid data in UserSegmentation");
            Assert.assertEquals(createSegmentResponse.getMessage(), "BadRequest : Given user segment name is empty", "Client registered with the invalid data in UserSegmentation");
        }
    }


    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 4, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "addUsersToSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"createSegment"})
    public void addUsersToSegment(String addUsersToSegmentData, String aioCaseKey) {
        inValidTenant = tenantID + "1001";
        inValidClient = clientID + "10001";
        inValidSegment = segmentID + "1001";

        log.info("Creating Client with " + segmentName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "multipart/form-data");
        switch (addUsersToSegmentData) {
            case "validFileWithValidSegmentIdTenantIdClientId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidSegmentIdClientIdAndInvalidTenantId":
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidSegmentIdTenantIdAndInvalidClientId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidClientIdTenantIdAndInvalidSegmentId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", inValidSegment);
                usersCSVFile = usersValidCSVFile;
                break;
            case "noFileUploaded":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "invalidFileFormat":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersInvalidFile;
                break;
            case "maxFileSize":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidMaxSizeCSVFile;
                break;
            case "validFileWithIncorrectDataFormat":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersInvalidCSVFile;
                break;
            case "validEventFormatFileWithValidClientAndTenant":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFileWithEventData;
                break;
            case "validEventFormatFileWithInvalidClientAndTenant":
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFileWithEventData;
                break;
            default:
                throw new IllegalStateException("Unexpected user addition data: " + addUsersToSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        if (addUsersToSegmentData.equalsIgnoreCase("noFileUploaded")) {
            User_Segmentation_Pojo addUsersToSegmentResponse = addUsersToSegment.post(queryParamDetails, headerDetails);
            Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Bad Request", "Add User data is success without adding file or invalid file");
            Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("CSV File missing or wrong format provided for the file in the request body "), "Add User data is success without adding file or invalid file");

        } else {
            User_Segmentation_Pojo addUsersToSegmentResponse = addUsersToSegment.post(queryParamDetails, headerDetails, usersCSVFile);
            if (addUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdTenantIdClientId") || addUsersToSegmentData.equalsIgnoreCase("validEventFormatFileWithValidClientAndTenant")) {
                String registeredSegmentName = addUsersToSegmentResponse.getPayload().getUserSegmentName();
                log.info("Tenant Register as: " + registeredSegmentName);
                uploadHistoryId = addUsersToSegmentResponse.getPayload().getUploadHistoryId();
                Assert.assertEquals(addUsersToSegmentResponse.getPayload().getUserSegmentId(), segmentID, "Failed to upload users to the segment");
            } else if (addUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdClientIdAndInvalidTenantId") || addUsersToSegmentData.equalsIgnoreCase("validEventFormatFileWithInvalidClientAndTenant")) {
                Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid tenant id");
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No tenant found for tenant id"), "User data is uploaded for the invalid tenant id");
            } else if (addUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdTenantIdAndInvalidClientId")) {
                Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid client id");
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No client found for client id"), "User data is uploaded for the invalid client id");
            } else if (addUsersToSegmentData.equalsIgnoreCase("validFileWithValidClientIdTenantIdAndInvalidSegmentId")) {
                Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid segment id");
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No user segment found for user segment id"), "User data is uploaded for the invalid segment id");
            } else if (addUsersToSegmentData.equalsIgnoreCase("invalidFileFormat")) {
                Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Bad Request", "Add User data is success without adding file or invalid file");
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("BadRequest : invalid file extension, only csv files are excepted"), "Add User data is success without adding file or invalid file");
            } else if (addUsersToSegmentData.equalsIgnoreCase("maxFileSize")) {
                Assert.assertEquals(addUsersToSegmentResponse.getStatus(), "Payload Too Large", "Add User data is success without adding file");
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("MaxUploadSizeExceededException : the request was rejected because its size"), "Add user api is able to accept large size file");
            } else if (addUsersToSegmentData.equalsIgnoreCase("validFileWithIncorrectDataFormat")) {
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("CSVParsingException"), "Add User data is success for invalid data");
            } else if (addUsersToSegmentData.equalsIgnoreCase("validFileWithIncorrectDataFormat")) {
                Assert.assertTrue(addUsersToSegmentResponse.getMessage().contains("CSVParsingException"), "Add User data is success without adding file");
            }
        }
    }

    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 5, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "subscribeSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"addUsersToSegment"})
    public void subscribeSegment(String subscribeSegmentData, String aioCaseKey) {
        log.info("Subscribe Segment " + segmentName + "For client " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        switch (subscribeSegmentData) {
            case "subscribeWithValidSegmentIdAndClientId":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "subscribeWithValidSegmentIdAndInvalidClientId":
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "subscribeWithInvalidSegmentIdAndInvalidClientId":
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", inValidSegment);
                break;
            case "subscribeWithInvalidSegmentIdAndValidClientId":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", inValidSegment);
                break;
            case "subscribeWithInvalidSegmentIdAndWithoutClientId":
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "subscribeWithValidSegmentIdAndClientIdWithEventEnabled":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                queryParamDetails.put("enable-ct-events", true);
                break;
            case "subscribeWithValidSegmentIdAndClientIdWithEventDisabled":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                queryParamDetails.put("enable-ct-events", false);
                break;
            default:
                throw new IllegalStateException("Unexpected subscribe data: " + subscribeSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo subscribedSegmentResponse = subscribeSegment.post(queryParamDetails, headerDetails, "");
        if (subscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndClientId")) {
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentName(), segmentName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientName(), clientName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientId(), clientID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentId(), segmentID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(isSegmentSubscribed(segmentID), true, "Failed to subscribe the segment");
        } else if (subscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndInvalidClientId") || subscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndInvalidClientId")) {
            Assert.assertEquals(subscribedSegmentResponse.getStatus(), "Not Found", "segment subscribed with invalid client id");
            Assert.assertTrue(subscribedSegmentResponse.getMessage().contains("EntityNotFoundException : Client not found with given client id"), "segment subscribed with invalid client id");

        } else if (subscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndValidClientId")) {
            Assert.assertEquals(subscribedSegmentResponse.getStatus(), "Not Found", "segment subscribed with invalid client id");
            Assert.assertTrue(subscribedSegmentResponse.getMessage().contains("EntityNotFoundException : User segment not found with given user segment id"), "segment subscribed with invalid segment id");

        } else if (subscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndWithoutClientId")) {
            Assert.assertEquals(subscribedSegmentResponse.getStatus(), "Bad Request", "segment subscribed without client id header");
            Assert.assertTrue(subscribedSegmentResponse.getMessage().contains("Header parameter missing : Required request header 'Client-Id' for method parameter type String is not present"), "segment subscribed without client id header");

        } else if (subscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndClientIdWithEventEnabled")) {
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentName(), segmentName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientName(), clientName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientId(), clientID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentId(), segmentID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getEnableCTEvents(), "true", "Failed to enable ct event to subscribe segment in UserSegmentation");
            Assert.assertEquals(isSegmentSubscribed(segmentID), true, "Failed to subscribe the segment");
        } else if (subscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndClientIdWithEventDisabled")) {
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentName(), segmentName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientName(), clientName, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getClientId(), clientID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getUserSegmentId(), segmentID, "Failed to subscribe segment in UserSegmentation");
            Assert.assertEquals(subscribedSegmentResponse.getPayload().getEnableCTEvents(), "false", "Failed to disable ct event to subscribe segment in UserSegmentation");
            Assert.assertEquals(isSegmentSubscribed(segmentID), true, "Failed to subscribe the segment");
        }
    }

    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 6, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "uploadHistoryData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"removeUsersToSegment"})
    public void getUploadHistoryStatus(String uploadHistoryData, String aioCaseKey) {
        log.info("get upload history status for the users upload in the segment " + segmentName + "For client " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        switch (uploadHistoryData) {
            case "ValidHistoryId":
                User_Segmentation_Pojo uploadHistoryStatusResponse = getUploadHistoryStatus.get(queryParamDetails, headerDetails, uploadHistoryId);
                Assert.assertEquals(uploadHistoryStatusResponse.getPayload().getUploadStatus(), "completed", "Failed to upload users to the segment" + segmentName + " in UserSegmentation");
                Assert.assertEquals(uploadHistoryStatusResponse.getPayload().getCreatedBy(), clientName, "Incorrect creator Name for the " + segmentName + " in UserSegmentation");
                Assert.assertEquals(uploadHistoryStatusResponse.getPayload().getTenantId(), tenantID, "Incorrect tenantID for the upload to the " + segmentName + " in UserSegmentation");
                Assert.assertEquals(uploadHistoryStatusResponse.getPayload().getUserSegmentName(), segmentName, "Incorrect segmentName for the upload to the " + segmentName + " in UserSegmentation");
                Assert.assertEquals(uploadHistoryStatusResponse.getPayload().getUserSegmentId(), segmentID, "Incorrect segmentID for the upload to the " + segmentName + " in UserSegmentation");
                break;
            case "InvalidHistoryId":
                String invalidHistoryId = uploadHistoryId + "10001";
                User_Segmentation_Pojo invalidUploadHistoryResponse = getUploadHistoryStatus.get(queryParamDetails, headerDetails, invalidHistoryId);
                Assert.assertEquals(invalidUploadHistoryResponse.getStatus(), "Not Found", "Failed for invalid upload history id " + invalidHistoryId);
                Assert.assertEquals(invalidUploadHistoryResponse.getMessage(), "EntityNotFoundException : Upload history not found with given id", "Failed for invalid upload history id " + invalidHistoryId);
                break;
            default:
                throw new IllegalStateException("Unexpected uploadHistory data: " + uploadHistoryData);
        }

    }

    @SneakyThrows
    @Description("To check the user segment mapped for the user")
    @Feature("User_Segmentation")
    @Test(priority = 7, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "getUserSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"subscribeSegment"})
    public void getUserSegmentID(String getUserSegmentData, String aioCaseKey) {
        log.info("getUserSegmentID test started");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        String invalidUserId = userID + "invalid";
        switch (getUserSegmentData) {
            case "ValidUserIdClientIdTenantId":
                queryParamDetails.put("user-id", userID);
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                break;
            case "InvalidUserIdAndValidClientIdTenantId":
                queryParamDetails.put("user-id", invalidUserId);
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                break;
            case "InvalidUserIdClientIdTenantId":
                queryParamDetails.put("user-id", invalidUserId);
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", inValidClient);
                break;
            case "InvalidUserIdClientIdAndValidTenantId":
                queryParamDetails.put("user-id", invalidUserId);
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", inValidClient);
                break;
            case "InvalidUserIdTenantIdAndValidClientId":
                queryParamDetails.put("user-id", invalidUserId);
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", clientID);
                break;
            case "ValidUserIdAndInvalidTenantIdAndValidClientId":
                queryParamDetails.put("user-id", userID);
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", clientID);
                break;
            case "ValidUserIdTenantIdAndInvalidClientId":
                queryParamDetails.put("user-id", userID);
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", inValidClient);
                break;
            default:
                throw new IllegalStateException("Unexpected uploadHistory data: " + getUserSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo getUserSegmentIdByUserIdResponse = getUserSegmentIdByUserId.get(queryParamDetails, headerDetails);
        if (getUserSegmentData.equalsIgnoreCase("ValidUserIdClientIdTenantId")) {
            String user_Received_SegmentID = getUserSegmentIdByUserIdResponse.getPayload().getUserSegmentId();
            log.info("segment ID is : " + user_Received_SegmentID);
            Assert.assertEquals(segmentID, user_Received_SegmentID, "Failed to get correct user Segment id");
        } else if (getUserSegmentData.equalsIgnoreCase("InvalidUserIdAndValidClientIdTenantId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + clientID + " has not subscribed any user segment which has user"));
        } else if (getUserSegmentData.equalsIgnoreCase("InvalidUserIdClientIdTenantId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + inValidClient + " has not subscribed any user segment which has user " + invalidUserId + " of tenant " + inValidTenant));
        } else if (getUserSegmentData.equalsIgnoreCase("InvalidUserIdClientIdAndValidTenantId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + inValidClient + " has not subscribed any user segment which has user " + invalidUserId + " of tenant " + tenantID));
        } else if (getUserSegmentData.equalsIgnoreCase("InvalidUserIdTenantIdAndValidClientId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + clientID + " has not subscribed any user segment which has user " + invalidUserId + " of tenant " + inValidTenant));
        } else if (getUserSegmentData.equalsIgnoreCase("ValidUserIdAndInvalidTenantIdAndValidClientId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + clientID + " has not subscribed any user segment which has user " + userID + " of tenant " + inValidTenant));
        } else if (getUserSegmentData.equalsIgnoreCase("ValidUserIdTenantIdAndInvalidClientId")) {
            Assert.assertEquals(getUserSegmentIdByUserIdResponse.getStatus(), "Not Found", "Failed for invalid user id " + invalidUserId);
            Assert.assertTrue(getUserSegmentIdByUserIdResponse.getMessage().contains("EntityNotFoundException : Client " + inValidClient + " has not subscribed any user segment which has user " + userID + " of tenant " + tenantID));
        }
    }

    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 7, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "getAllActiveSegmentsData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"})
    public void setGetAllActiveSegments(String getAllActiveSegmentsData, String aioCaseKey) {
        log.info("get all active segments for the selected the segment " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        switch (getAllActiveSegmentsData) {
            case "ValidClientId":
                headerDetails.put("Client-Id", "1009");
                queryParamDetails.put("page-number", 1);
                queryParamDetails.put("page-size", 10);
                break;
            case "InvalidClientId":
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("page-number", 1);
                queryParamDetails.put("page-size", 10);
                break;
            case "WithoutClientId":
                queryParamDetails.put("page-number", 1);
                queryParamDetails.put("page-size", 10);
                break;
            case "ModifiedPageSize":
                headerDetails.put("Client-Id", "1009");
                queryParamDetails.put("page-number", 9);
                queryParamDetails.put("page-size", 1);
                break;
            default:
                throw new IllegalStateException("Unexpected uploadHistory data: " + getAllActiveSegmentsData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        Get_All_Active_Segments_Pojo getAllActiveSegmentsResponse = getAllActiveSegments.get(queryParamDetails, headerDetails);
        if (getAllActiveSegmentsData.contains("ValidClientId")) {
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).skip(2).findAny().get().getUserSegmentId().equalsIgnoreCase(("1043")));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).skip(2).findFirst().get().getUserSegmentName().equalsIgnoreCase("UJ_Segment_Test_All_03"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).skip(2).findFirst().get().getClientId().equalsIgnoreCase("1009"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).skip(2).findFirst().get().getClientName().equalsIgnoreCase("Test_Journey_Client"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).skip(2).findFirst().get().getEnableCTEvents().equalsIgnoreCase("false"));
        } else if (getAllActiveSegmentsData.contains("InvalidHistoryId")) {
            Assert.assertEquals(getAllActiveSegmentsResponse.getStatus(), "Not Found", "Failed to get valid response for invalid clientid");
            Assert.assertTrue(getAllActiveSegmentsResponse.getMessage().contains("EntityNotFoundException : Client not found with client id " + inValidClient));
        } else if (getAllActiveSegmentsData.contains("WithoutClientId")) {
            Assert.assertEquals(getAllActiveSegmentsResponse.getStatus(), "Bad Request", "Failed to get valid response for no clientId");
            Assert.assertTrue(getAllActiveSegmentsResponse.getMessage().contains("Header parameter missing : Required request header 'Client-Id' for method parameter type String is not present"));
        } else if (getAllActiveSegmentsData.contains("ModifiedPageSize")) {
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).findFirst().get().getUserSegmentId().equalsIgnoreCase(("1036")));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).findFirst().get().getUserSegmentName().equalsIgnoreCase("UJ_Segment_Test_02"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).findFirst().get().getClientId().equalsIgnoreCase("1009"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).findFirst().get().getClientName().equalsIgnoreCase("Test_Journey_Client"));
            Assert.assertTrue(Arrays.stream(getAllActiveSegmentsResponse.getPayload()).findFirst().get().getEnableCTEvents().equalsIgnoreCase("false"));
        }
    }


    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 8, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "removeUsersToSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"getUserSegmentID"})
    public void removeUsersToSegment(String removeUsersToSegmentData, String aioCaseKey) {
        log.info("removeUsersToSegment" + segmentName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "multipart/form-data");
        switch (removeUsersToSegmentData) {
            case "validFileWithValidSegmentIdTenantIdClientId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidSegmentIdClientIdAndInvalidTenantId":
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidSegmentIdTenantIdAndInvalidClientId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFile;
                break;
            case "validFileWithValidClientIdTenantIdAndInvalidSegmentId":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", inValidSegment);
                usersCSVFile = usersValidCSVFile;
                break;
            case "noFileUploaded":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "invalidFileFormat":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersInvalidFile;
                break;
            case "maxFileSize":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidMaxSizeCSVFile;
                break;
            case "validFileWithIncorrectDataFormat":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersInvalidCSVFile;
                break;
            case "validEventFormatFileWithValidClientAndTenant":
                headerDetails.put("Tenant-Id", tenantID);
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFileWithEventData;
                break;
            case "validEventFormatFileWithInvalidClientAndTenant":
                headerDetails.put("Tenant-Id", inValidTenant);
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                usersCSVFile = usersValidCSVFileWithEventData;
                break;
            default:
                throw new IllegalStateException("Unexpected user addition data: " + removeUsersToSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        if (removeUsersToSegmentData.equalsIgnoreCase("noFileUploaded")) {
            User_Segmentation_Pojo removeUsersToSegmentResponse = removeUsersFromSegment.post(queryParamDetails, headerDetails);
            Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Bad Request", "Add User data is success without adding file or invalid file");
            Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("CSV File missing or wrong format provided for the file in the request body "), "Add User data is success without adding file or invalid file");

        } else {
            User_Segmentation_Pojo removeUsersToSegmentResponse = removeUsersFromSegment.post(queryParamDetails, headerDetails, usersCSVFile);
            if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdTenantIdClientId") || removeUsersToSegmentData.equalsIgnoreCase("validEventFormatFileWithValidClientAndTenant")) {
                String registeredSegmentName = removeUsersToSegmentResponse.getPayload().getUserSegmentName();
                log.info("Users data to remove from the Segment is: " + registeredSegmentName);
                uploadHistoryId = removeUsersToSegmentResponse.getPayload().getUploadHistoryId();
                Assert.assertEquals(registeredSegmentName, segmentName, "Failed to remove users from the segment");
                Assert.assertEquals(removeUsersToSegmentResponse.getPayload().getUserSegmentId(), segmentID, "Failed to upload users to Remove from the segment");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdClientIdAndInvalidTenantId") || removeUsersToSegmentData.equalsIgnoreCase("validEventFormatFileWithInvalidClientAndTenant")) {
                Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid tenant id");
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No tenant found for tenant id"), "User data is uploaded for the invalid tenant id");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithValidSegmentIdTenantIdAndInvalidClientId")) {
                Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid client id");
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No client found for client id"), "User data is uploaded for the invalid client id");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithValidClientIdTenantIdAndInvalidSegmentId")) {
                Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Not Found", "User data is uploaded for the invalid segment id");
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("EntityNotFoundException : No user segment found for user segment id"), "User data is uploaded for the invalid segment id");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("invalidFileFormat")) {
                Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Bad Request", "Remove User data is success without adding file or invalid file");
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("BadRequest : invalid file extension, only csv files are excepted"), "Add User data is success without adding file or invalid file");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("maxFileSize")) {
                Assert.assertEquals(removeUsersToSegmentResponse.getStatus(), "Payload Too Large", "Remove User data is success without adding file");
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("MaxUploadSizeExceededException : the request was rejected because its size"), "Add user api is able to accept large size file");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithIncorrectDataFormat")) {
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("CSVParsingException"), "Remove User data is success for invalid data");
            } else if (removeUsersToSegmentData.equalsIgnoreCase("validFileWithIncorrectDataFormat")) {
                Assert.assertTrue(removeUsersToSegmentResponse.getMessage().contains("CSVParsingException"), "Remove User data is success without adding file");
            }
        }
    }

    @SneakyThrows
    @Feature("User_Segmentation")
    @Test(priority = 9, dataProviderClass = UserSegmentationSupportData.class, dataProvider = "unSubscribeSegmentData", groups = {"User_Segmentation_Sanity", "User_Segmentation_Regression"}, dependsOnMethods = {"removeUsersToSegment"})
    public void unSubscribeSegment(String unSubscribeSegmentData, String aioCaseKey) throws SQLException, ClassNotFoundException {
        log.info("unsubscribe Segment " + segmentName + "For client " + clientName);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Accept", "application/json");
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Client-Id", clientID);
        queryParamDetails.put("user-segment-id", segmentID);
        switch (unSubscribeSegmentData) {
            case "unsubscribeWithValidSegmentIdAndClientId":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "unsubscribeWithValidSegmentIdAndInvalidClientId":
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            case "unsubscribeWithInvalidSegmentIdAndInvalidClientId":
                headerDetails.put("Client-Id", inValidClient);
                queryParamDetails.put("user-segment-id", inValidSegment);
                break;
            case "unsubscribeWithInvalidSegmentIdAndValidClientId":
                headerDetails.put("Client-Id", clientID);
                queryParamDetails.put("user-segment-id", inValidSegment);
                break;
            case "unsubscribeWithInvalidSegmentIdAndWithoutClientId":
                queryParamDetails.put("user-segment-id", segmentID);
                break;
            default:
                throw new IllegalStateException("Unexpected subscribe data: " + unSubscribeSegmentData);
        }
        log.info("Header Details " + headerDetails);
        log.info("Param Details " + queryParamDetails);
        User_Segmentation_Pojo unSubscribedSegmentResponse = unSubscribeSegment.post(queryParamDetails, headerDetails, "");
        if (unSubscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndClientId")) {
            Assert.assertEquals(unSubscribedSegmentResponse.getMessage(), "success", "Failed to unsubscribe segment in UserSegmentation");
            Assert.assertEquals(isSegmentUnSubscribed(segmentID), true, "Failed to update the segment id to unsubscibe");

        } else if (unSubscribeSegmentData.equalsIgnoreCase("subscribeWithValidSegmentIdAndInvalidClientId") || unSubscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndInvalidClientId")) {
            Assert.assertEquals(unSubscribedSegmentResponse.getStatus(), "Not Found", "segment subscribed with invalid client id");
            Assert.assertTrue(unSubscribedSegmentResponse.getMessage().contains("EntityNotFoundException : Client not found with given client id"), "segment unsubscribed with invalid client id");

        } else if (unSubscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndValidClientId")) {
            Assert.assertEquals(unSubscribedSegmentResponse.getStatus(), "Not Found", "segment subscribed with invalid client id");
            Assert.assertTrue(unSubscribedSegmentResponse.getMessage().contains("EntityNotFoundException : User segment not found with given user segment id"), "segment unsubscribed with invalid segment id");

        } else if (unSubscribeSegmentData.equalsIgnoreCase("subscribeWithInvalidSegmentIdAndWithoutClientId")) {
            Assert.assertEquals(unSubscribedSegmentResponse.getStatus(), "Bad Request", "segment subscribed without client id header");
            Assert.assertTrue(unSubscribedSegmentResponse.getMessage().contains("Header parameter missing : Required request header 'Client-Id' for method parameter type String is not present"), "segment unsubscribed without client id header");
        }
    }

}
