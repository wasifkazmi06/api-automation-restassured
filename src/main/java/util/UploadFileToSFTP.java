package util;

import com.jcraft.jsch.*;

public class UploadFileToSFTP {

    static public void uploadPhyNach(){

        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("", "",1234);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put("", "");
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }
}
