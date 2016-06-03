package com.example.justwyne.bluetoothconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by Justwyne on 5/25/16 AD.
 */
public class MediaButtonIntentReceiver extends BroadcastReceiver {
    public MediaButtonIntentReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                // Handle key press.
                Log.d("Remove","kkk");
            }
        }

//        String intentAction = intent.getAction();
//        Log.d("Remove","kkk");
//        if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
//            return;
//        }
//        KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
//        if (event == null) {
//            return;
//        }
//        int action = event.getAction();
//        if (action == KeyEvent.ACTION_DOWN) {
//            // do something
//            Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
//        }
//        abortBroadcast();
    }
}
