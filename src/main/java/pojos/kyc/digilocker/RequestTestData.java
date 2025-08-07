package pojos.kyc.digilocker;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTestData {

    private String lp_apiKey;
    private String mbe_apiKey;
    private String ps_apiKey;
    private String smb_apiKey;
    private String redirectURL;
    private String principalID;
    private String txn_id;
    private String txn_id_startedState;
    private String txn_id_approvedState;
    private String principalID_approvedState;
    private String principalID_declinedState;
    private String source1;
    private String source2;
    private String source3;
    private String source4;
    private String txn_id_autoDeclinedState;
    private String ps_principalId;
    private String smb_principalId;
    private String ps_txnId;
    private String smb_txnId;


}
