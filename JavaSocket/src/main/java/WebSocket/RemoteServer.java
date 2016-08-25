package WebSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import foo.RobotControl;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class RemoteServer extends WebSocketServer{

    private RobotControl control;

    private JSONObject currentProduct;

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
        try {
            JSONObject jsonObject = new JSONObject(s);

            switch(jsonObject.getString("action")) {
                case "pos":
                    mouseControl(jsonObject); break;
                case "current_product":
                    setCurrentProduct(jsonObject);
                    sendToAll(jsonObject); break;
                case "trigger_mode":
                    triggerMode(jsonObject); break;
                case "tap":
                    control.click(); break;
                case "forward_swipe":
                case "backward_swipe":
                    sendToAll(jsonObject); break;
            }

            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void mouseControl(JSONObject jsonObject) throws JSONException {
        int deltaX = jsonObject.getInt("delta_x");
        int deltaY = jsonObject.getInt("delta_y");

        control.move(deltaX, deltaY);
    }


    private void setCurrentProduct(JSONObject jsonObject) {
        this.currentProduct = jsonObject;
    }

    private void sendToAll(JSONObject jsonObject) {
        Collection<WebSocket> connections = connections();
        for (WebSocket webSocket: connections) {
            webSocket.send(jsonObject.toString());
        }
    }

    private void triggerMode(JSONObject jsonObject) throws JSONException {
        jsonObject.put("product", currentProduct);
        System.out.println(jsonObject.toString());
        sendToAll(jsonObject);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        if( webSocket != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }
}
