package util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ReturnRandomTxnId {
    static String txnID;

    public static String returnTxnIDMethod(String IDType) {

        int randomNum1 = ThreadLocalRandom.current().nextInt(1, 1000000000 + 1);
        String randomNum2 = Integer.toString(randomNum1);
        int randomNum3 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
        String randomNum4 = Integer.toString(randomNum3);

        switch (IDType) {
            case "MTX":
                txnID = "MTXTest12345" + randomNum2;
                break;
            case "RTX":
                txnID = "RTXTest12345" + randomNum2;
                break;
            case "CTX":
                txnID = "CTXTest12345" + randomNum2;
                break;
            case "PGID":
                txnID = "PGIDTest12345" + randomNum2;
                break;
            case "PRT":
                txnID = "PRT-17552-" + randomNum2 + "-" + randomNum4;
                break;
            case "NPCI":
                txnID = "8cff752" + randomNum2;
                break;
            case "RP_PID":
                txnID = randomNum2;
                break;
            case "AADHAAR":
                txnID = "Test" + randomNum2;
                break;
            case "REWARDID":
                txnID = "Reward-automation" + randomNum2;
                break;
            default:
                System.out.println("\nIncorrect txn ID type specified.\n");
                break;
        }
        return txnID;
    }

    public static String returnRandomNumber() {

          String randomNum =   UUID.randomUUID().toString();

        return randomNum;
    }
}
