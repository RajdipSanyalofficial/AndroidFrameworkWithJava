package com.example.androidwidgettoolbox;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class CompassView extends View {
    private Paint paint;
    private float direction = 0;

    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;

        // Draw compass circle
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        // Draw the compass line pointing north
        float angle = -direction * (float) Math.PI / 180;
        float startX = width / 2 + (float) (radius * Math.sin(angle));
        float startY = height / 2 - (float) (radius * Math.cos(angle));
        canvas.drawLine(width / 2, height / 2, startX, startY, paint);
    }

    public void updateDirection(float newDirection) {
        direction = newDirection;
        invalidate(); // Redraw the view
    }
}
