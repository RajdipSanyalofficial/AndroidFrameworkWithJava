package com.example.enhancingyourviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawingThread drawingThread;
    private Paint paint;

    public CustomSurfaceView(Context context) {
        super(context);
        init();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // SurfaceView settings
        getHolder().addCallback(this);

        // Initialize paint
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true); // Enable anti-aliasing for smoother drawing
        paint.setStyle(Paint.Style.FILL);

        // Check for hardware acceleration
        if (isHardwareAccelerated()) {
            paint.setAntiAlias(true); // Enabling smooth drawing with hardware acceleration
        } else {
            paint.setAntiAlias(false);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Start the drawing thread
        drawingThread = new DrawingThread(getHolder());
        drawingThread.setRunning(true);
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes, like screen orientation
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Stop the drawing thread
        boolean retry = true;
        drawingThread.setRunning(false);
        while (retry) {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Inner thread class to handle the drawing loop
    private class DrawingThread extends Thread {

        private SurfaceHolder surfaceHolder;
        private boolean running;

        public DrawingThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            drawCanvas(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        private void drawCanvas(Canvas canvas) {
            // Clear the canvas
            canvas.drawColor(Color.WHITE);

            // Draw a red circle with anti-aliasing
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 100, paint);
        }
    }
}

