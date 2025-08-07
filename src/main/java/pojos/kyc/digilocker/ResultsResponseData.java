package pojos.kyc.digilocker;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class ResultsResponseData {
    private String digilockerStatus;
    private ArrayList<DoumentsPojo> documents;
    private Object rawWebhook;
}
