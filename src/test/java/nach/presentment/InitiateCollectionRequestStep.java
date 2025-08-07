package nach.presentment;

import api.nach.presentment.InitiateCollection;
import io.qameta.allure.Step;
import nach.NACHConstants;
import org.testng.Assert;
import pojos.nach.presentment.InitiateCollectionPojo;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.util.HashMap;

public class InitiateCollectionRequestStep {

    static InitiateCollection initiateCollection;
    static InitiateCollectionPojo initiateCollectionPojo;
    static String MTX;

    static {
        try {
            initiateCollectionPojo = new InitiateCollectionPojo();
            initiateCollection = new InitiateCollection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public InitiateCollectionRequestStep() {
    }


    @Step()
    public static void initiateCollection(String amount, String product, String umUuid, String transactionId) {

        HashMap<String, String> headerDetails = new HashMap<>();
        if(transactionId.isEmpty() || transactionId == null) {
            if (product.equals("BNPL"))
                MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
            else MTX = ReturnRandomTxnId.returnTxnIDMethod("RP_PID");
        }

        String initiateCollectionRequest = new StringTemplate(NACHConstants.INITIATE_COLLECTION)
                .replace("amount", amount)
                .replace("merchantTxnId",MTX )
                .replace("product", product)
                .replace("umUuid", umUuid)
                .toString();

        initiateCollectionPojo = initiateCollection.post(headerDetails, initiateCollectionRequest);
        Assert.assertNotNull(initiateCollectionPojo.transactionId, "Check if the service is up and the authentication is valid");

    }

}
