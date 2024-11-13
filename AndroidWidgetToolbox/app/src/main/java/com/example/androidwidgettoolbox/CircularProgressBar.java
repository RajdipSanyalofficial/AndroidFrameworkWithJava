package com.example.androidwidgettoolbox;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class CircularProgressBar extends View {
    private Paint paint;
    private int progress = 0;

    public CircularProgressBar(Context context) {
        super(context);
        init();
    }
    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(getWidth(), getHeight()) / 2;
        RectF rect = new RectF(10, 10, getWidth() - 10, getHeight() - 10);
        canvas.drawArc(rect, -90, (360 * progress) / 100, false, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}

