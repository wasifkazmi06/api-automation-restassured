package neo.fd;

import api.neobank.fd.CreateFD;
import api.neobank.fd.CreateOrder;
import api.neobank.fd.WebhookFdCreation;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.neobank.fd.*;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class CreateFdTest {


    public CreateFdTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    CreateOrder orderCreation = new CreateOrder();
    CreateFD fdCreation = new CreateFD();
    WebhookFdCreation webhook_activateFd = new WebhookFdCreation();
    String razorpayPaymentid;
    String razorpayOrderId ;
    String razorpaySignature;

    @SneakyThrows
    @BeforeClass
    static void setup() {
        RestAssured.registerParser("text/html", Parser.JSON);
    }

    @SneakyThrows
    @Test(priority = 1)
    @Description("get the razorpay order id for the fd creation")
    @Owner("Kareeshma")
    public void createOrderWithNominee() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        headerDetails.put("Content-Type","application/json;charset=UTF-8");
        razorpayPaymentid = "pay_" + RandomStringUtils.randomAlphanumeric(14);
        String request= new StringTemplate(NeoConstants.CREATE_ORDER_WITH_NOMINEE).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).replace("umUuid",configuration.getVerifyUpi_testData().umUuid).replace("amount",configuration.getCreateOrder_nomineedetails().amount).replace("upiId",configuration.getVerifyUpi_testData().upiId).
                replace("name",configuration.getCreateOrder_nomineedetails().name).replace("relationship",configuration.getCreateOrder_nomineedetails().relationship).replace("dob",configuration.getCreateOrder_nomineedetails().dob).toString();
        NomineeDetailsPayload createOrderData = orderCreation.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+createOrderData.getStatus());
        Assert.assertEquals(createOrderData.status,"success");
        razorpayOrderId = createOrderData.getPayload().getOrderId();
        log.info("razorpay orderId is : " +razorpayOrderId);
        log.info("razorpay paymentId is : " +razorpayPaymentid);
        razorpaySignature = FdDBData.calculateRFC2104HMAC(razorpayOrderId+"|"+razorpayPaymentid,"Y7lGfxX6bxJ8SEB9m2SDu3jU");
        log.info("razorpay signature is : " +razorpaySignature);

        //created fd with razorpay orderId,paymentId & signature
        createFD(razorpayOrderId, razorpayPaymentid, razorpaySignature);

        }

    @SneakyThrows
    @Test(priority = 2)
    @Description("get the razorpay order id for the fd creation")
    @Owner("Kareeshma")
    public void createOrderWithoutNominee() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        razorpayPaymentid = "pay_" + RandomStringUtils.randomAlphanumeric(14);
        String request= new StringTemplate(NeoConstants.CREATE_ORDER_WITHOUT_NOMINEE).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).replace("umUuid",configuration.getVerifyUpi_testData().umUuid).replace("amount",configuration.getCreateOrder_nomineedetails().amount).replace("upiId",configuration.getVerifyUpi_testData().upiId).
                replace("name",configuration.getCreateOrder_nomineedetails().name).replace("relationship",configuration.getCreateOrder_nomineedetails().relationship).replace("dob",configuration.getCreateOrder_nomineedetails().dob).toString();
        NomineeDetailsPayload createOrderData = orderCreation.post(queryParamDetails, headerDetails, request);
        log.info("response status : "+createOrderData.getStatus());
        Assert.assertEquals(createOrderData.status,"success");
        razorpayOrderId = createOrderData.getPayload().getOrderId();
        log.info("razorpay orderId is : " +razorpayOrderId);
        log.info("razorpay paymentId is : " +razorpayPaymentid);
        razorpaySignature = FdDBData.calculateRFC2104HMAC(razorpayOrderId+"|"+razorpayPaymentid,"Y7lGfxX6bxJ8SEB9m2SDu3jU");
        log.info("razorpay signature is : " +razorpaySignature);

        //created fd with razorpay orderId,paymentId & signature
        createFD(razorpayOrderId, razorpayPaymentid, razorpaySignature);

    }

    @SneakyThrows
    @Description("create fd with razorpay orderId,paymentId received from create order api")
    @Owner("Kareeshma")
    public void createFD(String razorpay_OrderId, String razorpay_Paymentid, String razorpay_Signature) {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String request= new StringTemplate(NeoConstants.CREATE_FD).replace("lpUuid",configuration.getVerifyUpi_testData().lpUuid).
                replace("razorpayPaymentId",razorpay_Paymentid).replace("razorpayOrderId",razorpay_OrderId).replace("razorpaySignature",razorpay_Signature).toString();
        FdDetails createFDData = fdCreation.post(queryParamDetails, headerDetails, request);

        // verify fd entry in db
        HashMap<String, Integer> payment_status = FdDBData.getfdPaymentStatus(razorpayOrderId);
        HashMap<String, Object> postFdCreation_DbData = FdDBData.getfdDetailsPostCreation(payment_status.get("id").toString());

        if(createFDData.status.equalsIgnoreCase("success")){
            log.info("creditLimit is : "+createFDData.getPayload().creditLimit);
            log.info("fd created successfully");
            log.info("Fd created details : "+postFdCreation_DbData);
            Assert.assertEquals(postFdCreation_DbData.get("fd_status"),3);
        }
        else {
            // verify the payment is success before activating fd with webhook api  in db
            String webhook_respose;
            if(payment_status.get("payment_status") == 2 && (int) postFdCreation_DbData.get("fd_status") == 6){
                webhook_respose = webhookFdCreation();
                if(webhook_respose.equalsIgnoreCase("success")){
                    log.info("Fd created details : "+postFdCreation_DbData);
                    Assert.assertEquals(postFdCreation_DbData.get("fd_status"),3);
                }else{
                    //verify retrial entry in db in case of exception
                    HashMap<String, String> fdRetrial_DbData = FdDBData.getfdRetrialData(postFdCreation_DbData.get("fdId").toString());
                    log.info("Error message for fd retrial failure : " +fdRetrial_DbData.get("error_message"));
                }
            }
            else{
                log.info("payment is either not initiated or failed");
            }
        }


    }

    @SneakyThrows
    @Description("Activate the pending Fd where payment got success but fd creation failed")
    @Owner("Kareeshma")
    public String webhookFdCreation() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat","1");
        String accountNo = RandomStringUtils.randomNumeric(14);
        String request= new StringTemplate(NeoConstants.WEBHOOK_FD_CREATE).replace("entityId",configuration.getFdDetails_testData().entityId).
                replace("amount",configuration.getCreateOrder_nomineedetails().amount).replace("razorpayPaymentId",razorpayPaymentid).
                replace("accountNumber",accountNo).toString();
        log.info("webhook request body : "+request);
        FdDetails webhook_fddata = webhook_activateFd.post(queryParamDetails, headerDetails, request);
        return webhook_fddata.status;
    }


}
