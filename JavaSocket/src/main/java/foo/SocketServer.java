package foo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class SocketServer extends Thread {


    Socket socket;

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9093);

        while (true) {
            try {
                new SocketServer(listener.accept());
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }

    }

    private SocketServer(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( socket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println ("Server: " + inputLine);
                out.println(inputLine);
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }



    }
}