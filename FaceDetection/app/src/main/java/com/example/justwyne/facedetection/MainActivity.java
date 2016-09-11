package com.example.justwyne.facedetection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.media.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justwyne.facedetection.cam.FaceDetectionCamera;
import com.example.justwyne.facedetection.cam.FrontCameraRetriever;

public class MainActivity extends Activity implements FrontCameraRetriever.Listener, FaceDetectionCamera.Listener {

    private static final String TAG = "FDT" + MainActivity.class.getSimpleName();

    private TextView helloWorldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloWorldTextView = (TextView) findViewById(R.id.helloWorldTextView);
        // Go get the front facing camera of the device
        // best practice is to do this asynchronously
        FrontCameraRetriever.retrieveFor(this);
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
        // When the front facing camera has been retrieved we still need to ensure our display is ready
        // so we will let the camera surface view initialise the camera i.e turn face detection on
        SurfaceView cameraSurface = new CameraSurfaceView(this, camera, this);
        // Add the surface view (i.e. camera preview to our layout)
        ((LinearLayout) findViewById(R.id.helloWorldCameraPreview)).addView(cameraSurface);
    }

    @Override
    public void onFailedToLoadFaceDetectionCamera() {
        // This can happen if
        // there is no front facing camera
        // or another app is using the camera
        // or our app or another app failed to release the camera properly
        Log.d("Camera Error", "Failed to load camera, what went wrong?");
        helloWorldTextView.setText(R.string.error_with_face_detection);
    }

    @Override
    public void onFaceDetected() {
        helloWorldTextView.setText(R.string.face_detected_message);
    }

    @Override
    public void onFaceTimedOut() {
        helloWorldTextView.setText(R.string.face_detected_then_lost_message);
    }

    @Override
    public void onFaceDetectionNonRecoverableError() {
        // This can happen if
        // Face detection not supported on this device
        // Something went wrong in the Android api
        // or our app or another app failed to release the camera properly
        helloWorldTextView.setText(R.string.error_with_face_detection);
    }
}
