package foo;


import com.oracle.tools.packager.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.io.ObjectOutputStream;

/**
 * Created by Justwyne on 5/24/16 AD.
 */
public class SocketClient extends Thread{

    public static void main(String[] args) throws IOException {
        String ip = "127.0.0.1";
        int port = 9093;
        Socket socket = new Socket(ip, port);
        PrintWriter out;

        PrintStream p;
        for (int i=0; i<100; i++) {
            try {
                String data = "Hello-" + i;

                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(data);

                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getLocalizedMessage());
            } finally {
            }
        }
    }
}
