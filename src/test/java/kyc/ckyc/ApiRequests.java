package kyc.ckyc;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiRequests {

   // public static final boolean PAN_CONSENT_FIELD = true;
   // public static final String PAN_NAME = "";
    public static final String DOB = "714138656";
    //public static final String PAN_NO = "";
    //public static final boolean uploadedFromGallery = false;


    public static JSONObject getPanDocRequest(String panNumber, String panName,String panConsent)  {
        JSONObject dataObject = new JSONObject();

        dataObject.put("PAN_CONSENT_FIELD", panConsent);
        dataObject.put("PAN_NAME", panName);
        dataObject.put("DOB", DOB);
        dataObject.put("PAN_NO", panNumber);
       // dataObject.put("uploadedFromGallery", uploadedFromGallery);
        return dataObject;
    }

    public static JSONObject getEmploymentDataRequest(String employmentType,String fatherName,String incomeRange,String gender) {
        JSONObject dataObject = new JSONObject();
        dataObject.put("EMPLOYMENT_TYPE",employmentType);
        dataObject.put("FATHER_NAME",fatherName);
        dataObject.put("INCOME_RANGE",incomeRange);
        dataObject.put("GENDER",gender);
        return dataObject;

    }
}
