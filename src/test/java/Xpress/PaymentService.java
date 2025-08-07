package Xpress;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.Xpress.LoanDetailsPojo;
import util.StringTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Xpress.AmsService.fetchMuid;

public class PaymentService extends XpressData {
    public PaymentService() throws Exception {
    }

    public static List<Integer> FetchMaxDPDOfLoan(String mobileNumber) throws Exception {
        String masterUserId = fetchMuid(mobileNumber);
        // Request headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("api-key", "DxqyE9hntY9J1drGp");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("master_user_id", masterUserId);
        // Execute the API call
        List<Integer> loanIdsList = new ArrayList<>();
        boolean allDpdsCleared = false;
        while (!allDpdsCleared) {
            // Fetch DPD data
            Response fetchMaxDpdResponse = fetchMaxDPD.get(queryParams, headers);

            if (fetchMaxDpdResponse.statusCode() == 200) {
                InputStream inputStream = fetchMaxDpdResponse.asInputStream();
                ObjectMapper objectMapper = new ObjectMapper();
                LoanDetailsPojo loanData = objectMapper.readValue(inputStream, LoanDetailsPojo.class);

                allDpdsCleared = true; // Assume DPDs cleared unless proven otherwise

                for (LoanDetailsPojo.Data item : loanData.getData()) {
                    if (item.getPrevious_dpd() != null && item.getPrevious_dpd() > 0) {
                        // If a non-zero DPD is encountered, mark dpds as not cleared
                        allDpdsCleared = false;

                        // Attempt to clear DPD through makeEMIPayment
                        String boostLoanId = String.valueOf(item.getBoost_loan_id());
                        String date = convertDate(item.getDate().toString(), "yyyy-MM-dd");
                        double installment = Double.parseDouble(item.getInstallment()) + 1000;
                        String amount = String.valueOf(installment);
                        makeEMIPayment(boostLoanId, date, amount);

                        System.out.println("Clearing DPD for Boost Loan ID: " + boostLoanId);
                    }
                }

                // Extract loan IDs
                loanIdsList.clear(); // Clear the list before re-populating
                loanIdsList.addAll(loanData.getLoan_ids());

                // Log every loop iteration for visibility
                if (!allDpdsCleared) {
                    System.out.println("DPDs not yet cleared. Retrying...");
                }

            } else {
                System.out.println("Failed to fetch data. Status code: " + fetchMaxDpdResponse.statusCode());
                // Optional: Add a delay or break if API keeps failing
                break;
            }

            // Optional: Add a delay between retries to avoid excessive API calls
            try {
                Thread.sleep(2000); // 2-second delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (allDpdsCleared) {
            System.out.println("All DPDs cleared successfully.");
        } else {
            System.out.println("DPDs could not be cleared. Exiting.");
        }

        System.out.println("loan_ids: " + loanIdsList);
        return loanIdsList;
    }

    private static void makeEMIPayment(String boostLoanId, String date, String amount) {
        // Implement this method to update data as required

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("AUTOMATION-KEY", "Dz9diCI31G7vtBt7");

        String paymentApiRequest = null;
        paymentApiRequest = new StringTemplate(XpressData.PAYMENT_API_REQUEST)
                .replace("date", date)
                .replace("amount", amount)
                .toString();
        Response fetchPaymentApiResponse = paymentAPI.postWithPathParamsWithReponse(headers, paymentApiRequest, boostLoanId);
        Assert.assertEquals(fetchPaymentApiResponse.getStatusCode(), 200, "Getting Invalid status code for user registration api.");
    }
}
