package com.example.justwyne.facedetection.cam;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Justwyne on 9/10/16 AD.
 */
public class FaceDetectionCamera implements OneShotFaceDetectionListener.Listener {
    private final Camera camera;

    private Listener listener;

    public FaceDetectionCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Use this to detect faces when you have a custom surface to display upon
     *
     * @param listener the {@link com.blundell.tutorial.cam.FaceDetectionCamera.Listener} for when faces are detected
     * @param holder   the {@link android.view.SurfaceHolder} to display upon
     */
    public void initialise(Listener listener, SurfaceHolder holder) {
        this.listener = listener;
        try {
            camera.stopPreview();
        } catch (Exception swallow) {
            // ignore: tried to stop a non-existent preview
        }
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            camera.setFaceDetectionListener(new OneShotFaceDetectionListener(this));
            camera.startFaceDetection();
        } catch (IOException e) {
            this.listener.onFaceDetectionNonRecoverableError();
        }
    }

    @Override
    public void onFaceDetected() {
        listener.onFaceDetected();
    }

    @Override
    public void onFaceTimedOut() {
        listener.onFaceTimedOut();
    }

    public void recycle() {
        if (camera != null) {
            camera.release();
        }
    }

    public interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();

        void onFaceDetectionNonRecoverableError();

    }
}
