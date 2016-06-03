package com.example.justwyne.bluetoothconnection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    private MediaButtonIntentReceiver receiver;
    BroadcastReceiver mBroadcastReceiver1;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = new IntentFilter();//"android.intent.action.MEDIA_BUTTON"
//        MediaButtonIntentReceiver r = new MediaButtonIntentReceiver();
        filter.setPriority(1000); //this line sets receiver priority
//        registerReceiver(r, filter);

//        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);//"android.intent.action.MEDIA_BUTTON"
//        receiver = new MediaButtonIntentReceiver();
//        filter.setPriority(1000); //this line sets receiver priority
//        registerReceiver(receiver, filter);


//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
//        filter.addAction("SOME_OTHER_ACTION");
//
//        receiver2 = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                Log.d("Remote", "Hello");
//            }
//        };
//        registerReceiver(receiver, filter);

//        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

//        mBroadcastReceiver1 = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                final String action = intent.getAction();
//
//                Log.d("Remote","hello");
//
//                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
//                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
//                    switch(state) {
//                        case BluetoothAdapter.STATE_OFF:
//                            break;
//                        case BluetoothAdapter.STATE_TURNING_OFF:
//                            break;
//                        case BluetoothAdapter.STATE_ON:
//                            break;
//                        case BluetoothAdapter.STATE_TURNING_ON:
//                            break;
//                    }
//
//                }
//            }
//        };
//
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
//        registerReceiver(mBroadcastReceiver1, filter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        registerReceiver(mMessageReceiver, filter);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                Toast.makeText(getApplicationContext(), "BT found", Toast.LENGTH_SHORT).show();
            } else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                Toast.makeText(getApplicationContext(), "BT Connected", Toast.LENGTH_SHORT).show();
            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                Toast.makeText(getApplicationContext(), "BT Disconnected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "BT Disconnect requested", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }
}
