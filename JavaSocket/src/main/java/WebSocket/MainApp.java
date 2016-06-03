package WebSocket;

import org.java_websocket.WebSocketImpl;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import foo.RobotControl;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class MainApp {

    public static void main(String[] args) throws IOException, AWTException {
        WebSocketImpl.DEBUG = false;
        int port = 8887; // 843 flash policy port

        RobotControl control = new RobotControl();

        RemoteServer s = new RemoteServer(port, control);
        s.start();

        System.out.println( "ChatServer started on port: " + s.getPort() );

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
        }
    }

}
