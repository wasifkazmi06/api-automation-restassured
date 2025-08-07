package neo.fd;

import api.neobank.fd.GetFDList;
import api.neobank.fd.GetFdCount;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import neo.NeoConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.neobank.fd.Configuration;
import pojos.neobank.fd.FdList;
import pojos.neobank.fd.ReadFdList;

import java.util.HashMap;
import java.util.Map;

import static util.readYml.readYamlFile;

@Slf4j
public class ReadFdListTest {

    public ReadFdListTest() throws Exception {
        super();
    }
    Configuration configuration = readYamlFile(Configuration.class, NeoConstants.FD_TESTDATA) ;
    GetFDList fdListPayload = new GetFDList();
    GetFdCount fdCount = new GetFdCount();
    String TestCustomer_UUID =  configuration.getVerifyUpi_testData().lpUuid;

    @SneakyThrows
    @Test
    @Description("fetch the FD list present for an user")
    @Owner("Kareeshma")
    public void getFDList() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat", "1");
        ReadFdList fetchFDList = fdListPayload.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fetchFDList.status,"success");
        log.info("Fd List size : " +fetchFDList.getPayload().fdList.size());
    }

    @SneakyThrows
    @Test
    @Description("fetch the total FD count present for an user")
    @Owner("Kareeshma")
    public void getFDCount() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Tenanat", "1");
        FdList fetchFDCount = fdCount.get(queryParamDetails, headerDetails,TestCustomer_UUID);
        Assert.assertEquals(fetchFDCount.status,"success");
        log.info("Fd count of user " +TestCustomer_UUID + " : " +fetchFDCount.getPayload());
    }


}
