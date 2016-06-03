package WebSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Set;

import foo.RobotControl;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class RemoteServer extends WebSocketServer{

    private RobotControl control;

    public RemoteServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public RemoteServer(int port, RobotControl control) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.control = control;
    }

    public RemoteServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println( webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
        System.out.println( "Open Size: " + connections().size() );

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println( webSocket + " has left the room!" );
        System.out.println( "Close Size: " + connections().size() );
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println("Data: " + s );

        String[] stringList = s.split(",");

        if(stringList.length <= 1) return;

        if(stringList[0].equalsIgnoreCase("pos")) {
            controlProcess(stringList);
        }if (stringList[0].equalsIgnoreCase("s_event")){
            eventProcess(stringList);
        } else if(stringList[0].equalsIgnoreCase("tap")){
            try {
                clickProcess();
            } catch (AWTException error) {
                error.printStackTrace();
            }
        } else {
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        if( webSocket != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    private void clickProcess() throws AWTException {
        control.click();
    }

    private void controlProcess(String[] str) {
        int posX = Integer.parseInt(str[1]);
        int posY = Integer.parseInt(str[2]);

        control.move(posX, posY);
    }

    private void eventProcess(String[] str) {
        Collection<WebSocket> connections = connections();

        String trigger = str[1];
        String data = String.format("s_event,%s", trigger);


        for (WebSocket webSocket: connections) {
            webSocket.send(data);
        }
    }
}
