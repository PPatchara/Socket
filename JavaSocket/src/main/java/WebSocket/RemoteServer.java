package WebSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import foo.RobotControl;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class RemoteServer extends WebSocketServer{

    private Logger log = Logger.getLogger(this.getClass().getName());
    private RobotControl control;
    private JSONObject currentProduct;
    private FileHandler fh;

    private void init() {
        // "%1$tF %1$tT.%1$tL %4$s, %2$s: %5$s%6$s%n"
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");

        Date date = new Date();
        String dateString = new SimpleDateFormat("yyMMdd_HHmmss_S").format(date);

        try {
            XMLFormatter xmlFormatter = new XMLFormatter();
            fh = new FileHandler("./" + dateString + "-logfile.log");
            fh.setFormatter(xmlFormatter);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Hello, world");
    }

    public RemoteServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public RemoteServer(int port, RobotControl control) throws UnknownHostException {
        super(new InetSocketAddress(port));
        init();
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
            String action = jsonObject.getString("action");

            if (action.equals("pos")) {
                mouseControl(jsonObject);
            } else if (action.equals("current_product")) {
                setCurrentProduct(jsonObject);
                sendToAll(jsonObject);
            } else if (action.equals("trigger_mode")) {
                triggerMode(jsonObject);
            } else if (action.equals("tap")) {
                click();
            } else if (action.equals("forward_swipe") || action.equals("backward_swipe")) {
                sendToAll(jsonObject);
            }

            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void click() {
        log.info("Clicked");
        control.click();
    }

    private void mouseControl(JSONObject jsonObject) throws JSONException {
        int deltaX = jsonObject.getInt("delta_x");
        int deltaY = jsonObject.getInt("delta_y");
        String message = String.format("Moved : (%d,%d)", deltaX, deltaY);
        log.info(message);

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
        log.info("Triggered");
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
