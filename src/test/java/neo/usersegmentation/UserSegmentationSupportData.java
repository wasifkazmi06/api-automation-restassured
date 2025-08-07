package neo.usersegmentation;


import dbUtils.UserSegmentation_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserSegmentationSupportData {


    public static final String tenantName = "User_Segmentation_AS_Tenant_" + new Timestamp(System.currentTimeMillis()).getTime();
    public static final String clientName = "User_Segmentation_AS_Client_" + new Timestamp(System.currentTimeMillis()).getTime();
    public static final String segmentName = "User_Segmentation_AS_Segment_" + new Timestamp(System.currentTimeMillis()).getTime();
    public static String tenantID;
    public static String clientID;
    public static String segmentID;
    public static String uploadHistoryId;
    String inValidTenant,inValidClient,inValidSegment;


    public static final String selectTenant = "SELECT * FROM tenant where id='";
    public static final String selectClient = "SELECT * FROM client where id='";
    public static final String selectSegment = "SELECT * FROM user_segment where id='";
    public static final String selectSubscription = "SELECT * FROM subscription where user_segment_id='";

    File usersValidCSVFile = new File(UserSegmentationConstants.usersValidCSVFile);
    File usersValidMaxSizeCSVFile = new File(UserSegmentationConstants.usersValidMaxSizeCSVFile);
    File usersInvalidCSVFile = new File(UserSegmentationConstants.usersInvalidCSVFile);
    File usersValidCSVFileWithEventData = new File(UserSegmentationConstants.usersValidCSVFileWithEventData);
    File usersInvalidFile = new File(UserSegmentationConstants.usersInValidFile);
    File usersCSVFile = null;

    public String userID = "632d6428298a660001827deb";


    @Step
    public boolean isClientCreated(String clientID, String clientName) throws SQLException, ClassNotFoundException {
        String str = null;
        boolean result;
        ResultSet rs = UserSegmentation_MySQL_DBAccessObject.selectFromMySqlDb(selectClient + clientID + "';");
        while (rs.next()) {
            str = rs.getString("name");
        }
        return str.equals(clientName);
    }

    @Step
    public boolean isTenantCreated(String tenantID, String tenantName) throws SQLException, ClassNotFoundException {
        String str = null;
        boolean result;
        ResultSet rs = UserSegmentation_MySQL_DBAccessObject.selectFromMySqlDb(selectTenant + tenantID + "';");
        while (rs.next()) {
            str = rs.getString("name");
        }
        return str.equals(tenantName);
    }

    @Step
    public boolean isSegmentCreated(String segmentID, String segmentName) throws SQLException, ClassNotFoundException {
        String str = null;
        boolean result;
        ResultSet rs = UserSegmentation_MySQL_DBAccessObject.selectFromMySqlDb(selectSegment + segmentID + "';");
        while (rs.next()) {
            str = rs.getString("name");
        }
        return str.equals(segmentName);
    }


    @Step
    public boolean isSegmentSubscribed(String segmentID) throws SQLException, ClassNotFoundException {
        String str = null;
        boolean result;
        ResultSet rs = UserSegmentation_MySQL_DBAccessObject.selectFromMySqlDb(selectSubscription + segmentID + "';");
        while (rs.next()) {
            str = rs.getString("active");
        }
        return str.equals("1");
    }


    @Step
    public boolean isSegmentUnSubscribed(String segmentID) throws SQLException, ClassNotFoundException {
        String str = null;
        boolean result;
        ResultSet rs = UserSegmentation_MySQL_DBAccessObject.selectFromMySqlDb(selectSubscription + segmentID + "';");
        while (rs.next()) {
            str = rs.getString("active");
        }
        return str.equals("0");
    }


    @DataProvider(name = "createTenantData")
    public Object[][] createTenantData() {
        return new Object[][]{
                        {"validTenantName", "DIGI-01"},
                        {"characterLimit", "DIGI-02"},
                        {"emptySpace", "DIGI-03"},
                        {"emptyState", "DIGI-04"}
                };
    }

    @DataProvider(name = "createClientData")
    public Object[][] createClientData() {
        return new Object[][]{
                {"validClientName", "DIGI-01"},
                {"characterLimit", "DIGI-02"},
                {"emptySpace", "DIGI-03"},
                {"emptyState", "DIGI-04"}
        };
    }

    @DataProvider(name = "createSegmentData")
    public Object[][] createSegmentData() {
        return new Object[][]{
                {"validSegmentName", "DIGI-01"},
                {"characterLimit", "DIGI-02"},
                {"emptySpace", "DIGI-03"},
                {"emptyState", "DIGI-04"}
        };
    }

    @DataProvider(name = "addUsersToSegmentData")
    public Object[][] addUsersToSegmentData() {
        return new Object[][]{
                {"validFileWithValidSegmentIdTenantIdClientId", "DIGI-01"},
                {"validFileWithValidSegmentIdClientIdAndInvalidTenantId", "DIGI-02"},
                {"validFileWithValidSegmentIdTenantIdAndInvalidClientId", "DIGI-03"},
                {"validFileWithValidClientIdTenantIdAndInvalidSegmentId", "DIGI-04"},
                {"noFileUploaded", "DIGI-04"},
                {"invalidFileFormat", "DIGI-04"},
                //{"maxFileSize", "DIGI-04"},
                {"validFileWithIncorrectDataFormat", "DIGI-04"},
                {"validEventFormatFileWithValidClientAndTenant", "DIGI-04"},
                {"validEventFormatFileWithInvalidClientAndTenant", "DIGI-04"}
        };
    }

    @DataProvider(name = "subscribeSegmentData")
    public Object[][] subscribeSegmentData() {
        return new Object[][]{
                {"subscribeWithValidSegmentIdAndClientId", "DIGI-01"},
                {"subscribeWithValidSegmentIdAndInvalidClientId", "DIGI-02"},
                {"subscribeWithInvalidSegmentIdAndInvalidClientId", "DIGI-03"},
                {"subscribeWithInvalidSegmentIdAndValidClientId", "DIGI-04"},
                {"subscribeWithInvalidSegmentIdAndWithoutClientId", "DIGI-04"},
                {"subscribeWithValidSegmentIdAndClientIdWithEventEnabled", "DIGI-04"},
                {"subscribeWithValidSegmentIdAndClientIdWithEventDisabled", "DIGI-04"}
        };
    }


    @DataProvider(name = "uploadHistoryData")
    public Object[][] uploadHistoryData() {
        return new Object[][]{
                {"ValidHistoryId", "DIGI-01"},
                {"InvalidHistoryId", "DIGI-02"}
        };
    }

    @DataProvider(name = "getUserSegmentData")
    public Object[][] getUserSegmentData() {
        return new Object[][]{
                {"ValidUserIdClientIdTenantId", "DIGI-01"},
                {"InvalidUserIdAndValidClientIdTenantId", "DIGI-02"},
                {"InvalidUserIdClientIdTenantId", "DIGI-03"},
                {"InvalidUserIdClientIdAndValidTenantId", "DIGI-04"},
                {"InvalidUserIdTenantIdAndValidClientId", "DIGI-05"},
                {"ValidUserIdAndInvalidTenantIdAndValidClientId", "DIGI-06"},
                {"ValidUserIdTenantIdAndInvalidClientId", "DIGI-07"}
        };
    }

    @DataProvider(name = "getAllActiveSegmentsData")
    public Object[][] getAllActiveSegmentsData() {
        return new Object[][]{
                {"ValidClientId", "DIGI-01"},
                {"InvalidClientId", "DIGI-02"},
                {"WithoutClientId", "DIGI-03"},
                {"ModifiedPageSize", "DIGI-04"},
        };
    }

    @DataProvider(name = "removeUsersToSegmentData")
    public Object[][] removeUsersToSegmentData() {
        return new Object[][]{
                {"validFileWithValidSegmentIdTenantIdClientId", "DIGI-01"},
                {"validFileWithValidSegmentIdClientIdAndInvalidTenantId", "DIGI-02"},
                {"validFileWithValidSegmentIdTenantIdAndInvalidClientId", "DIGI-03"},
                {"validFileWithValidClientIdTenantIdAndInvalidSegmentId", "DIGI-04"},
                {"noFileUploaded", "DIGI-04"},
                {"invalidFileFormat", "DIGI-04"},
                //{"maxFileSize", "DIGI-04"},
                {"validFileWithIncorrectDataFormat", "DIGI-04"},
                {"validEventFormatFileWithValidClientAndTenant", "DIGI-04"},
                {"validEventFormatFileWithInvalidClientAndTenant", "DIGI-04"}
        };
    }

    @DataProvider(name = "unSubscribeSegmentData")
    public Object[][] unSubscribeSegmentData() {
        return new Object[][]{
                {"unsubscribeWithValidSegmentIdAndClientId", "DIGI-01"},
                {"unsubscribeWithValidSegmentIdAndInvalidClientId", "DIGI-02"},
                {"unsubscribeWithInvalidSegmentIdAndInvalidClientId", "DIGI-03"},
                {"unsubscribeWithInvalidSegmentIdAndValidClientId", "DIGI-04"},
                {"unsubscribeWithInvalidSegmentIdAndWithoutClientId", "DIGI-04"}
        };
    }
}
