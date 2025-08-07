package snailMailChanges;
import com.jcraft.jsch.*;
import util.ReadProperties;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static constants.UtilConstants.*;

public class GeniricUtility {

    public static Session serverSessionConnect(String host, String user, String password) {
        JSch jsch = new JSch();
        Session session = null;

        try {
            // Create session
            session = jsch.getSession(user, host);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }
    public static int readFileFromSFTP(String source, String date) throws JSchException {
        ChannelSftp channelSftp = null;
        String host = ReadProperties.testConfigMap.get(SFTP_HOST).toString();
        String user = ReadProperties.testConfigMap.get(SFTP_USER).toString();
        String password = ReadProperties.testConfigMap.get(SFTP_PASSWORD).toString();
        String remoteFilePath = "/snailMail/Payu_mailList_"+source+"_"+date+".csv";
        System.out.println(remoteFilePath);
        Session session = serverSessionConnect(host, user, password);
        // Open an SFTP channel
        Channel channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        int rowCount = 0;
        try {
            // Count rows in the file
            InputStream inputStream = channelSftp.get(remoteFilePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.readLine() != null) {
                rowCount++;
            }
            System.out.println("Number of rows in the file: " + rowCount);

            // Download a file
            byte[] buffer = new byte[1024];
            int bytesRead;
            System.out.println("Reading file contents:");
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                System.out.write(buffer, 0, bytesRead);
            }

            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Disconnect SFTP channel and session
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }

        }
        return rowCount-1;

    }

}
