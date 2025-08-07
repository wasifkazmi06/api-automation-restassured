package vaultTM;

import io.qameta.allure.Allure;
import lazypay.MakeTransaction;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j

public class AccountConversion {

    public static String bnplProductVersion = System.getProperty("bnplProductVersion");
    public static String cofProductVersion = System.getProperty("cofProductVersion");
    public static String supervisorContractVersion = System.getProperty("supervisorContractVersion");
    public static ResultSet bnplAccountData;
    public static ResultSet cofAccountData;
    public static String planId;
    public static String bnplPlanAssociationId;
    public static List<String> cofPlanAssociationId = new ArrayList<>();
    public static String bnplAccountId;
    public static List<String> cofAccountId = new ArrayList<>();

    @BeforeClass
    public static void getAccountDetails() throws Exception {
        bnplAccountData = VaultTMData.getBNPLAccountData(MakeTransaction.mobile);
        cofAccountData = VaultTMData.getCOFAccountData(MakeTransaction.mobile);

        while (bnplAccountData.next()) {
            planId = bnplAccountData.getString("plan_id");
            bnplAccountId = bnplAccountData.getString("account_id");
            bnplPlanAssociationId = bnplAccountData.getString("plan_association_id");
            log.info("User's planId: {}, bnplAccountId: {} and bnplPlanAssociationId: {}",
                    planId, bnplAccountId, bnplPlanAssociationId);
        }
       while (cofAccountData.next()) {
           String cofAccId = cofAccountData.getString("ledger_account_id");
           String cofPlanAssoId = cofAccountData.getString("plan_association_id");
           cofAccountId.add(cofAccId);
           cofPlanAssociationId.add(cofPlanAssoId);
           log.info("User's cofAccountId: {} and cofPlanAssociationId: {}", cofAccountId, cofPlanAssociationId);
        }
        log.info("Number of COF loans for this user is: {}", cofAccountId.size());
    }

    @Test
    public static void accountConvertMethod() throws Exception {

        /**
         BNPL plan disassociation
         */
        HashMap<String, String> bnplPlanDisassociateRequest = new HashMap<>();
        bnplPlanDisassociateRequest.put("plan_id", planId);
        bnplPlanDisassociateRequest.put("account_plan_assoc_id", bnplPlanAssociationId);

        PlanDisassociateStep.planDisassociateMethod(bnplPlanDisassociateRequest);
        Assert.assertEquals(PlanDisassociateStep.planDisassociatePojo.status, "PLAN_UPDATE_STATUS_PENDING_EXECUTION");
        Allure.addAttachment("BNPL plan disassociation status", PlanDisassociateStep.planDisassociatePojo.status);

        /**
         COF plan disassociation
         */
        for(int i=0; i<cofPlanAssociationId.size(); i++) {
            HashMap<String, String> cofPlanDisassociateRequest = new HashMap<>();
            cofPlanDisassociateRequest.put("plan_id", planId);
            cofPlanDisassociateRequest.put("account_plan_assoc_id", cofPlanAssociationId.get(i));

            Thread.sleep(3000);
            PlanDisassociateStep.planDisassociateMethod(cofPlanDisassociateRequest);
            Assert.assertEquals(PlanDisassociateStep.planDisassociatePojo.status, "PLAN_UPDATE_STATUS_PENDING_EXECUTION");
            Allure.addAttachment("COF plan disassociation status", PlanDisassociateStep.planDisassociatePojo.status);
        }

        /**
         BNPL account product version ID update
         */
        HashMap<String, String> bnplAccountUpdateRequest = new HashMap<>();
        bnplAccountUpdateRequest.put("account_id", bnplAccountId);
        bnplAccountUpdateRequest.put("product_version_id", bnplProductVersion);
        bnplAccountUpdateRequest.put("schedule_migration_type", VaultTMData.scheduleMigrationType);

        Thread.sleep(3000);
        AccountUpdateStep.accountUpdateMethod(bnplAccountUpdateRequest);
        Assert.assertEquals(AccountUpdateStep.accountUpdatePojo.status, "ACCOUNT_UPDATE_STATUS_PENDING_EXECUTION");
        Allure.addAttachment("BNPL account update status", AccountUpdateStep.accountUpdatePojo.status);

        /**
         COF account product version ID update
         */
        for(int i=0; i<cofAccountId.size(); i++) {
            HashMap<String, String> cofAccountUpdateRequest = new HashMap<>();
            cofAccountUpdateRequest.put("account_id", cofAccountId.get(i));
            cofAccountUpdateRequest.put("product_version_id", cofProductVersion);
            cofAccountUpdateRequest.put("schedule_migration_type", VaultTMData.scheduleMigrationType);

            Thread.sleep(3000);
            AccountUpdateStep.accountUpdateMethod(cofAccountUpdateRequest);
            Assert.assertEquals(AccountUpdateStep.accountUpdatePojo.status, "ACCOUNT_UPDATE_STATUS_PENDING_EXECUTION");
            Allure.addAttachment("COF account update status", AccountUpdateStep.accountUpdatePojo.status);
        }

        /**
         Plan migration
         */
        HashMap<String, String> planMigrateRequest = new HashMap<>();
        planMigrateRequest.put("plan_id", planId);
        planMigrateRequest.put("supervisor_contract_version_id", supervisorContractVersion);

        Thread.sleep(3000);
        PlanMigrateStep.planMigrateMethod(planMigrateRequest);
        Assert.assertEquals(PlanMigrateStep.planMigratePojo.status, "PLAN_UPDATE_STATUS_PENDING_EXECUTION");
        Allure.addAttachment("Plan migration status", PlanMigrateStep.planMigratePojo.status);

        /**
         BNPL account plan association
         */
        HashMap<String, String> bnplPlanAssociateRequest = new HashMap<>();
        bnplPlanAssociateRequest.put("plan_id", planId);
        bnplPlanAssociateRequest.put("account_id", bnplAccountId);

        Thread.sleep(3000);
        PlanAssociateStep.planAssociateMethod(bnplPlanAssociateRequest);
        Assert.assertEquals(PlanAssociateStep.planAssociatePojo.status, "PLAN_UPDATE_STATUS_PENDING_EXECUTION");
        Allure.addAttachment("BNPL account plan association status", PlanAssociateStep.planAssociatePojo.status);

        /**
         COF account plan association
         */
        for(int i=0; i<cofAccountId.size(); i++) {
            HashMap<String, String> cofPlanAssociateRequest = new HashMap<>();
            cofPlanAssociateRequest.put("plan_id", planId);
            cofPlanAssociateRequest.put("account_id", cofAccountId.get(i));

            Thread.sleep(3000);
            PlanAssociateStep.planAssociateMethod(cofPlanAssociateRequest);
            Assert.assertEquals(PlanAssociateStep.planAssociatePojo.status, "PLAN_UPDATE_STATUS_PENDING_EXECUTION");
            Allure.addAttachment("COF account plan association status", PlanAssociateStep.planAssociatePojo.status);
        }
    }
}