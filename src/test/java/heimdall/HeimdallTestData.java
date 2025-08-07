package heimdall;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HeimdallTestData extends HeimdallSupportData {

    String ValidUserId_Lazypay = "8179250614667824840",
            ValidUserId_PS = "8179250614667824836",
            InvalidUserId_Lazypay="ABCD5788085793037870401",
            ValidlpUuid_Lazypay = "12749009",
            ValidloanId_Lazypay = "STPLSC01099996740017",
            ValidtransactionId_Lazypay="TXN1684741791178",
            DuplicatetransactionId_Lazypay="TXN1684741791178",
            ValidsanctionedAmount_Lazypay="10000",
            InvalidsanctionedAmount_Lazypay="0",
            lessThanTxnAmount="500",
            ValidtxnAmount_Lazypay="1000",
            InvalidtxnAmount_Lazypay="0",
            GreaterThanSecuredAmount="30001",
            ValidProductSegments_LP_Value1="BNPL",
            ValidProductSegments_LP_Value2="PersonalLoan",
            ValidProductSegments_PS="PersonalLoan",
            InvalidProductSegments_Lazypay="Loan",
            ValidVoucherType_type1="Receipt",
            ValidVoucherType_type2="Payment",
            InvalidVoucherType="Payments",
            ValidTxnType_type1="1",ValidTxnType_type2="2",ValidTxnType_type3="3",ValidTxnType_type4="4",ValidTxnType_type5="5",
            ValidTxnType_type6="6",ValidTxnType_type7="7",ValidTxnType_type8="8",ValidTxnType_type9="9",ValidTxnType_type10="10",
            ValidTxnType_type11="11",ValidTxnType_type12="12",ValidTxnType_type13="13",ValidTxnType_type14="14",
            ValidTxnType_type15="15",ValidTxnType_type16="16",ValidTxnType_type17="17",ValidTxnType_type18="18",ValidTxnType_type19="19",
            ValidTxnType_type20="20",ValidTxnType_type21="21",ValidTxnType_type22="22",ValidTxnType_type23="23",ValidTxnType_type24="24",
            ValidTxnType_type25="26",ValidTxnType_type26="27",ValidTxnType_type27="28",ValidTxnType_type28="29",ValidTxnType_type29="30",
            ValidTxnType_type30="31", ValidTxnType_type31="34",ValidTxnType_type32="35",ValidTxnType_type33="40",ValidTxnType_type34="41",
            ValidTxnType_type35="42",ValidTxnType_type36="43",ValidTxnType_type37="44",
            InvalidTxnType_Lazypay="EMI",
            ValidInstrumentType_type1="DDBCPO",ValidInstrumentType_type2="Cheque",ValidInstrumentType_type3="RTGSNEFT",ValidInstrumentType_type4="Cash",
            ValidInstrumentType_type5="ECS",ValidInstrumentType_type6="Debit%20Card",ValidInstrumentType_type7="Auto%20Debit",ValidInstrumentType_type8="Net%20Banking",
            ValidInstrumentType_type9="Money%20Transfer",ValidInstrumentType_type10="Standing%20Instruction", ValidInstrumentType_type11="EBPP%20-%20Bill%20Desk",ValidInstrumentType_type12="EBPP%20-%20TPL",
            ValidInstrumentType_type13="CreditCard",ValidInstrumentType_type14="UPI",
            InvalidInstrumentType_Lazypay="Netbanking",
            ValidSource_Lazypay="LazyPay",
            ValidSource_PS="PSCORE",
            InvalidSource="LP",
            ValidSanctionDate = "2023-06-15",
            InvalidSanctionDate = "2024-06-15",
            ValidTxnDate = "2023-06-15",
            InvalidTxnDate = "2024-06-15";






}
