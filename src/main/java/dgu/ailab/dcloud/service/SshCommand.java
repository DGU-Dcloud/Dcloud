package dgu.ailab.dcloud.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import dgu.ailab.dcloud.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class SshCommand {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    public static void executeCommand(String host, int port, String username, String password, String command) {
        try {
            // JSch 객체 생성
            JSch jsch = new JSch();

            // 세션 생성 (host, port, username, password)
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no"); // 호스트 키 검사 비활성화
            session.connect(); // SSH 연결

            // 명령어 실행을 위한 채널 생성 (ChannelExec)
            ChannelExec channel = (ChannelExec) session.openChannel("exec");

            // sudo 명령을 실행하기 위해 사용자의 권한을 확인하는 명령 추가
            command = "echo '" + password + "' | sudo -S -p '' " + command;


            channel.setCommand(command);

            // 명령어 실행 결과를 읽기 위한 스트림 생성
            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // 채널 연결
            channel.connect();

            // 명령어 실행 결과 출력
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("line : {}", line);
            }

            // 채널 연결 해제
            channel.disconnect();

            // 세션 종료
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
