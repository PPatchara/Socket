package WebSocket;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import foo.RobotControl;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class MainApp {

    public static void main(String[] args) throws IOException, AWTException {
        int port = 8887; // 843 flash policy port

        RobotControl control = new RobotControl();

        RemoteServer s = new RemoteServer(port, control);
        s.start();

        System.out.println( "ChatServer started on IP: " + InetAddress.getLocalHost().getHostAddress() + " port: " + s.getPort() );
//        System.out.println();

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
        }
    }

}
