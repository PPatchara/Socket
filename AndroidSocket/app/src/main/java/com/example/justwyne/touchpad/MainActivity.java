package com.example.justwyne.touchpad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private Socket socket;
    private String ip = "172.20.10.3";
    private int port = 9093;
    PrintWriter out;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new ClientThread()).start();
    }

    private void prepareSocket(){
        try {
            socket = new Socket(ip, port);
        } catch (IOException ex) {
            Log.d("Socket", ex.getMessage());
            Log.d("Socket", ex.getLocalizedMessage());
            Log.d("Socket", "Error! can't connect to server");
        }

    }

    public boolean onTouchEvent(MotionEvent e)
    {
        int xpos=(int) e.getX();
        int ypos=(int) e.getY();
        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "On touch (down)" + String.valueOf(xpos) + ", " + String.valueOf(ypos));
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "On touch (up)" + String.valueOf(xpos) + ", " + String.valueOf(ypos));
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "On touch (move)" + String.valueOf(xpos) + ", " + String.valueOf(ypos));
                break;
        }

        String data = String.format("%d,%d", xpos, ypos);

        if( data != null && socket != null) {
            Log.v("Data", data);
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(data);
            } catch (IOException ex) {}
        }

        return true;
    }

    class ClientThread implements Runnable {
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket = new Socket(ip, port);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
