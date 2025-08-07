package util;

import com.jcraft.jsch.*;
import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class DeleteUser {

    static String mobile;
    static String scriptName;

    @Test
    public static void deleteUserAdhoc() throws Exception {
        mobile = "6360476325";
        deleteUserMethod(mobile,  "sbox", "col");

    }

    public static void deleteUserMethod(String mobile, String env, String db) throws Exception {
        ReadProperties.intializeTestConfig(System.getProperty("env"));
        String sshUser = ReadProperties.testConfigMap.get(UtilConstants.SSH_USER).toString();
        String sshPassword = ReadProperties.testConfigMap.get(UtilConstants.SSH_PASSWORD).toString();
        int sshPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SSH_PORT).toString());
        //static int localPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SSH_LOCAL_PORT).toString());
        String sshHost = ReadProperties.testConfigMap.get(UtilConstants.SSH_HOST).toString();
        String command = "python /tmp/" + returnScriptName(env) + " " + mobile + " " + db;

        try {
            JSch jsch = new JSch();
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            Session session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPassword);
            session.setConfig(config);
            session.connect();
            log.info(command);
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            if (channelExec.isClosed()) {
                log.info("Exit status: " + channelExec.getExitStatus());
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public static String returnScriptName(String env){
        switch (env) {
            case "sbox":
                scriptName = "purgeUser3.py";
                break;
            case "QA":
                scriptName = "purgeUserQA.py";
                break;
            default:
                scriptName = null;
                log.warn("Incorrect environment provided. Accepted values are sbox or QA.");
                break;
        }
        return scriptName;
    }
}
